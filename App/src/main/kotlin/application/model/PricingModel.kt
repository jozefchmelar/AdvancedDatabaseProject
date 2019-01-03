package application.model

import model.*
import tornadofx.*

class PricingModel : ItemViewModel<Cennik> {

    constructor(item:Cennik) : super(item)
    constructor() : super()

    val id        = bind(Cennik::getId)
    val cena_den  = bind(Cennik::getCena_den)
    val poplatok  = bind(Cennik::getPoplatok)
    val platny_od = bind(Cennik::getPlatny_od)
    val platny_do = bind(Cennik::getPlatny_do)

    override fun onCommit() {
        super.onCommit()
        item = Cennik(
               id.value,
               cena_den.value,
               poplatok.value,
               platny_od.value,
               platny_do.value
        )
    }
}