package application.view.Rental

import application.controller.*
import application.model.*
import tableviewpag
import tornadofx.*
import kotlin.math.*

class RentalNewRentalView : View("Rental") {

    private val controller: RentalController by inject()
    private val costumer: CustomersController by inject()
    private val vehicles: VehiclesController by inject()
    private val selectedVehicle = VehicleModel().toProperty()

    val rental = RentalModel()

    override val root = borderpane {
        center =
                hbox {
                    paddingAll = 24.0

                    vbox {
                        paddingAll = 24.0

                        label("Who").addClass("card-title")
                        addClass("card")
                        checkbox("Renting to company") {
                            addClass("check-box")
                            selectedProperty().bindBidirectional(rental.isRentingCompany)
                        }
                        tableviewpag(costumer.companies) {
                            smartResize()
                            column("Nazov", CompanyModel::nazov).apply { isSortable = false }
                            column("Ico", CompanyModel::ico).apply { isSortable = false }
                            column("Kontakt", CompanyModel::kontakt).apply { isSortable = false }
                            disableProperty().bind(rental.isRentingCompany.select { (!it).toProperty() })
                            bindSelected(rental.company)
                        }
                        spacer()
                        separator { paddingAll = 24 }
                        spacer()
                        tableviewpag(costumer.people) {
                            smartResize()
                            column("First name", PersonModel::meno).apply { isSortable = false }
                            column("Last name", PersonModel::priezvisko).apply { isSortable = false }
                            column("Birth nubmer", PersonModel::rodCislo).apply { isSortable = false }
                            column("Contatc", PersonModel::kontakt).apply { isSortable = false }
                            disableProperty().bind(rental.isRentingCompany)
                            bindSelected(rental.person)
                        }
                    }

                    spacer()
                    vbox {
                        paddingAll = 24.0

                        label("Car").addClass("card-title")
                        addClass("card")
                        tableviewpag(vehicles.vehicles) {
                            column("spz", VehicleModel::spz)
                            column("znacka", VehicleModel::znacka)
                            column("typ", VehicleModel::typ)
                            column("Cena den", VehicleModel::cennik) {
                                converter(Conv { floor(it.cena_den).toString() + "$" })
                            }

                            bindSelected(selectedVehicle)
                            smartResize()
                            selectionModel.selectedItemProperty().onChange {
                                rental.vozidlo.value = it?.item
                            }
                        }
                        spacer()
                        separator { paddingAll = 24 }
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

                        label("Date").addClass("card-title")
                        addClass("card")
                        form {
                            fieldset {
                                field("From") {
                                    datepicker(rental.datumOD)
                                }
                                field("To") {
                                    datepicker(rental.datumDO)
                                }
                            }
                        }
                    }
                    spacer()
                    spacer()
                    vbox {
                        paddingAll = 24.0
                        label("Rent").addClass("card-title")
                        addClass("card")
                        borderpane {
                            center {
                                paddingAll = 24.0
                                vbox {
                                    button("Rent") {
                                        action {
                                            rental.commit()
                                            controller.rent(rental.item)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
    }
}
