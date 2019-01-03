package application.view

import application.controller.*
import application.model.*
import javafx.beans.property.*
import javafx.geometry.*
import javafx.scene.layout.*
import javafx.util.*
import tableviewpag
import tornadofx.*
import kotlin.reflect.jvm.*

class InvoicesView : View("Invoices") {
    private val controller: InvoiceController by inject()
    private val selectedInvoice = SimpleObjectProperty<InvoiceModel>()
    override val root = borderpane {
        padding = Insets(20.0)

        center = vbox {
            addClass("card")
            text("Invoices").addClass("card-title")
            tableviewpag(controller.invoices) {
                column("#", InvoiceModel::id).apply { isSortable = false }
                column("Created", InvoiceModel::datumVystavenia).apply { isSortable = false }
                column("Paid", InvoiceModel::datumZaplatenia).apply { isSortable = false }
                bindSelected(selectedInvoice)
            }
            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS
        }
        right = vbox {
//            label(selectedInvoice)
        }
    }
}

