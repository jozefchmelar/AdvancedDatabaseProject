package application.controller

import application.model.RentalModel
import tornadofx.Controller

class RentalController : Controller() {
    val rentals = TableModel { Db.connection.nacitajVypozicky("", "", 25, it).map(::RentalModel) }

    init {
        rentals.current()
    }
}

