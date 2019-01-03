package application.controller

import application.model.*
import javafx.collections.*
import model.*
import tornadofx.*

class PricingController : Controller() {

    fun save(item: Cennik?) {
        Db.connection.pridajCennik(item)
    }

    val pricing = TableModel<InvoiceModel> { Db.connection.nacitajCenniky("") }

}