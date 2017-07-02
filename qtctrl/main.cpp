#include "tctrlmainwindow.h"
#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    TctrlMainWindow w;
    w.show();

    return a.exec();
}
