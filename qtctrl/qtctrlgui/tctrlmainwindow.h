#ifndef TCTRLMAINWINDOW_H
#define TCTRLMAINWINDOW_H

#include <QMainWindow>
#include "AppSchema.h"

namespace Ui {
class TctrlMainWindow;
}

class TctrlMainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit TctrlMainWindow(QWidget *parent = 0);
    ~TctrlMainWindow();

private slots:
    void on_actionExit_triggered();

    void on_actionLoad_Schema_triggered();

private:
    Ui::TctrlMainWindow *ui;

    AppSchemaPtr _appSchema;
};

#endif // TCTRLMAINWINDOW_H
