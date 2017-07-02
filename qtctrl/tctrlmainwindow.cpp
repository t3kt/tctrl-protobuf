#include "tctrlmainwindow.h"
#include "ui_tctrlmainwindow.h"
#include <QFile>
#include <QFileDialog>
#include <QMessageBox>
#include <QTextStream>

TctrlMainWindow::TctrlMainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::TctrlMainWindow)
{
    ui->setupUi(this);
}

TctrlMainWindow::~TctrlMainWindow()
{
    delete ui;
}

void TctrlMainWindow::on_actionLoadSchemaFromFile_triggered()
{
    QString fileName = QFileDialog::getOpenFileName(this, tr("Open Schema File"), QString(), tr("Json Files (*.json)"));

    if (!fileName.isEmpty()) {
        QFile file(fileName);
        if (!file.open(QIODevice::ReadOnly)) {
            QMessageBox::critical(this, tr("Error"), tr("Could not open schema file"));
            return;
        }
        QTextStream in(&file);
        //in.readAll();
        file.close();
    }
}

void TctrlMainWindow::on_actionExit_triggered()
{
    QCoreApplication::quit();
}
