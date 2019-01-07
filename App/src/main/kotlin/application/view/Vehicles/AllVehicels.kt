package application.view.Vehicles

import javafx.scene.control.*
import tornadofx.*

class AllVehicels : View("Vehicles") {
    override val root = tabpane {
        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
        tab(VehiclesView::class)
        tab(NewVehicleView::class)
        tab(VehicleState::class)
    }
}