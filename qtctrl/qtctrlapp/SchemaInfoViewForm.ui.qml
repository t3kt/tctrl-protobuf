import QtQuick 2.4
import QtQuick.Controls 2.2
import QtQuick.Controls 1.4

Item {
    width: 400
    height: 400

    Column {
        id: column
    }

    TextEdit {
        id: textEdit
        text: qsTr("Text Edit")
        font.pixelSize: 12
    }
}
