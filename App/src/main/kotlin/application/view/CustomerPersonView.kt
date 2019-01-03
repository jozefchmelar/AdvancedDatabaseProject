package application.view

import application.controller.*
import application.model.*
import javafx.geometry.*
import javafx.scene.layout.*
import tornadofx.*

class CustomerPersonView : View("Person") {

    private val controller: CustomersController by inject()
    private val createdCustomer = PersonModel()

    override val root = borderpane {
        paddingAll = 24.0

        center {
            hbox {
            paddingRight=24
                vbox {
                    addClass("card")

                    text("Persons").addClass("card-title")
                    hgrow= Priority.ALWAYS
                    tableview(controller.companies) {
                        smartResize()
                        column("Nazov", CompanyModel::nazov).apply { isSortable = false }
                        column("Ico", CompanyModel::ico).apply { isSortable = false }
                        column("Kontakt", CompanyModel::kontakt).apply { isSortable = false }
                    }
                    borderpane {
                        left = button("Older").addClass("button-flat")
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
                text("Add person").addClass("card-title")
                form {
                    fieldset {
                        field("First name") {
                            textfield(createdCustomer.meno)
                        }
                        field("Last name") {
                            textfield(createdCustomer.priezvisko)
                        }
                        field("birth number") {
                            textfield(createdCustomer.rodCislo)
                        }
                        field("Email") {
                            textfield(createdCustomer.kontakt)
                        }
                    }
                }
                hbox {

                    button("Get") {
                        createdCustomer.commit()
                        controller.savePerson(createdCustomer.item)
                    }
                }
            }
        }
    }
}
