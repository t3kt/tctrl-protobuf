#ifndef TCTRLMAINWINDOW_H
#define TCTRLMAINWINDOW_H

#include <QMainWindow>

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
    void on_actionLoadSchemaFromFile_triggered();

    void on_actionExit_triggered();

private:
    Ui::TctrlMainWindow *ui;
};

#endif // TCTRLMAINWINDOW_H
