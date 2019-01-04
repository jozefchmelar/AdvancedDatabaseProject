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
                smartResize()
                column("#", InvoiceModel::id).apply { isSortable = false }
                column("Created", InvoiceModel::datumVystavenia).apply { isSortable = false }
                column("Paid", InvoiceModel::datumZaplatenia).apply { isSortable = false }
                column("Price", InvoiceModel::suma).apply { isSortable = false }
                column("Contact", InvoiceModel::vypozicka) {
                    isSortable = false
                    converter(Conv { it.zakaznik.kontakt })
                }
                column("Car SPZ", InvoiceModel::car) {
                    converter(Conv { it.spz })
                    isSortable = false
                }
                column("Car ", InvoiceModel::car) {
                    converter(Conv { it.znacka })
                    isSortable = false
                }
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

