package application.controller

import application.model.*
import javafx.collections.*

class VehiclesController : MyController() {
    val vehicles = FXCollections.observableArrayList<VehicleModel>()

    override fun get() {
        vehicles.setAll(Db.connection.nacitajVozidla("").map(::VehicleModel))
    }
}

