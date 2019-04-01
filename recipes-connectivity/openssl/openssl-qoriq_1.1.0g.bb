SUMMARY = "Secure Socket Layer"
DESCRIPTION = "Secure Socket Layer (SSL) binary and related cryptographic tools."
HOMEPAGE = "http://www.openssl.org/"
BUGTRACKER = "http://www.openssl.org/news/vulnerabilities.html"
SECTION = "libs/network"

DISABLE_STATIC = ""

# "openssl | SSLeay" dual license
LICENSE = "openssl"
LIC_FILES_CHKSUM = "file://LICENSE;md5=cae6da10f4ffd9703214776d2aabce32"

DEPENDS += "cryptodev-linux"
DEPENDS_append_class-target = " openssl-native"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/openssl;nobranch=1 \
           file://run-ptest \
           file://openssl-c_rehash.sh \
           file://0001-Take-linking-flags-from-LDFLAGS-env-var.patch \
           file://0001-Remove-test-that-requires-running-as-non-root.patch \
           file://0001-aes-asm-aes-armv4-bsaes-armv7-.pl-make-it-work-with-.patch \
          "

SRCREV = "472c9c380669eb7a26819a52598632f257b3e72b"

PROVIDES = "openssl"

python() {
    pkgs = d.getVar('PACKAGES').split()
    for p in pkgs:
        if 'openssl-qoriq' in p:
            d.appendVar("RPROVIDES_%s" % p, p.replace('openssl-qoriq', 'openssl'))
            d.appendVar("RCONFLICTS_%s" % p, p.replace('openssl-qoriq', 'openssl'))
            d.appendVar("RREPLACES_%s" % p, p.replace('openssl-qoriq', 'openssl'))
}

S = "${WORKDIR}/git"

inherit lib_package multilib_header ptest

do_configure () {
	os=${HOST_OS}
	case $os in
	linux-uclibc |\
	linux-uclibceabi |\
	linux-gnueabi |\
	linux-uclibcspe |\
	linux-gnuspe |\
	linux-musl*)
		os=linux
		;;
		*)
		;;
	esac
	target="$os-${HOST_ARCH}"
	case $target in
	linux-arm)
		target=linux-armv4
		;;
	linux-armeb)
		target=linux-armv4
		;;
	linux-aarch64*)
		target=linux-aarch64
		;;
	linux-sh3)
		target=linux-generic32
		;;
	linux-sh4)
		target=linux-generic32
		;;
	linux-i486)
		target=linux-elf
		;;
	linux-i586 | linux-viac3)
		target=linux-elf
		;;
	linux-i686)
		target=linux-elf
		;;
	linux-gnux32-x86_64)
		target=linux-x32
		;;
	linux-gnu64-x86_64)
		target=linux-x86_64
		;;
	linux-mips)
                # specifying TARGET_CC_ARCH prevents openssl from (incorrectly) adding target architecture flags
		target="linux-mips32 ${TARGET_CC_ARCH}"
		;;
	linux-mipsel)
		target="linux-mips32 ${TARGET_CC_ARCH}"
		;;
        linux-gnun32-mips*)
               target=linux-mips64
                ;;
        linux-*-mips64 | linux-mips64)
               target=linux64-mips64
                ;;
        linux-*-mips64el | linux-mips64el)
               target=linux64-mips64
                ;;
	linux-microblaze*|linux-nios2*)
		target=linux-generic32
		;;
	linux-powerpc)
		target=linux-ppc
		;;
	linux-powerpc64)
		target=linux-ppc64
		;;
	linux-riscv64)
		target=linux-generic64
		;;
	linux-riscv32)
		target=linux-generic32
		;;
	linux-supersparc)
		target=linux-sparcv9
		;;
	linux-sparc)
		target=linux-sparcv9
		;;
	darwin-i386)
		target=darwin-i386-cc
		;;
	esac
        useprefix=${prefix}
        if [ "x$useprefix" = "x" ]; then
                useprefix=/
        fi
	libdirleaf="$(echo ${libdir} | sed s:$useprefix::)"
	perl ./Configure -DHAVE_CRYPTODEV ${EXTRA_OECONF} --prefix=$useprefix --openssldir=${libdir}/ssl-1.1 --libdir=${libdirleaf} $target
}

#| engines/afalg/e_afalg.c: In function 'eventfd':
#| engines/afalg/e_afalg.c:110:20: error: '__NR_eventfd' undeclared (first use in this function)
#|      return syscall(__NR_eventfd, n);
#|                     ^~~~~~~~~~~~
EXTRA_OECONF_aarch64 += "no-afalgeng"

#| ./libcrypto.so: undefined reference to `getcontext'
#| ./libcrypto.so: undefined reference to `setcontext'
#| ./libcrypto.so: undefined reference to `makecontext'
EXTRA_OECONF_libc-musl += "-DOPENSSL_NO_ASYNC"

do_install () {
        oe_runmake DESTDIR="${D}" MANDIR="${mandir}" MANSUFFIX=ssl install
        oe_multilib_header openssl/opensslconf.h
}

do_install_append_class-native () {
        # Install a custom version of c_rehash that can handle sysroots properly.
        # This version is used for example when installing ca-certificates during
        # image creation.
        install -Dm 0755 ${WORKDIR}/openssl-c_rehash.sh ${D}${bindir}/c_rehash
        sed -i -e 's,/etc/openssl,${sysconfdir}/ssl,g' ${D}${bindir}/c_rehash
}

do_install_ptest() {
        cp -r * ${D}${PTEST_PATH}

        # Putting .so files in ptest package will mess up the dependencies of the main openssl package
        # so we rename them to .so.ptest and patch the test accordingly
        mv ${D}${PTEST_PATH}/libcrypto.so ${D}${PTEST_PATH}/libcrypto.so.ptest
        mv ${D}${PTEST_PATH}/libssl.so ${D}${PTEST_PATH}/libssl.so.ptest
        sed -i 's/$target{shared_extension_simple}/".so.ptest"/' ${D}${PTEST_PATH}/test/recipes/90-test_shlibload.t
}

RDEPENDS_${PN}-ptest += "perl-module-file-spec-functions bash python"
RRECOMMENDS_libcrypto += "openssl-conf"
RDEPENDS_${PN}-bin = "perl"
RDEPENDS_${PN}-misc = "perl"

FILES_${PN} =+ " ${libdir}/ssl-1.1/*"

PACKAGES =+ "libcrypto libssl openssl-conf ${PN}-engines ${PN}-misc"
FILES_libcrypto = "${libdir}/libcrypto${SOLIBS}"
FILES_libssl = "${libdir}/libssl${SOLIBS}"
FILES_openssl-conf = "${sysconfdir}/ssl/openssl.cnf"
FILES_${PN}-engines = "${libdir}/engines-1.1"
FILES_${PN}-misc = "${libdir}/ssl-1.1/misc"

RPROVIDES_openssl-conf = "openssl10-conf"
RREPLACES_openssl-conf = "openssl10-conf"
RCONFLICTS_openssl-conf = "openssl10-conf"
