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
        info->setItem(0, 0, new QTableWidgetItem("-"));
        info->setItem(1, 0, new QTableWidgetItem("-"));
        info->setItem(2, 0, new QTableWidgetItem("-"));
    } else {
        info->setItem(0, 1, new QTableWidgetItem(appSchema->key()));
        info->setItem(0, 2, new QTableWidgetItem(appSchema->label()));
        info->setItem(0, 3, new QTableWidgetItem(appSchema->description()));
    }
}
