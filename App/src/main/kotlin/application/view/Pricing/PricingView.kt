package application.view.Pricing

import application.controller.*
import application.model.*
import javafx.scene.layout.*
import tableviewpag
import tornadofx.*

class PricingView : View("Pricing") {

    val controller: PricingController by inject()
    private val createdPricing = PricingModel()

    override val root = borderpane {
        paddingAll = 24.0

        center {
            hbox {
                paddingRight = 24
                vbox {
                    addClass("card")
                    text("Pricing").addClass("card-title")
                    hgrow = Priority.ALWAYS
                    tableviewpag(controller.pricing){
                        smartResize()
                        column("#", PricingModel::id).apply { isSortable = false }
                        column("cena_den", PricingModel::cena_den).apply { isSortable = false }
                        column("poplatok", PricingModel::poplatok).apply { isSortable = false }
                        column("platny_od", PricingModel::platny_od).apply { isSortable = false }
                        column("platny_do", PricingModel::platny_do).apply { isSortable = false }
                    }
                }

            }
            right {
                paddingLeft = 24
                minWidth = 300.0
                vbox {
                    addClass("card")
                    text("Add ${"Pricing".toLowerCase()}").addClass("card-title")
                    form {
                        fieldset {
                            field("Day pre price") {
                                textfield(createdPricing.cena_den)
                            }
                            field("Fee") {
                                textfield(createdPricing.poplatok)
                            }
                            field("From") {
                                datepicker(createdPricing.platny_od)
                            }
                            field("To") {
                                datepicker(createdPricing.platny_do)
                            }
                        }
                    }
                    hbox {
                        button("Save") {
                            action {
                                createdPricing.commit()
                                controller.save(createdPricing.item)
                            }
                        }
                    }

                }
            }
        }
    }


}