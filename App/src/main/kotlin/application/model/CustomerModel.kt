package application.model

import model.*
import tornadofx.*

class CustomerModel : ItemViewModel<Zakaznik> {

    constructor(item: Zakaznik) : super(item)
    constructor() : super()

    val id = bind(Zakaznik::getId)
    val kontakt = bind(Zakaznik::getKontakt)

    override fun onCommit() {
        super.onCommit()
        item = Zakaznik(
                id.value,
                kontakt.value
        )
    }
}