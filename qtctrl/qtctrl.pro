TEMPLATE = subdirs

SUBDIRS += \
    qtctrlapp \
    qtctrlcore

qtctrlapp.subdir = qtctrlapp
qtctrlcore.subdir = qtctrlcore

qtctrlapp.depends = qtctrlcore
