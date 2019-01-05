package application.view

import application.controller.*
import application.model.*
import javafx.geometry.*
import javafx.scene.layout.*
import tableviewpag
import tornadofx.*

class VehiclesView : View("Vehicles") {
    private val controller: VehiclesController by inject()
    private val selectedVehicle = VehicleModel().toProperty()

    override val root = borderpane {
        //   controller.get()
        padding = Insets(20.0)

        center = vbox {
            addClass("card")
            text("Vehicles").addClass("card-title")
            padding = Insets(20.0)

            tableviewpag(controller.vehicles) {

                column("id", VehicleModel::id)
                column("spz", VehicleModel::spz)
                column("znacka", VehicleModel::znacka)
                column("typ", VehicleModel::typ)
                bindSelected(selectedVehicle)
                smartResize()
            }

        }
        right = vbox {
            addClass("card")
            text("Maintance").addClass("card-title")

            tableview(selectedVehicle.select { it.udrzby }) {
                column("pocetKM", MaintanceModel::pocetKM)
                column("cena", MaintanceModel::cena)
                column("datumOD", MaintanceModel::datumOD)
                column("datumDO", MaintanceModel::datumDO)
                column("popis", MaintanceModel::popis)
                smartResize()
            }

            imageview(selectedVehicle.select { it.fotkaCesta }){

            }
        }

    }
}