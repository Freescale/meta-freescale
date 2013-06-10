/****************************************************************************
**
** hacked by Eric Bénard - Eukréa Electromatique
** inspired from https://community.freescale.com/docs/DOC-94123
** and from fbset.c http://users.telenet.be/geertu/Linux/fbdev/
**
** based on qeglfshooks_imx6.cpp which is :
** Copyright (C) 2013 Digia Plc and/or its subsidiary(-ies).
** Contact: http://www.qt-project.org/legal
**
** This file is part of the qmake spec of the Qt Toolkit.
**
** $QT_BEGIN_LICENSE:LGPL$
** Commercial License Usage
** Licensees holding valid commercial Qt licenses may use this file in
** accordance with the commercial license agreement provided with the
** Software or, alternatively, in accordance with the terms contained in
** a written agreement between you and Digia.  For licensing terms and
** conditions see http://qt.digia.com/licensing.  For further information
** use the contact form at http://qt.digia.com/contact-us.
**
** GNU Lesser General Public License Usage
** Alternatively, this file may be used under the terms of the GNU Lesser
** General Public License version 2.1 as published by the Free Software
** Foundation and appearing in the file LICENSE.LGPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU Lesser General Public License version 2.1 requirements
** will be met: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.
**
** In addition, as a special exception, Digia gives you certain additional
** rights.  These rights are described in the Digia Qt LGPL Exception
** version 1.1, included in the file LGPL_EXCEPTION.txt in this package.
**
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU
** General Public License version 3.0 as published by the Free Software
** Foundation and appearing in the file LICENSE.GPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU General Public License version 3.0 requirements will be
** met: http://www.gnu.org/copyleft/gpl.html.
**
**
** $QT_END_LICENSE$
**
****************************************************************************/
#include <fcntl.h>    /* For O_RDWR */
#include <unistd.h>   /* For open(), creat() */
#include "qeglfshooks.h"
#include <EGL/egl.h>
#include <linux/fb.h>
#include <sys/ioctl.h>

QT_BEGIN_NAMESPACE

class QEglFSImx5Hooks : public QEglFSHooks
{
public:
    QEglFSImx5Hooks();
    virtual QSize screenSize() const;
    virtual EGLNativeWindowType createNativeWindow(const QSize &size, const QSurfaceFormat &format);
    virtual void destroyNativeWindow(EGLNativeWindowType window);

private:
    QSize mScreenSize;
    EGLNativeDisplayType mNativeDisplay;
};


QEglFSImx5Hooks::QEglFSImx5Hooks()
{
    int width, height;
    /* code taken from fbset.c */
    int fh;
    struct fb_var_screeninfo var;
    fh = open("/dev/fb0", O_RDONLY);
    ioctl(fh, FBIOGET_VSCREENINFO, &var);
    mScreenSize.setHeight(var.yres);
    mScreenSize.setWidth(var.xres);
    close(fh);
    mNativeDisplay = EGL_DEFAULT_DISPLAY;
}

QSize QEglFSImx5Hooks::screenSize() const
{
    return mScreenSize;
}

EGLNativeWindowType QEglFSImx5Hooks::createNativeWindow(const QSize &size, const QSurfaceFormat &format)
{
    Q_UNUSED(format);

    EGLNativeWindowType eglWindow =  open("/dev/fb0", O_RDWR);
    return eglWindow;
}


void QEglFSImx5Hooks::destroyNativeWindow(EGLNativeWindowType window)
{
    close(window);
}

QEglFSImx5Hooks eglFSImx5Hooks;
QEglFSHooks *platformHooks = &eglFSImx5Hooks;

QT_END_NAMESPACE
