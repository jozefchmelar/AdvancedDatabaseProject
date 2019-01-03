package application.controller

import application.model.*
import javafx.collections.*

class InvoiceController : MyController() {

    val invoices = TableModel<InvoiceModel> { Db.connection.nacitajFaktury("","",25,1) }

    override fun get() {
        //  invoices.setAll(Db.connection.nacitajFaktury("").map(::InvoiceModel))
    }
}