#-------------------------------------------------
#
# Project created by QtCreator 2017-07-02T18:38:03
#
#-------------------------------------------------

QT       += gui

TARGET = qtctrlcore
TEMPLATE = lib
CONFIG += staticlib
CONFIG += c++14

# The following define makes your compiler emit warnings if you use
# any feature of Qt which as been marked as deprecated (the exact warnings
# depend on your compiler). Please consult the documentation of the
# deprecated API in order to know how to port your code away from it.
DEFINES += QT_DEPRECATED_WARNINGS

# You can also make your code fail to compile if you use deprecated APIs.
# In order to do so, uncomment the following line.
# You can also select to disable deprecated APIs only up to a certain version of Qt.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

SOURCES += \
        qtctrlcore.cpp \
    ../../cpp/tctrlcpp/src/schema/AppSchema.cpp \
    ../../cpp/tctrlcpp/src/schema/ModuleSchema.cpp \
    ../../cpp/tctrlcpp/src/schema/ParamSchema.cpp \
    ../../cpp/tctrlcpp/src/schema/SchemaNode.cpp \
    ../../cpp/tctrlcpp/src/schema/SchemaUtil.cpp \
    ../../cpp/gensrc/tctrl-schema.pb.cc \
    schemaitemmodel.cpp

HEADERS += \
        qtctrlcore.h \
    ../../cpp/tctrlcpp/src/schema/AppSchema.h \
    ../../cpp/tctrlcpp/src/schema/Common.h \
    ../../cpp/tctrlcpp/src/schema/ModuleSchema.h \
    ../../cpp/tctrlcpp/src/schema/ParamSchema.h \
    ../../cpp/tctrlcpp/src/schema/SchemaNode.h \
    ../../cpp/tctrlcpp/src/schema/SchemaUtil.h \
    ../../cpp/gensrc/tctrl-schema.pb.h \
    schemaitemmodel.h
unix {
    target.path = /usr/lib
    INSTALLS += target
}

INCLUDEPATH += \
    ../../cpp/tctrlcpp/src/schema \
    ../../cpp/gensrc \
    ../../cpp/lib

win32:CONFIG(release, debug|release): LIBS += -L$$PWD/../../../../etc/protobuf-3.3.0/cmake/build/release/ -llibprotobufd
else:win32:CONFIG(debug, debug|release): LIBS += -L$$PWD/../../../../etc/protobuf-3.3.0/cmake/build/debug/ -llibprotobufd
else:unix: LIBS += -L$$PWD/../../../../etc/protobuf-3.3.0/cmake/build/ -llibprotobufd

INCLUDEPATH += $$PWD/../../../../etc/protobuf-3.3.0/src
DEPENDPATH += $$PWD/../../../../etc/protobuf-3.3.0/src

win32-g++:CONFIG(release, debug|release): PRE_TARGETDEPS += $$PWD/../../../../etc/protobuf-3.3.0/cmake/build/release/liblibprotobufd.a
else:win32-g++:CONFIG(debug, debug|release): PRE_TARGETDEPS += $$PWD/../../../../etc/protobuf-3.3.0/cmake/build/debug/liblibprotobufd.a
else:win32:!win32-g++:CONFIG(release, debug|release): PRE_TARGETDEPS += $$PWD/../../../../etc/protobuf-3.3.0/cmake/build/release/libprotobufd.lib
else:win32:!win32-g++:CONFIG(debug, debug|release): PRE_TARGETDEPS += $$PWD/../../../../etc/protobuf-3.3.0/cmake/build/debug/libprotobufd.lib
else:unix: PRE_TARGETDEPS += $$PWD/../../../../etc/protobuf-3.3.0/cmake/build/liblibprotobufd.a
