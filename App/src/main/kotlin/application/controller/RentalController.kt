package application.controller

import application.model.RentalModel
import model.*
import tornadofx.Controller

class RentalController : Controller() {

    fun getDetails(vypozicka: Vypozicka) {

    }

    val rentals = TableModel {
        Db.connection.nacitajVypozicky("", "", 10, it).onEach {
            it.vozidlo = Db.connection.nacitajVozidla("where id=${it!!.vozidlo.id}", "", 2, 1).first()
        }.map(::RentalModel)
    }

    init {
        rentals.current()
    }
}

