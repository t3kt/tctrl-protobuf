TEMPLATE = subdirs

SUBDIRS += \
    qtctrlapp \
    qtctrlcore \
    qtctrlgui

qtctrlapp.subdir = qtctrlapp
qtctrlcore.subdir = qtctrlcore
qtctrlgui.subdir = qtctrlgui

qtctrlapp.depends = qtctrlcore
qtctrlgui.depends = qtctrlcore

win32:CONFIG(release, debug|release): LIBS += -L$$PWD/../../../etc/protobuf-3.3.0/cmake/build/release/ -llibprotobufd
else:win32:CONFIG(debug, debug|release): LIBS += -L$$PWD/../../../etc/protobuf-3.3.0/cmake/build/debug/ -llibprotobufd
else:unix: LIBS += -L$$PWD/../../../etc/protobuf-3.3.0/cmake/build/ -llibprotobufd

INCLUDEPATH += $$PWD/../../../etc/protobuf-3.3.0/src
DEPENDPATH += $$PWD/../../../etc/protobuf-3.3.0/src

win32-g++:CONFIG(release, debug|release): PRE_TARGETDEPS += $$PWD/../../../etc/protobuf-3.3.0/cmake/build/release/liblibprotobufd.a
else:win32-g++:CONFIG(debug, debug|release): PRE_TARGETDEPS += $$PWD/../../../etc/protobuf-3.3.0/cmake/build/debug/liblibprotobufd.a
else:win32:!win32-g++:CONFIG(release, debug|release): PRE_TARGETDEPS += $$PWD/../../../etc/protobuf-3.3.0/cmake/build/release/libprotobufd.lib
else:win32:!win32-g++:CONFIG(debug, debug|release): PRE_TARGETDEPS += $$PWD/../../../etc/protobuf-3.3.0/cmake/build/debug/libprotobufd.lib
else:unix: PRE_TARGETDEPS += $$PWD/../../../etc/protobuf-3.3.0/cmake/build/liblibprotobufd.a
