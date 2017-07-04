#include "SchemaModel.h"

SchemaModel::SchemaModel(QObject *parent)
    : SchemaNodeModel(parent)
{
}

SchemaModel::~SchemaModel()
{
}

ModuleSchemaModel::ModuleSchemaModel(
  SchemaNodeModel *parentNode,
  ModuleSchemaModel *parentModule,
  AppSchemaModel *appSchemaModel,
  ModuleSchemaPtr schema)
  : SchemaNodeModel(parentNode)
  , _schema(schema)
  , _parentModule(parentModule)
  , _appSchemaModel(appSchemaModel) {
  _key = QString::fromStdString(schema->key());
  _label = QString::fromStdString(schema->label());
  _path = QString::fromStdString(schema->path());

  for (auto& childSchema : schema->childModules()) {
    auto childModule = new ModuleSchemaModel(this, this, appSchemaModel, childSchema);
    _childModules.append(childModule);
  }
}

ModuleSchemaModel::~ModuleSchemaModel() {
  // ... should the child modules be cleaned up here or does QObject handle that?
}

AppSchemaModel::AppSchemaModel(
  QObject* parentNode,
  AppSchemaPtr schema)
  : SchemaNodeModel(parentNode)
  , _schema(schema) {
  _key = QString::fromStdString(schema->key());
  _label = QString::fromStdString(schema->label());
  _path = QString::fromStdString(schema->path());

  for (auto& childSchema : schema->childModules()) {
    auto childModule = new ModuleSchemaModel(this, nullptr, this, childSchema);
    _childModules.append(childModule);
  }
}

AppSchemaModel::~AppSchemaModel() {
  // cleanup...?
}
