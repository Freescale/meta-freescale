# Fixes compile errors like
# Source/WebCore/platform/graphics/egl/GLContextEGL.cpp:198:59: error: invalid 'static_cast' from type 'GLNativeWindowType' {aka 'long unsigned int'} to type 'EGLNativeWindowType' {aka 'wl_egl_window*'}
PACKAGECONFIG:remove:imxgpu3d = "opengl x11"
PACKAGECONFIG:append:imxgpu3d = " gles2"
