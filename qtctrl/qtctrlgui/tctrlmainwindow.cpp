#include "tctrlmainwindow.h"
#include "ui_tctrlmainwindow.h"
#include <QFileDialog>
#include <QFile>
#include <QTextStream>
#include <QMessageBox>

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

void TctrlMainWindow::on_actionExit_triggered()
{
    QCoreApplication::exit();
}

void TctrlMainWindow::on_actionLoad_Schema_triggered()
{
    QString fileName = QFileDialog::getOpenFileName(this, tr("Open schema file"), "", tr("JSON Files (*.json);;All files (*)"));
    if (fileName.isEmpty()) {
        return;
    }
    QFile file(fileName);
    if (!file.open(QIODevice::ReadOnly | QIODevice::Text)) {
        QMessageBox::information(this, tr("Unable to open file"),
            file.errorString());
        return;
    }
    QTextStream input(&file);
//    QString jsonText = input.readAll();
    QString jsonText;
    while(!input.atEnd()) {
        jsonText += input.readLine() + "\n";
    }
    _appSchema = AppSchema::createFromJson(jsonText.toStdString());
    ui->schemaJsonTextBrowser->setText(jsonText);
}
