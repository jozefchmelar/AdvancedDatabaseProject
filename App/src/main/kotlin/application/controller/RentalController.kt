package application.controller

import application.model.*
import javafx.collections.*

class RentalController : MyController() {
    val rentals = FXCollections.observableArrayList<RentalModel>()

    override fun get() {
      //  vehicles.setAll(Db.connection.nacitajVypozicky("").map(::RentalModel))
    }
}

