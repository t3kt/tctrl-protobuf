#-------------------------------------------------
#
# Project created by QtCreator 2017-07-02T22:05:14
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = qtctrlgui
TEMPLATE = app

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
        main.cpp \
        tctrlmainwindow.cpp

HEADERS += \
        tctrlmainwindow.h

FORMS += \
        tctrlmainwindow.ui


INCLUDEPATH += \
    ../../cpp/tctrlcpp/src/schema \
    ../../cpp/gensrc \
    ../../cpp/lib \
    ../qtctrlcore \
    ../../../../etc/protobuf-3.3.0/src

win32:CONFIG(release, debug|release): LIBS += -L$$OUT_PWD/../qtctrlcore/release/ -lqtctrlcore
else:win32:CONFIG(debug, debug|release): LIBS += -L$$OUT_PWD/../qtctrlcore/debug/ -lqtctrlcore
else:unix: LIBS += -L$$OUT_PWD/../qtctrlcore/ -lqtctrlcore

INCLUDEPATH += $$PWD/../qtctrlcore
DEPENDPATH += $$PWD/../qtctrlcore

win32-g++:CONFIG(release, debug|release): PRE_TARGETDEPS += $$OUT_PWD/../qtctrlcore/release/libqtctrlcore.a
else:win32-g++:CONFIG(debug, debug|release): PRE_TARGETDEPS += $$OUT_PWD/../qtctrlcore/debug/libqtctrlcore.a
else:win32:!win32-g++:CONFIG(release, debug|release): PRE_TARGETDEPS += $$OUT_PWD/../qtctrlcore/release/qtctrlcore.lib
else:win32:!win32-g++:CONFIG(debug, debug|release): PRE_TARGETDEPS += $$OUT_PWD/../qtctrlcore/debug/qtctrlcore.lib
else:unix: PRE_TARGETDEPS += $$OUT_PWD/../qtctrlcore/libqtctrlcore.a

win32:CONFIG(release, debug|release): LIBS += -L$$OUT_PWD/../qtctrlcore/release/ -lqtctrlcore
else:win32:CONFIG(debug, debug|release): LIBS += -L$$OUT_PWD/../qtctrlcore/debug/ -lqtctrlcore
else:unix: LIBS += -L$$OUT_PWD/../qtctrlcore/ -lqtctrlcore

INCLUDEPATH += $$PWD/../qtctrlcore
DEPENDPATH += $$PWD/../qtctrlcore

win32-g++:CONFIG(release, debug|release): PRE_TARGETDEPS += $$OUT_PWD/../qtctrlcore/release/libqtctrlcore.a
else:win32-g++:CONFIG(debug, debug|release): PRE_TARGETDEPS += $$OUT_PWD/../qtctrlcore/debug/libqtctrlcore.a
else:win32:!win32-g++:CONFIG(release, debug|release): PRE_TARGETDEPS += $$OUT_PWD/../qtctrlcore/release/qtctrlcore.lib
else:win32:!win32-g++:CONFIG(debug, debug|release): PRE_TARGETDEPS += $$OUT_PWD/../qtctrlcore/debug/qtctrlcore.lib
else:unix: PRE_TARGETDEPS += $$OUT_PWD/../qtctrlcore/libqtctrlcore.a
