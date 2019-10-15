
PACKAGES += "vpp-core-data vpp-core-plugin-igmp vpp-core-plugin-gtpu vpp-core-plugin-gbp \
		vpp-core-plugin-lb vpp-core-plugin-avf vpp-core-plugin-cdp vpp-core-plugin-map vpp-core-plugin-srv6as \
		vpp-core-plugin-l2e vpp-core-plugin-acl vpp-core-plugin-flowprobe vpp-core-plugin-abf \
		vpp-core-plugin-pppoe vpp-core-plugin-lacp vpp-core-plugin-ioam vpp-core-plugin-memif \
		vpp-core-plugin-stn vpp-core-plugin-srv6ad vpp-core-plugin-mactime vpp-core-plugin-unittest \
		vpp-core-plugin-nat vpp-core-plugin-srv6am vpp-core-plugin-lb_test \
		vpp-core-plugin-ioam_vxlan_gpe_test vpp-core-plugin-map_test vpp-core-plugin-avf_test \
		vpp-core-plugin-mactime_test vpp-core-plugin-udp_ping_test vpp-core-plugin-ioam_trace_test \
		vpp-core-plugin-lacp_test vpp-core-plugin-stn_test vpp-core-plugin-memif_test \
		vpp-core-plugin-nat_test vpp-core-plugin-acl_test vpp-core-plugin-ioam_export_test \
		vpp-core-plugin-flowprobe_test vpp-core-plugin-ioam_pot_test \
		vpp-core-plugin-vxlan_gpe_ioam_export_test  vpp-core-plugin-pppoe_test vpp-core-plugin-gtpu_test \
		vpp-core-plugin-cdp_test vpp-core-plugin-dpdk vpp-core-plugin-dpdk_test vpp-core-plugin-vmxnet3_test \
                vpp-core-plugin-vmxnet3 vpp-core-plugin-svs vpp-core-test vpp-core-plugin-nsh_test \
                vpp-core-plugin-ioam_test vpp-core-plugin-nsim_test vpp-core-plugin-nsim vpp-core-plugin-perfmon \ 
                vpp-core-plugin-nsh \
		vpp-core-plugin-tlsopenssl vpp-core-plugin-ixge vpp-core-plugin-ila vpp-core-plugin-data \
		"

#vpp-core-python 
#FILES_${PN}-python = "${PYTHON_SITEPACKAGES_DIR} \
#	          ${PYTHON_SITEPACKAGES_DIR}/vpp_papi-1.6.1-py2.7.egg \
#		  ${PYTHON_SITEPACKAGES_DIR}/vpp_papi-1.6.1-py2.7.egg-info \
#		  ${PYTHON_SITEPACKAGES_DIR}/vpp_papi-1.6.1-py2.7.egg-info/requires.txt \
#		  ${PYTHON_SITEPACKAGES_DIR}/vpp_papi-1.6.1-py2.7.egg-info/PKG-INFO \
#		  ${PYTHON_SITEPACKAGES_DIR}/vpp_papi-1.6.1-py2.7.egg-info/top_level.txt \
#		  ${PYTHON_SITEPACKAGES_DIR}/vpp_papi-1.6.1-py2.7.egg-info/dependency_links.txt \
#		  ${PYTHON_SITEPACKAGES_DIR}/vpp_papi-1.6.1-py2.7.egg-info/zip-safe \
#		  ${PYTHON_SITEPACKAGES_DIR}/vpp_papi-1.6.1-py2.7.egg-info/SOURCES.txt \
#			"

FILES_${PN}-test = " ${bindir}/vpp_json_test "
FILES_${PN}-plugin-igmp = " ${libdir}/vpp_plugins/igmp_plugin.so "
FILES_${PN}-plugin-gtpu = "  ${libdir}/vpp_plugins/gtpu_plugin.so "
FILES_${PN}-plugin-gbp = " ${libdir}/vpp_plugins/gbp_plugin.so "
FILES_${PN}-plugin-lb = " ${libdir}/vpp_plugins/lb_plugin.so "
FILES_${PN}-plugin-avf = "  ${libdir}/vpp_plugins/avf_plugin.so "
FILES_${PN}-plugin-cdp = "  ${libdir}/vpp_plugins/cdp_plugin.so "
FILES_${PN}-plugin-map = "  ${libdir}/vpp_plugins/map_plugin.so "
FILES_${PN}-plugin-srv6as = "  ${libdir}/vpp_plugins/srv6as_plugin.so "
FILES_${PN}-plugin-tlsopenssl = "  ${libdir}/vpp_plugins/tlsopenssl_plugin.so "
FILES_${PN}-plugin-ixge = "  ${libdir}/vpp_plugins/ixge_plugin.so "
FILES_${PN}-plugin-l2e = "  ${libdir}/vpp_plugins/l2e_plugin.so "
FILES_${PN}-plugin-acl = "  ${libdir}/vpp_plugins/acl_plugin.so "
FILES_${PN}-plugin-flowprobe = "  ${libdir}/vpp_plugins/flowprobe_plugin.so "
FILES_${PN}-plugin-abf = "  ${libdir}/vpp_plugins/abf_plugin.so "
FILES_${PN}-plugin-pppoe = "  ${libdir}/vpp_plugins/pppoe_plugin.so "
FILES_${PN}-plugin-lacp = "  ${libdir}/vpp_plugins/lacp_plugin.so "
FILES_${PN}-plugin-ioam = "  ${libdir}/vpp_plugins/ioam_plugin.so "
FILES_${PN}-plugin-memif = "  ${libdir}/vpp_plugins/memif_plugin.so "
FILES_${PN}-plugin-stn = "  ${libdir}/vpp_plugins/stn_plugin.so "
FILES_${PN}-plugin-srv6ad = "  ${libdir}/vpp_plugins/srv6ad_plugin.so "
FILES_${PN}-plugin-mactime = "  ${libdir}/vpp_plugins/mactime_plugin.so "
FILES_${PN}-plugin-unittest = "  ${libdir}/vpp_plugins/unittest_plugin.so "
FILES_${PN}-plugin-nat = "  ${libdir}/vpp_plugins/nat_plugin.so "
FILES_${PN}-plugin-srv6am = "  ${libdir}/vpp_plugins/srv6am_plugin.so "
FILES_${PN}-plugin-dpdk = "  ${libdir}/vpp_plugins/dpdk_plugin.so "
FILES_${PN}-plugin-ila = "  ${libdir}/vpp_plugins/ila_plugin.so "
FILES_${PN}-plugin-svs = "  ${libdir}/vpp_plugins/svs_plugin.so "
FILES_${PN}-plugin-nsh = "  ${libdir}/vpp_plugins/nsh_plugin.so "
FILES_${PN}-plugin-vmxnet3 = "  ${libdir}/vpp_plugins/vmxnet3_plugin.so "
FILES_${PN}-plugin-perfmon = "  ${libdir}/vpp_plugins/perfmon_plugin.so "
FILES_${PN}-plugin-nsim = " ${libdir}/vpp_plugins/nsim_plugin.so"

FILES_${PN}-plugin-lb_test = "  ${libdir}/vpp_api_test_plugins/lb_test_plugin.so "
FILES_${PN}-plugin-ioam_vxlan_gpe_test = "  ${libdir}/vpp_api_test_plugins/ioam_vxlan_gpe_test_plugin.so "
FILES_${PN}-plugin-map_test = "  ${libdir}/vpp_api_test_plugins/map_test_plugin.so "
FILES_${PN}-plugin-avf_test = "  ${libdir}/vpp_api_test_plugins/avf_test_plugin.so "
FILES_${PN}-plugin-mactime_test = "  ${libdir}/vpp_api_test_plugins/mactime_test_plugin.so "
FILES_${PN}-plugin-udp_ping_test = "  ${libdir}/vpp_api_test_plugins/udp_ping_test_plugin.so "
FILES_${PN}-plugin-ioam_trace_test = "  ${libdir}/vpp_api_test_plugins/ioam_trace_test_plugin.so "
FILES_${PN}-plugin-lacp_test = "  ${libdir}/vpp_api_test_plugins/lacp_test_plugin.so "
FILES_${PN}-plugin-stn_test = "  ${libdir}/vpp_api_test_plugins/stn_test_plugin.so "
FILES_${PN}-plugin-memif_test = "  ${libdir}/vpp_api_test_plugins/memif_test_plugin.so "
FILES_${PN}-plugin-nat_test = "  ${libdir}/vpp_api_test_plugins/nat_test_plugin.so "
FILES_${PN}-plugin-acl_test = "  ${libdir}/vpp_api_test_plugins/acl_test_plugin.so "
FILES_${PN}-plugin-ioam_export_test = "  ${libdir}/vpp_api_test_plugins/ioam_export_test_plugin.so "
FILES_${PN}-plugin-flowprobe_test = "  ${libdir}/vpp_api_test_plugins/flowprobe_test_plugin.so "
FILES_${PN}-plugin-ioam_pot_test = "  ${libdir}/vpp_api_test_plugins/ioam_pot_test_plugin.so "
FILES_${PN}-plugin-vxlan_gpe_ioam_export_test = "  ${libdir}/vpp_api_test_plugins/vxlan_gpe_ioam_export_test_plugin.so "
FILES_${PN}-plugin-pppoe_test = "  ${libdir}/vpp_api_test_plugins/pppoe_test_plugin.so "
FILES_${PN}-plugin-gtpu_test = "  ${libdir}/vpp_api_test_plugins/gtpu_test_plugin.so "
FILES_${PN}-plugin-cdp_test = "  ${libdir}/vpp_api_test_plugins/cdp_test_plugin.so "
FILES_${PN}-plugin-dpdk_test = "  ${libdir}/vpp_api_test_plugins/dpdk_test_plugin.so "
FILES_${PN}-plugin-nsh_test = "  ${libdir}/vpp_api_test_plugins/nsh_test_plugin.so "
FILES_${PN}-plugin-ioam_test = "  ${libdir}/vpp_api_test_plugins/ioam_test_plugin.so "
FILES_${PN}-plugin-vmxnet3_test = "  ${libdir}/vpp_api_test_plugins/vmxnet3_test_plugin.so "
FILES_${PN}-plugin-nsim_test = "  ${libdir}/vpp_api_test_plugins/nsim_test_plugin.so "


FILES_${PN}-data = " \
		${datadir}/vpp/api/core/sctp.api.json \
		${datadir}/vpp/api/core/sr.api.json \
		${datadir}/vpp/api/core/gre.api.json \
		${datadir}/vpp/api/core/lisp.api.json \
		${datadir}/vpp/api/core/netmap.api.json \
		${datadir}/vpp/api/core/tcp.api.json \
		${datadir}/vpp/api/core/ipsec_gre.api.json \
		${datadir}/vpp/api/core/bfd.api.json \
		${datadir}/vpp/api/core/tap.api.json \
		${datadir}/vpp/api/core/oam.api.json \
		${datadir}/vpp/api/core/vpe.api.json \
		${datadir}/vpp/api/core/bier.api.json \
		${datadir}/vpp/api/core/ipsec.api.json \
		${datadir}/vpp/api/core/af_packet.api.json \
		${datadir}/vpp/api/core/vxlan_gpe.api.json \
		${datadir}/vpp/api/core/session.api.json \
		${datadir}/vpp/api/core/one.api.json \
		${datadir}/vpp/api/core/sr_mpls.api.json \
		${datadir}/vpp/api/core/feature.api.json \
		${datadir}/vpp/api/core/p2p_ethernet.api.json \
		${datadir}/vpp/api/core/memclnt.api.json \
		${datadir}/vpp/api/core/pg.api.json \
		${datadir}/vpp/api/core/l2.api.json \
		${datadir}/vpp/api/core/punt.api.json \
		${datadir}/vpp/api/core/classify.api.json \
		${datadir}/vpp/api/core/policer.api.json \
		${datadir}/vpp/api/core/span.api.json \
		${datadir}/vpp/api/core/udp.api.json \
		${datadir}/vpp/api/core/vxlan.api.json \
		${datadir}/vpp/api/core/mpls.api.json \
		${datadir}/vpp/api/core/qos.api.json \
		${datadir}/vpp/api/core/ip.api.json \
		${datadir}/vpp/api/core/ipip.api.json \
		${datadir}/vpp/api/core/bond.api.json \
		${datadir}/vpp/api/core/dns.api.json \
		${datadir}/vpp/api/core/tapv2.api.json \
		${datadir}/vpp/api/core/pipe.api.json \
		${datadir}/vpp/api/core/dhcp6_pd_client_cp.api.json \
		${datadir}/vpp/api/core/l2tp.api.json \
		${datadir}/vpp/api/core/lisp_gpe.api.json \
		${datadir}/vpp/api/core/cop.api.json \
		${datadir}/vpp/api/core/geneve.api.json \
		${datadir}/vpp/api/core/stats.api.json \
		${datadir}/vpp/api/core/rd_cp.api.json \
		${datadir}/vpp/api/core/dhcp.api.json \
		${datadir}/vpp/api/core/lldp.api.json \
		${datadir}/vpp/api/core/dhcp6_ia_na_client_cp.api.json \
		${datadir}/vpp/api/core/ipfix_export.api.json \
		${datadir}/vpp/api/core/vhost_user.api.json \
		${datadir}/vpp/api/core/interface.api.json \
		${datadir}/vpp/C.py \
                ${datadir}/vpp/api/core/flow.api.json \
                ${datadir}/vpp/api/core/map.api.json  \
                ${datadir}/vpp/api/core/dpdk.api.json \    
		${datadir}/vpp/JSON.py  \
                ${datadir}/vpp/vppapigen_json.py \
                ${datadir}/vpp/vppapigen_c.py "


FILES_${PN} = " \
		${sysconfdir}/vpp \
		${sysconfdir}/vpp/startup.conf \
		${sysconfdir}/vpp/startup.conf.dflt \
		${sysconfdir}/rc.local \
		${bindir}/svmdbtool \
		${bindir}/svmtool \
		${bindir}/vppapigen \
                ${bindir}/vpp_get_stats \
                ${bindir}/vpp_prometheus_export \
		${bindir}/elftool \
		${bindir}/cpelatency \
		${bindir}/cpelinreg \
		${bindir}/elog_merge \
		${bindir}/c2cpel  \
		${bindir}/cpelstate  \
		${bindir}/cpeldump \
		${bindir}/vpp \
		${bindir}/vpp_api_test \
		${bindir}/vpp_get_metrics \
		${bindir}/vpp_restart \
		${bindir}/vppctl \
		${libdir}/libcperf.so.0.0.0 \
		${libdir}/libcperf.so.0 \
		${libdir}/libsvm.so.0 \
		${libdir}/libsvm.so.0.0.0 \
		${libdir}/libsvmdb.so.0 \
		${libdir}/libsvmdb.so.0.0.0 \
		${libdir}/libvapiclient.so.0 \
		${libdir}/libvapiclient.so.0.0.0 \
		${libdir}/libvatplugin.so.0 \
		${libdir}/libvatplugin.so.0.0.0 \
		${libdir}/libvcl_ldpreload.so.0 \
		${libdir}/libvcl_ldpreload.so.0.0.0 \
		${libdir}/libvlib.so.0 \
		${libdir}/libvlib.so.0.0.0 \
		${libdir}/libvlibmemory.so.0 \
		${libdir}/libvlibmemory.so.0.0.0 \
		${libdir}/libvlibmemoryclient.so.0 \
		${libdir}/libvlibmemoryclient.so.0.0.0 \
		${libdir}/libvnet.so.0 \
		${libdir}/libvnet.so.0.0.0 \
		${libdir}/libvppapiclient.so.0 \
		${libdir}/libvppapiclient.so.0.0.0 \
		${libdir}/libvppcom.so.19.09 \
                ${libdir}/libvppapiclient.so.19.09  \
                ${libdir}/libsvm.so.19.09 \
                ${libdir}/libvcl_ldpreload.so.19.09 \
                ${libdir}/libvlibmemoryclient.so.19.09 \
                ${libdir}/libvnet.so.19.09 \
                ${libdir}/libvapiclient.so.19.09 \
                ${libdir}/libvlibmemory.so.19.09 \
                ${libdir}/libvppinfra.so.19.09 \
                ${libdir}/usr/lib/libvppcom.so.19.09 \
                ${libdir}/libvlib.so.19.09 \
                ${libdir}/libvatplugin.so.19.09 \
                ${libdir}/libsvmdb.so.19.09 \
		${libdir}/libvppcom.so.0.0.0 \
		${libdir}/libvppinfra.so.0 \
		${libdir}/libvppinfra.so.0.0.0 \
		"

FILES_${PN}-plugin-data = " \
		${datadir}/vpp/api/plugins/pot.api.json \
		${datadir}/vpp/api/plugins/pppoe.api.json \
		${datadir}/vpp/api/plugins/igmp.api.json \
		${datadir}/vpp/api/plugins/avf.api.json \
		${datadir}/vpp/api/plugins/mactime.api.json \
		${datadir}/vpp/api/plugins/gbp.api.json \
		${datadir}/vpp/api/plugins/map.api.json \
		${datadir}/vpp/api/plugins/udp_ping.api.json \
		${datadir}/vpp/api/plugins/l2e.api.json \
		${datadir}/vpp/api/plugins/ioam_export.api.json \
		${datadir}/vpp/api/plugins/lb.api.json \
		${datadir}/vpp/api/plugins/vxlan_gpe_ioam_export.api.json \
		${datadir}/vpp/api/plugins/ioam_vxlan_gpe.api.json \
		${datadir}/vpp/api/plugins/acl.api.json \
		${datadir}/vpp/api/plugins/ioam_cache.api.json \
		${datadir}/vpp/api/plugins/stn.api.json \
		${datadir}/vpp/api/plugins/abf.api.json \
		${datadir}/vpp/api/plugins/nat.api.json \
		${datadir}/vpp/api/plugins/flowprobe.api.json \
		${datadir}/vpp/api/plugins/memif.api.json \
		${datadir}/vpp/api/plugins/lacp.api.json \
		${datadir}/vpp/api/plugins/gtpu.api.json \
		${datadir}/vpp/api/plugins/trace.api.json \
		${datadir}/vpp/api/plugins/dpdk.api.json \
		${datadir}/vpp/api/plugins/cdp.api.json \
                ${datadir}/vpp/api/plugins/nsh.api.json \
                ${datadir}/vpp/api/plugins/nsim.api.json \
                ${datadir}/vpp/api/plugins/svs.api.json \
                ${datadir}/vpp/api/plugins/vmxnet3.api.json \
                ${datadir}/vpp/api/core/syslog.api.json \
                ${datadir}/vpp/api/core/vxlan_gbp.api.json \
                ${datadir}/vpp/plugins/perfmon/PerfmonTables.tar.xz "

FILES_${PN}-dev = " \
		${includedir}/* \
		${libdir}/libvcl_ldpreload.so \
		${libdir}/libvppapiclient.so \
		${libdir}/libvppcom.so \
		${libdir}/libvlib.so \
		${libdir}/libsvm.so \
		${libdir}/libsvmdb.so \
		${libdir}/libvatplugin.so \
		${libdir}/libvlibmemory.so \
		${libdir}/libvapiclient.so \
		${libdir}/libvnet.so \
		${libdir}/libvppinfra.so \
		${libdir}/libcperf.so \
		${libdir}/libvlibmemoryclient.so  \
                ${libdir}/cmake/vpp/*.cmake "

