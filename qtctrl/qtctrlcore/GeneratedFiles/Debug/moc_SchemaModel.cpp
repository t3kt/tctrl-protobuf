/****************************************************************************
** Meta object code from reading C++ file 'SchemaModel.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.9.1)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "../../SchemaModel.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'SchemaModel.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.9.1. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
QT_WARNING_PUSH
QT_WARNING_DISABLE_DEPRECATED
struct qt_meta_stringdata_SchemaNodeModel_t {
    QByteArrayData data[4];
    char stringdata0[31];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_SchemaNodeModel_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_SchemaNodeModel_t qt_meta_stringdata_SchemaNodeModel = {
    {
QT_MOC_LITERAL(0, 0, 15), // "SchemaNodeModel"
QT_MOC_LITERAL(1, 16, 3), // "key"
QT_MOC_LITERAL(2, 20, 5), // "label"
QT_MOC_LITERAL(3, 26, 4) // "path"

    },
    "SchemaNodeModel\0key\0label\0path"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_SchemaNodeModel[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
       0,    0, // methods
       3,   14, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

 // properties: name, type, flags
       1, QMetaType::QString, 0x00095001,
       2, QMetaType::QString, 0x00095001,
       3, QMetaType::QString, 0x00095001,

       0        // eod
};

void SchemaNodeModel::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{

#ifndef QT_NO_PROPERTIES
    if (_c == QMetaObject::ReadProperty) {
        SchemaNodeModel *_t = static_cast<SchemaNodeModel *>(_o);
        Q_UNUSED(_t)
        void *_v = _a[0];
        switch (_id) {
        case 0: *reinterpret_cast< QString*>(_v) = _t->key(); break;
        case 1: *reinterpret_cast< QString*>(_v) = _t->label(); break;
        case 2: *reinterpret_cast< QString*>(_v) = _t->path(); break;
        default: break;
        }
    } else if (_c == QMetaObject::WriteProperty) {
    } else if (_c == QMetaObject::ResetProperty) {
    }
#endif // QT_NO_PROPERTIES
    Q_UNUSED(_o);
    Q_UNUSED(_id);
    Q_UNUSED(_c);
    Q_UNUSED(_a);
}

const QMetaObject SchemaNodeModel::staticMetaObject = {
    { &QObject::staticMetaObject, qt_meta_stringdata_SchemaNodeModel.data,
      qt_meta_data_SchemaNodeModel,  qt_static_metacall, nullptr, nullptr}
};


const QMetaObject *SchemaNodeModel::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *SchemaNodeModel::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_SchemaNodeModel.stringdata0))
        return static_cast<void*>(const_cast< SchemaNodeModel*>(this));
    return QObject::qt_metacast(_clname);
}

int SchemaNodeModel::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    
#ifndef QT_NO_PROPERTIES
   if (_c == QMetaObject::ReadProperty || _c == QMetaObject::WriteProperty
            || _c == QMetaObject::ResetProperty || _c == QMetaObject::RegisterPropertyMetaType) {
        qt_static_metacall(this, _c, _id, _a);
        _id -= 3;
    } else if (_c == QMetaObject::QueryPropertyDesignable) {
        _id -= 3;
    } else if (_c == QMetaObject::QueryPropertyScriptable) {
        _id -= 3;
    } else if (_c == QMetaObject::QueryPropertyStored) {
        _id -= 3;
    } else if (_c == QMetaObject::QueryPropertyEditable) {
        _id -= 3;
    } else if (_c == QMetaObject::QueryPropertyUser) {
        _id -= 3;
    }
#endif // QT_NO_PROPERTIES
    return _id;
}
struct qt_meta_stringdata_ModuleSchemaModel_t {
    QByteArrayData data[1];
    char stringdata0[18];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_ModuleSchemaModel_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_ModuleSchemaModel_t qt_meta_stringdata_ModuleSchemaModel = {
    {
QT_MOC_LITERAL(0, 0, 17) // "ModuleSchemaModel"

    },
    "ModuleSchemaModel"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_ModuleSchemaModel[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
       0,    0, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

       0        // eod
};

void ModuleSchemaModel::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    Q_UNUSED(_o);
    Q_UNUSED(_id);
    Q_UNUSED(_c);
    Q_UNUSED(_a);
}

const QMetaObject ModuleSchemaModel::staticMetaObject = {
    { &SchemaNodeModel::staticMetaObject, qt_meta_stringdata_ModuleSchemaModel.data,
      qt_meta_data_ModuleSchemaModel,  qt_static_metacall, nullptr, nullptr}
};


const QMetaObject *ModuleSchemaModel::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *ModuleSchemaModel::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_ModuleSchemaModel.stringdata0))
        return static_cast<void*>(const_cast< ModuleSchemaModel*>(this));
    return SchemaNodeModel::qt_metacast(_clname);
}

int ModuleSchemaModel::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = SchemaNodeModel::qt_metacall(_c, _id, _a);
    return _id;
}
struct qt_meta_stringdata_AppSchemaModel_t {
    QByteArrayData data[1];
    char stringdata0[15];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_AppSchemaModel_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_AppSchemaModel_t qt_meta_stringdata_AppSchemaModel = {
    {
QT_MOC_LITERAL(0, 0, 14) // "AppSchemaModel"

    },
    "AppSchemaModel"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_AppSchemaModel[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
       0,    0, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

       0        // eod
};

void AppSchemaModel::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    Q_UNUSED(_o);
    Q_UNUSED(_id);
    Q_UNUSED(_c);
    Q_UNUSED(_a);
}

const QMetaObject AppSchemaModel::staticMetaObject = {
    { &SchemaNodeModel::staticMetaObject, qt_meta_stringdata_AppSchemaModel.data,
      qt_meta_data_AppSchemaModel,  qt_static_metacall, nullptr, nullptr}
};


const QMetaObject *AppSchemaModel::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *AppSchemaModel::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_AppSchemaModel.stringdata0))
        return static_cast<void*>(const_cast< AppSchemaModel*>(this));
    return SchemaNodeModel::qt_metacast(_clname);
}

int AppSchemaModel::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = SchemaNodeModel::qt_metacall(_c, _id, _a);
    return _id;
}
struct qt_meta_stringdata_SchemaModel_t {
    QByteArrayData data[1];
    char stringdata0[12];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_SchemaModel_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_SchemaModel_t qt_meta_stringdata_SchemaModel = {
    {
QT_MOC_LITERAL(0, 0, 11) // "SchemaModel"

    },
    "SchemaModel"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_SchemaModel[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
       0,    0, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

       0        // eod
};

void SchemaModel::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    Q_UNUSED(_o);
    Q_UNUSED(_id);
    Q_UNUSED(_c);
    Q_UNUSED(_a);
}

const QMetaObject SchemaModel::staticMetaObject = {
    { &SchemaNodeModel::staticMetaObject, qt_meta_stringdata_SchemaModel.data,
      qt_meta_data_SchemaModel,  qt_static_metacall, nullptr, nullptr}
};


const QMetaObject *SchemaModel::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *SchemaModel::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_SchemaModel.stringdata0))
        return static_cast<void*>(const_cast< SchemaModel*>(this));
    return SchemaNodeModel::qt_metacast(_clname);
}

int SchemaModel::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = SchemaNodeModel::qt_metacall(_c, _id, _a);
    return _id;
}
QT_WARNING_POP
QT_END_MOC_NAMESPACE
