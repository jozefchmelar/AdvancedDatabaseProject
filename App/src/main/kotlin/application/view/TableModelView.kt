import application.controller.*
import javafx.collections.*
import javafx.event.*
import javafx.scene.control.*
import tornadofx.*

fun <T> EventTarget.tableviewpag(items: TableModel<T>, op: TableView<T>.() -> Unit = {}) = vbox {

    tableview(items.list) {
        op()
    }
    borderpane {
        left = button("Older") {
            addClass("button-flat")
            action { items.less() }
        }
        center = label(items.page)
        right = button("Newer") {
            addClass("button-flat")
            action { items.more() }
        }

    }
}