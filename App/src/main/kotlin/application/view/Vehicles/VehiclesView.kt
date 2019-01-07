package application.view.Vehicles

import application.controller.*
import application.model.*
import application.view.Rental.*
import tableviewpag
import tornadofx.*
import tornadofx.Stylesheet.Companion.selected
import java.time.*
import kotlin.math.*
class VehiclesView : View("Vehicles") {
    private val controller: VehiclesController by inject()
    val priceController: PricingController by inject()

    private val selectedVehicle = VehicleModel().toProperty()
    private val newMaintance = MaintanceModel()
    val from = LocalDate.now().toProperty()
    val to = LocalDate.now().toProperty()

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
                    column("Naklady", VehicleModel::naklady)
                    column("Vynosy", VehicleModel::vynosy)
                    column("Poruchovost", VehicleModel::poruchovost) {
                       //converter(Conv { (it).toString() + "%" })
                   }
                    column("Cena den", VehicleModel::cennik) {
                        converter(Conv { floor(it.cena_den).toString() + "$" })
                    }

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
                separator()
                hbox {
                    text("Pocet vypoziciek znacky ").addClass("card-title")
                    text(selectedVehicle.select { it?.znacka ?:"".toProperty() }).addClass("card-title")
                }
                form {
                    fieldset {
                        field("Od") {
                            datepicker(from)
                        }
                        field("Do") {
                            datepicker(to)
                        }
                        field("Vypozicky znacky") {
                            textfield(controller.znacky) {
                                isEditable = false
                            }
                        }
                        field("Pocet vypoziciek") {
                            textfield(controller.autoPocty) {
                                isEditable = false
                            }
                        }
                        button("Get") {
                           action { controller.getCounts(from.value.toDate(), to.value.toDate(), selectedVehicle.value) }
                        }
                    }

                }
            }

        }

    }
}