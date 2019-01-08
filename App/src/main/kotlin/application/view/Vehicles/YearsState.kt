package application.view.Vehicles

import application.controller.*
import application.model.*
import application.view.Rental.*
import javafx.scene.*
import tornadofx.*
import java.time.*

class YearsState : View("Year"){
    private val controller: VehiclesController by inject()

    val year = LocalDate.of(2015,1,1).toProperty()

    override val root = borderpane{
        left{
            hbox {
                vbox {
                    form {
                        fieldset {
                            field("Rok") {
                                datepicker(year)
                            }
                        }
                        button("Show") {
                            action {
                                controller.getMostUsedDays(year.value.toDate())
                            }
                        }
                    }
                }
            }
        }

        center{
            tableview(controller.usages){
                column("Datum",UsageModel::date){
                    converter(Conv{ "${it.dayOfMonth}.${it.monthValue}.${it.year}"})
                }
                column("Pocet aut",UsageModel::usage)
            }
        }
    }

}
