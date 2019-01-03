package application.view

import application.controller.*
import application.model.*
import javafx.geometry.*
import javafx.scene.layout.*
import tableviewpag
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
                    tableviewpag(controller.people) {
                        smartResize()
                        column("First name", PersonModel::meno).apply { isSortable = false }
                        column("Last name", PersonModel::priezvisko).apply { isSortable = false }
                        column("Birth nubmer", PersonModel::rodCislo).apply { isSortable = false }
                        column("Contatc", PersonModel::kontakt).apply { isSortable = false }
                    }

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
