package application.view

import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import javafx.stage.*
import tornadofx.* // ktlint-disable

class MainView : View("PDS") {

    private val controller: Controller by inject()

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

    }.also { primaryStage.setOnCloseRequest { System.exit(0) } }


}
