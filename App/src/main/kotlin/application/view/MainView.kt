package application.view

import application.controller.MyController
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import javafx.stage.*
import tornadofx.* // ktlint-disable

class MainView : View("PDS") {

    private val controller: MyController by inject()

    override val root = borderpane {
        minWidth = 1000.0
        vgrow = Priority.ALWAYS
        hgrow = Priority.ALWAYS
        top =
            toolbar {
                addClass("toolbar-colored")
                label("Car rental").addClass("card-title")
            }

        center = tabpane {
            insets(20.0)
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            tab(CustomersView::class)
            tab(InvoicesView::class)
            tab(PricingView::class)
            tab(RentalView::class)
            tab(VehiclesView::class)
        }
        bottom = vbox {
//            textfield(controller.durationProperty, converter = DoubleConv { "Duration : " + it.format() })
//            slider(min = 0.01, max = 2) {
//                isShowTickLabels = true
//                valueProperty().bindBidirectional(controller.durationProperty)
//                setOnMouseReleased { controller.setSimSpeed() }
//            }
        }
    }.also { primaryStage.setOnCloseRequest { System.exit(0) } }


}
