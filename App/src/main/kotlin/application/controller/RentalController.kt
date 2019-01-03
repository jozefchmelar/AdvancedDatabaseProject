package application.controller

import application.model.*
import javafx.collections.*

class RentalController : MyController() {
    val rentals = FXCollections.observableArrayList<RentalModel>()

    override fun get() {
        rentals.setAll(Db.connection.nacitajVypozicky("","",25,1).map(::RentalModel))
    }
}

