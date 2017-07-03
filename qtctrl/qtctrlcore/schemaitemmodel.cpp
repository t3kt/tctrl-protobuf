#include "schemaitemmodel.h"

SchemaItemModel::SchemaItemModel(QObject *parent)
    : QAbstractItemModel(parent)
{
}

QVariant SchemaItemModel::headerData(int section, Qt::Orientation orientation, int role) const
{
    // FIXME: Implement me!
  return QVariant();
}

QModelIndex SchemaItemModel::index(int row, int column, const QModelIndex &parent) const
{
    // FIXME: Implement me!
  return QModelIndex();
}

QModelIndex SchemaItemModel::parent(const QModelIndex &index) const
{
    // FIXME: Implement me!
    return QModelIndex();
}

int SchemaItemModel::rowCount(const QModelIndex &parent) const
{
    if (!parent.isValid())
        return 0;

    // FIXME: Implement me!
    return -1;
}

int SchemaItemModel::columnCount(const QModelIndex &parent) const
{
    if (!parent.isValid())
        return 0;

    // FIXME: Implement me!
    return -1;
}

QVariant SchemaItemModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    // FIXME: Implement me!
    return QVariant();
}

enum SchemaItemColumn {
  COL_NAME,
  COL_NODE_TYPE,

  NUM_COLUMNS
};

QStandardItemModel* SchemaTreeModelBuilder::build() {
  
  auto model = new QStandardItemModel();

  auto appItem = new QStandardItem();
  appItem->setColumnCount(NUM_COLUMNS);
  QList<QStandardItem*> infoItems;
  infoItems << new QStandardItem("(app)");
  appItem->appendRow(infoItems);

  model->appendRow(QList<QStandardItem*>{appItem});
  //TODO
  return model;
}

QStandardItem* SchemaTreeModelBuilder::buildModule(
  const ModuleSchema& mod,
  QStandardItem* parent) {

  //TODO
  return nullptr;
}

QStandardItem* SchemaTreeModelBuilder::buildParam(
  const ParamSchema& mod,
  QStandardItem* parent) {

  //TODO
  return nullptr;
}
