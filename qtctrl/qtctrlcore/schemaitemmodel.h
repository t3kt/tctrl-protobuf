#ifndef SCHEMAITEMMODEL_H
#define SCHEMAITEMMODEL_H

#include <QAbstractItemModel>
#include <QStandardItemModel>
#include <QStandardItem>
#include "AppSchema.h"
#include "SchemaNode.h"

class SchemaTreeItem : public QObject {
  Q_OBJECT
public:
  virtual SchemaTreeItem* child(int row) { return nullptr; }
  virtual int childCount() const { return 0; }
  virtual int columnCount() const { return 1; }
  virtual QVariant data(int column) const { return QVariant(); }
private:
};

class AppSchemaItem : public SchemaTreeItem {
  Q_OBJECT
public:
private:
};

class ModuleSchemaItem : public SchemaTreeItem {
  Q_OBJECT
public:
  

private:
  const AppSchemaItem* _hostApp;
  const ModuleSchemaItem* _parentModule;
};

class SchemaItemModel : public QAbstractItemModel
{
    Q_OBJECT

public:
    explicit SchemaItemModel(QObject *parent = nullptr);

    // Header:
    QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;

    // Basic functionality:
    QModelIndex index(int row, int column,
                      const QModelIndex &parent = QModelIndex()) const override;
    QModelIndex parent(const QModelIndex &index) const override;

    int rowCount(const QModelIndex &parent = QModelIndex()) const override;
    int columnCount(const QModelIndex &parent = QModelIndex()) const override;

    QVariant data(const QModelIndex &index, int role = Qt::DisplayRole) const override;

private:
};

class SchemaTreeModelBuilder {
public:
  SchemaTreeModelBuilder(const AppSchema& appSchema) : _appSchema(appSchema) {}

  QStandardItemModel* build();
private:
  QStandardItem* buildModule(const ModuleSchema& mod, QStandardItem* parent);
  QStandardItem* buildParam(const ParamSchema& mod, QStandardItem* parent);

  const AppSchema& _appSchema;
};

#endif // SCHEMAITEMMODEL_H
