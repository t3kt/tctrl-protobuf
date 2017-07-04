#pragma once

#include <QObject>
#include <QList>
#include <AppSchema.h>

class AppSchemaModel;

class SchemaNodeModel : public QObject {
  Q_OBJECT

    Q_PROPERTY(QString key READ key)
    Q_PROPERTY(QString label READ label)
    Q_PROPERTY(QString path READ path)
public:
  SchemaNodeModel(QObject *parent) : QObject(parent) {}
  virtual ~SchemaNodeModel() {}

  const QString& key() const { return _key; }
  const QString& label() const { return _label; }
  const QString& path() const { return _path; }
protected:
  QString _key;
  QString _label;
  QString _path;
};

class ModuleSchemaModel : public SchemaNodeModel
{
  Q_OBJECT

public:
  ModuleSchemaModel(
    SchemaNodeModel *parentNode,
    ModuleSchemaModel *parentModule,
    AppSchemaModel *appSchemaModel,
    ModuleSchemaPtr schema);
  virtual ~ModuleSchemaModel();

private:
  ModuleSchemaPtr _schema;
  ModuleSchemaModel *_parentModule;
  AppSchemaModel *_appSchemaModel;
  QList<ModuleSchemaModel*> _childModules;
};

class AppSchemaModel : public SchemaNodeModel
{
  Q_OBJECT

public:
  AppSchemaModel(
    QObject *parentNode,
    AppSchemaPtr schema);
  virtual ~AppSchemaModel();

private:
  AppSchemaPtr _schema;
  QList<ModuleSchemaModel*> _childModules;
};

class SchemaModel : public SchemaNodeModel
{
  Q_OBJECT

public:
    SchemaModel(QObject *parent);
    ~SchemaModel();
};
