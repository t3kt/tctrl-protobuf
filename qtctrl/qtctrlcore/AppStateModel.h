#pragma once

#include <QObject>
#include <QString>
#include <QList>

class AppStateModel : public QObject {
  Q_OBJECT
public:
  AppStateModel(QObject* parent = Q_NULLPTR);
  virtual ~AppStateModel() {}
private:
};
