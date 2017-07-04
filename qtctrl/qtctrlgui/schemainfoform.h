#ifndef SCHEMAINFOFORM_H
#define SCHEMAINFOFORM_H

#include <QWidget>
#include "AppSchema.h"

namespace Ui {
class SchemaInfoForm;
}

class SchemaInfoForm : public QWidget
{
    Q_OBJECT

public:
    explicit SchemaInfoForm(QWidget *parent = 0);
    ~SchemaInfoForm();

    void attachSchema(AppSchemaPtr appSchema);

private:
    Ui::SchemaInfoForm *ui;
    AppSchemaPtr _appSchema;
};

#endif // SCHEMAINFOFORM_H
