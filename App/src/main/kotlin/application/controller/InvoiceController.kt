package application.controller

import application.model.*
import javafx.collections.*

class InvoiceController : MyController() {

    val invoices = TableModel<InvoiceModel> { Db.connection.nacitajFaktury("") }

    override fun get() {
        //  invoices.setAll(Db.connection.nacitajFaktury("").map(::InvoiceModel))
    }
}