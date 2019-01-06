package application.view

import application.controller.*
import application.model.*
import javafx.geometry.*
import tableviewpag
import tornadofx.*

class VehiclesView : View("Vehicles") {
    private val controller: VehiclesController by inject()
    private val selectedVehicle = VehicleModel().toProperty()
    private val newMaintance = MaintanceModel()

    override val root = borderpane {
        //   controller.get()
        paddingAll = 24.0


        left = hbox {
            paddingAll = 24.0
            vbox {

                addClass("card")
                text("Vehicles").addClass("card-title")

                tableviewpag(controller.vehicles) {

                    column("id", VehicleModel::id)
                    column("spz", VehicleModel::spz)
                    column("znacka", VehicleModel::znacka)
                    column("typ", VehicleModel::typ)
                    bindSelected(selectedVehicle)
                    smartResize()
                }
                spacer()

            }
        }
        center = hbox {
            paddingAll = 24.0

            separator()

            vbox {
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
                spacer()
                borderpane {
                    center {
                        imageview(selectedVehicle.select { it.fotkaCesta }) {
                            isPreserveRatio = true
                            fitHeight = 300.0
                            fitWidth = 300.0
                        }
                    }
                }

            }
            spacer()
            vbox {
                paddingAll = 24.0

                addClass("card")
                text("New Maintance record").addClass("card-title")
                form {
                    fieldset {
                        field(" price") {
                            textfield(newMaintance.cena)
                        }
                        field("km") {
                            textfield(newMaintance.pocetKM)
                        }
                        field("From") {
                            datepicker(newMaintance.datumOD)
                        }
                        field("To") {
                            datepicker(newMaintance.datumDO)
                        }
                        field("popis") {
                            textfield(newMaintance.popis)
                        }
                    }
                    button("add") {
                        action {
                            newMaintance.commit()
                            controller.addMaintnace(selectedVehicle.value.item, newMaintance.item)
                        }
                    }
                }
            }

        }

    }
}