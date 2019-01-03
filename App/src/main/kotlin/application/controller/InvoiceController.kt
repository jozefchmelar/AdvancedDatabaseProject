package application.controller

import application.model.*
import javafx.collections.*
import tornadofx.Controller

class InvoiceController : Controller() {

    val invoices = TableModel { Db.connection.nacitajFaktury("","",10,it).map(::InvoiceModel) }

    init {
        invoices.current()
    }

}