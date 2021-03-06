package application.view.Customers

import application.controller.*
import application.model.*
import javafx.scene.layout.*
import tableviewpag
import tornadofx.*
import java.time.*

class CustomerPersonView : View("Person") {

    private val controller: CustomersController by inject()
    private val createdCustomer = PersonModel()

    val selected = PersonModel().toProperty()
    val from = LocalDate.now().toProperty()
    val to = LocalDate.now().toProperty()

    override val root = borderpane {
        paddingAll = 24.0

        center {
            hbox {
                paddingRight = 24
                vbox {
                    addClass("card")

                    text("Persons").addClass("card-title")
                    hgrow = Priority.ALWAYS
                    tableviewpag(controller.people) {
                        smartResize()
                        column("First name", PersonModel::meno).apply { isSortable = false }
                        column("Last name", PersonModel::priezvisko).apply { isSortable = false }
                        column("Birth nubmer", PersonModel::rodCislo).apply { isSortable = false }
                        column("Contatc", PersonModel::kontakt).apply { isSortable = false }
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
                        action {
                            createdCustomer.commit()
                            controller.savePerson(createdCustomer.item)
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
                            textfield(controller.peopleRentDays) {
                                isEditable = false
                            }
                        }
                        button("Get") {
                            action { controller.getPersonRentaDate(from.value.toDate(), to.value.toDate(), selected.value) }
                        }
                    }

                }
            }
        }
    }
}
