package application.view.Customers

import application.controller.*
import application.model.*
import javafx.scene.layout.*
import tableviewpag
import tornadofx.*
import java.time.*

class CustomerCompanyView : View("Company") {

    private val controller: CustomersController by inject()

    private val createdCompany = CompanyModel()
    val selected = CompanyModel().toProperty()
    val from = LocalDate.now().toProperty()
    val to = LocalDate.now().toProperty()

    override val root = borderpane {
        paddingAll = 24.0

        center {
            hbox {
                paddingRight = 24
                vbox {
                    addClass("card")
                    text("Companies").addClass("card-title")

                    hgrow = Priority.ALWAYS
                    tableviewpag(controller.companies) {
                        smartResize()
                        column("Nazov", CompanyModel::nazov).apply { isSortable = false }
                        column("Ico", CompanyModel::ico).apply { isSortable = false }
                        column("Kontakt", CompanyModel::kontakt).apply { isSortable = false }
                        bindSelected(selected)
                    }

                }
            }

        }
        right {
            paddingLeft = 24
            minWidth = 300.0
            vbox {
                addClass("card")
                text("Add company").addClass("card-title")
                form {
                    fieldset {
                        field("name") {
                            textfield(createdCompany.nazov)
                        }
                        field("ico") {
                            textfield(createdCompany.ico)
                        }

                        field("Email") {
                            textfield(createdCompany.kontakt)
                        }
                    }
                }
                hbox {
                    button("Get") {
                        action {
                            createdCompany.commit()
                            controller.saveCompany(createdCompany.item)
                        }
                    }
                }
                separator()
                text("Pocet vypozicnych dni").addClass("card-title")
                form {
                    fieldset {
                        field("Od") {
                            datepicker(from)
                        }
                        field("Do") {
                            datepicker(to)
                        }
                        field("Pocet dni") {
                            textfield(controller.companyRentDays) {
                                isEditable = false
                            }
                        }
                        button("Get") {
                            action { controller.getCompanyRentDays(from.value.toDate(), to.value.toDate(),selected.value) }
                        }
                    }
                }
            }
        }

    }
}
