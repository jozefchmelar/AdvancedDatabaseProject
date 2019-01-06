package application.controller

import application.model.RentalModel
import model.*
import tornadofx.Controller

class RentalController : Controller() {

    fun rent(item: Vypozicka) {
        Db.connection.pridajVypozicku(item)
        rentals.current()
    }


    val rentals = TableModel {
        Db.connection.nacitajVypozicky("", "", 10, it).map(::RentalModel)
    }

    init {
        rentals.current()
    }
}

