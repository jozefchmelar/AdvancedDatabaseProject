package application.view.Vehicles

import application.controller.*
import application.model.*
import javafx.scene.*
import tableviewpag
import tornadofx.*

class VehiclesWithouProfit : View("Vehicles without profit") {
    private val controller: VehiclesState by inject()

    override val root = borderpane {
        left = vbox {
            form {
                fieldset {
                    field("From") {
                        datepicker(controller.from)
                    }
                    field("To") {
                        datepicker(controller.to)
                    }
                }
                button("Get") {
                    action {
                        controller.vehiclesWithoutProfit.current()
                    }
                }
            }
        }
        center = vbox {
                tableviewpag(controller.vehiclesWithoutProfit){
                    smartResize()
                    column("id",VehicleModel::id)
                    column("SPZ",VehicleModel::spz)
                    column("Vynosy",VehicleModel::vynosy)
                }
        }
    }

}