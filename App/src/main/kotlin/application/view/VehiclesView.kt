package application.view

import application.controller.*
import application.model.*
import javafx.geometry.*
import javafx.scene.layout.*
import tornadofx.*

class VehiclesView : View("Vehicles") {
    private val controller: VehiclesController by inject()

    override val root = borderpane {
        //   controller.get()
        padding = Insets(20.0)

        center = borderpane {
            top {
                borderpane {
                    paddingTop = 25

                    center { label("Who's renting a car?").addClass("card-title") }
                }
            }
            center {

                vbox {
                    vgrow = Priority.ALWAYS
                    hbox {
                        addClass("card")
                        text("Company").addClass("card-title")
                        setOnMouseClicked { println("company click") }
                    }



                    hbox {
                        paddingTop = 50
                        addClass("card")
                        text("Person").addClass("card-title")
                        setOnMouseClicked { println("person clik") }

                    }

                }

            }


        }

    }
}