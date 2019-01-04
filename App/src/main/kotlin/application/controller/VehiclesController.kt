package application.controller

import application.model.*
import javafx.collections.*
import tornadofx.*

class VehiclesController : Controller() {

    val vehicles = TableModel { Db.connection.nacitajVozidla("", "", 25, it)
            .map(::VehicleModel) }

    init {
        vehicles.current()
    }
}

