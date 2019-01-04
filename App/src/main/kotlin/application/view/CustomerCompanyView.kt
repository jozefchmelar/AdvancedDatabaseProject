package application.view

import application.controller.*
import application.model.*
import javafx.scene.layout.*
import tableviewpag
import tornadofx.*

class CustomerCompanyView : View("Company") {

    private val controller: CustomersController by inject()

    private val createdCompany = CompanyModel()

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
            }
        }

    }
}
