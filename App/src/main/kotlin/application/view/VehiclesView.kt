package application.view

import application.controller.*
import application.model.*
import javafx.geometry.*
import javafx.scene.layout.*
import tornadofx.*

class VehiclesView : View("Vehicles") {
    private val controller: VehiclesController by inject()

    override val root = borderpane {
     //   controller.get()
        padding = Insets(20.0)

        center = vbox {
            addClass("card")
            text("Vehicles").addClass("card-title")
            tableview(controller.vehicles) {
                vgrow = Priority.ALWAYS
                hgrow = Priority.ALWAYS
                smartResize()
                column("Nazov", VehicleModel::spz).apply { isSortable = false }
            }
            button("Get"){action{ controller.get()}}
            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS
        }
    }
}