package application.view

import application.controller.*
import application.model.*
import javafx.scene.*
import tableviewpag
import tornadofx.*

class VehicleState : View("Stete") {

    private val controller: VehiclesController by inject()
    private val selectedVehicleUsage = VehicleModel().toProperty()
    private val selectedVehicleFault = VehicleModel().toProperty()


    override val root = tabpane {

        tab("Stav") {
            paddingAll = 20.0

            borderpane {
                center = piechart("Vozidla", controller.vehiclesChart) {
                    animated = true
                }
            }
        }
        tab("Vytazenost"){
            paddingAll = 20.0

            borderpane {
                center = tableviewpag( controller.vehiclesUsage) {
                    column("spz", VehicleModel::spz)
                    column("znacka", VehicleModel::znacka)
                    column("typ", VehicleModel::typ)
                    column("vytazenost", VehicleModel::vytazenost)
                    bindSelected(selectedVehicleUsage)
                    smartResize()

                }
                right=
                    imageview(selectedVehicleUsage.select { it.fotkaCesta }) {
                        isPreserveRatio = true
                        fitHeight = 300.0
                        fitWidth = 300.0
                }
            }
        }
        tab("Poruchovost"){
            borderpane {
                center = tableviewpag( controller.vehiclesFault) {
                    column("spz", VehicleModel::spz)
                    column("znacka", VehicleModel::znacka)
                    column("typ", VehicleModel::typ)
                    column("poruchovost", VehicleModel::poruchovost)
                    bindSelected(selectedVehicleFault)
                    smartResize()

                }
                right =
                    imageview(selectedVehicleFault.select { it.fotkaCesta }) {
                        isPreserveRatio = true
                        fitHeight = 300.0
                        fitWidth = 300.0

                }
            }

        }
    }
}
