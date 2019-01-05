package application.view

import application.controller.*
import application.model.*
import javafx.beans.property.*
import javafx.scene.*
import javafx.stage.*
import tableviewpag
import tornadofx.*

class AllVehicels : View("Vehicles") {
    override val root = tabpane {
        tab(VehiclesView::class)
        tab(NewVehicleView::class)
    }
}

class NewVehicleView : View("New vehicle") {
    private val controller: VehiclesController by inject()
    val pricing = PricingModel().toProperty()
    val newVehicle = VehicleModel()
    val filePath = SimpleStringProperty()
    override val root = borderpane {
        center = vbox {
            addClass("card")
            label("New car").addClass("card-title")
            form {

                fieldset {
                    field("SPZ") {
                        textfield(newVehicle.spz)
                    }
                    field("znacka") {
                        textfield(newVehicle.znacka)
                    }
                    field("type") {
                        textfield(newVehicle.typ)
                    }
                    field("Fotka") {
                        textfield(filePath) {
                            isEditable = false
                        }
                        button("select pic") {
                            action {
                                filePath.set(chooseFile("select picture", arrayOf(FileChooser.ExtensionFilter("pic", "*.jpeg", "*.jpg", "*.pdf",
                                        "*.tiff", "*.tif", "*.JPG", "*.JPEG"))).first().absolutePath)
                            }
                        }
                    }
                }
                button("SAVE"){
                    action{
                        newVehicle.commit()
                        controller.newVehicle(newVehicle.item,filePath.value)
                    }
                }
            }
        }
        right = tableviewpag(controller.pricing) {
            onSelectionChange {
                it?.item.let { newVehicle.cennik.setValue(it) }
            }
        }

    }

}