package application.view.Vehicles

import application.controller.*
import application.model.*
import javafx.scene.layout.*
import tableviewpag
import tornadofx.*

class VehicleState : View("State") {

    private val controller: VehiclesController by inject()
    private val selectedVehicleUsage = VehicleModel().toProperty()
    private val selectedVehicleFault = VehicleModel().toProperty()


    override val root = tabpane {
        tab("Stav") {
            paddingAll = 20.0

            borderpane {
              center{
                    hbox{

                        piechart("Vozidla", controller.vehiclesChart) {
                            animated = true
                            paddingAll = 20.0
                        }
                        spacer()
                        piechart("Vytazenost znaciek", controller.vytazenostZnaciek) {
                            animated = true
                            paddingAll = 20.0
                        }
                    }}
            }
        }
        tab("Vynosy") {
            paddingAll = 20.0

            borderpane {
                paddingAll = 20.0
                top = label(controller.vynosyTitle) { addClass("card-title") }
                center = vbox {
                    paddingAll = 20.0
                    tableview(controller.vynosy) {
                        vgrow= Priority.ALWAYS
                        column("spz", XmlVozidloModel::spz)
                        column("znacka", XmlVozidloModel::znacka)
                        column("typ", XmlVozidloModel::typ)
                        column("zarobok", XmlVozidloModel::zarobok)
                        smartResize()
                    }
                }
                right = vbox {
                    addClass("card")
                    paddingAll = 20.0

                    scrollpane{text(controller.vynosyXml)}
                }
            }
        }
        tab("Spolahlivost") {
            paddingAll = 20.0

            borderpane {
                paddingAll = 20.0
                top = label(controller.spolahlivostTitle) { addClass("card-title") }
                center = vbox {
                    paddingAll = 20.0
                    tableview(controller.spolahlivost) {
                        vgrow= Priority.ALWAYS
                        column("spz", XmlVozidloModel::spz)
                        column("znacka", XmlVozidloModel::znacka)
                        column("typ", XmlVozidloModel::typ)
                        column("dni prevadzky ", XmlVozidloModel::dniPrevadzky)
                        column("dni oprav ", XmlVozidloModel::dniOprav)
                        column("koeficient spolahlivosti", XmlVozidloModel::koeficientSpolahlivosti)
                        smartResize()
                    }
                }
                right = vbox {
                    addClass("card")
                    paddingAll = 20.0

                    scrollpane{text(controller.spolahlivostXml)}
                }
            }
        }
        tab("Poruchovost") {
            borderpane {
                center = tableviewpag(controller.vehiclesFault) {
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
        tab(VehiclesWithouProfit::class)
        tab(YearsState::class)
    }
}
