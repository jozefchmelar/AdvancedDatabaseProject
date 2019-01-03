package application.view

import application.controller.*
import application.model.*
import javafx.scene.layout.*
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
                    tableview(controller.companies) {
                        smartResize()
                        column("Nazov", CompanyModel::nazov).apply { isSortable = false }
                        column("Ico", CompanyModel::ico).apply { isSortable = false }
                        column("Kontakt", CompanyModel::kontakt).apply { isSortable = false }
                    }
                    borderpane {
                        left = button("Older") {
                            addClass("button-flat")
                            action { controller.getLess() }
                        }
                        center = label(controller.page)
                        right = button("Newer") {
                            addClass("button-flat")
                            action { controller.getMore() }
                        }

                    }
                    button("Get") { action { runAsync { controller.get() } } }
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
                        createdCompany.commit()
                        controller.saveCompany(createdCompany.item)
                    }
                }
            }
        }
    }

}

