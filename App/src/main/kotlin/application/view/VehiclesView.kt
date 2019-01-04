package application.view

import application.controller.*
import application.model.*
import javafx.geometry.*
import javafx.scene.layout.*
import tableviewpag
import tornadofx.*

class VehiclesView : View("Vehicles") {
    private val controller: VehiclesController by inject()

    override val root = borderpane {
        //   controller.get()
        padding = Insets(20.0)

        center = vbox {
            tableviewpag(controller.vehicles) {
                column("spz", VehicleModel::spz)
                column("znacka", VehicleModel::znacka)
                column("typ", VehicleModel::typ)
            }
        }


    }
}