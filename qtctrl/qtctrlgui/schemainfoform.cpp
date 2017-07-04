#include "schemainfoform.h"
#include "ui_schemainfoform.h"

SchemaInfoForm::SchemaInfoForm(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::SchemaInfoForm)
{
    ui->setupUi(this);
}

SchemaInfoForm::~SchemaInfoForm()
{
    delete ui;
}

void SchemaInfoForm::attachSchema(AppSchemaPtr appSchema) {
    _appSchema = appSchema;
    auto info = ui->infoTable;
    if (_appSchema) {
        info->setItem(0, 0, new QTableWidgetItem(tr("-")));
        info->setItem(1, 0, new QTableWidgetItem(tr("-")));
        info->setItem(2, 0, new QTableWidgetItem(tr("-")));
    } else {
        info->setItem(0, 1, new QTableWidgetItem(QString::fromStdString(appSchema->key())));
        info->setItem(0, 2, new QTableWidgetItem(QString::fromStdString(appSchema->label())));
        info->setItem(0, 3, new QTableWidgetItem(QString::fromStdString(appSchema->description())));
    }
}
