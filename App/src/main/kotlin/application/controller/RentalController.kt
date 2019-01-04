package application.controller

import application.model.RentalModel
import model.*
import tornadofx.Controller

class RentalController : Controller() {


    val rentals = TableModel {
        Db.connection.nacitajVypozicky("", "", 10, it).map(::RentalModel)
    }

    init {
        rentals.current()
    }
}

