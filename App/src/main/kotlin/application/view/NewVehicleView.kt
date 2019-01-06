package application.view

import application.controller.*
import application.model.*
import javafx.beans.property.*
import javafx.scene.*
import javafx.scene.control.*
import javafx.scene.image.*
import javafx.stage.*
import tableviewpag
import tornadofx.*
import java.io.*

class AllVehicels : View("Vehicles") {
    override val root = tabpane {
        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
        tab(VehiclesView::class)
        tab(NewVehicleView::class)
    }
}

class NewVehicleView : View("New vehicle") {
    private val controller: VehiclesController by inject()
    private val pricingController: PricingController by inject()

    val pricing = PricingModel().toProperty()
    val newVehicle = VehicleModel()
    val filePath = SimpleStringProperty()

    override val root = borderpane {
        paddingAll = 24.0

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
                                val pathToPicture = chooseFile("select picture", arrayOf(FileChooser.ExtensionFilter("pic", "*.jpeg", "*.jpg", "*.pdf",
                                        "*.tiff", "*.tif", "*.JPG", "*.JPEG"))).first().absolutePath
                                newVehicle.fotkaCesta.value = Image(FileInputStream(File((pathToPicture))))
                                newVehicle.photoPath.value = pathToPicture
                                filePath.set(pathToPicture)
                            }
                        }
                    }
                }
                button("SAVE") {
                    action {
                        newVehicle.commit()
                        controller.newVehicle(newVehicle.item)
                    }
                }
            }
        }
        right = tableviewpag(pricingController.pricing) {
            paddingAll = 24.0
            addClass("card")

            smartResize()
            column("#", PricingModel::id).apply { isSortable = false }
            column("cena_den", PricingModel::cena_den).apply { isSortable = false }
            column("poplatok", PricingModel::poplatok).apply { isSortable = false }
            column("platny_od", PricingModel::platny_od).apply { isSortable = false }
            column("platny_do", PricingModel::platny_do).apply { isSortable = false }
            onSelectionChange {
                it?.item.let { newVehicle.cennik.value = it }
            }
        }

    }

}