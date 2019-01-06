package application.view

import application.controller.*
import javafx.scene.*
import tornadofx.*

class VehicleState : View("Stete") {

    private val controller: VehiclesController by inject()

    override val root = vbox {
        piechart("Vozidla", controller.vehiclesChart) {
            animated = true

        }
    }
}
