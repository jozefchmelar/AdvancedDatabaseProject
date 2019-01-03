package application.model

import model.*
import tornadofx.*

class CompanyModel : ItemViewModel<Firma> {

    constructor(item: Firma) : super(item)
    constructor() : super()

    val id = bind(Firma::getId)
    val kontakt = bind(Firma::getKontakt)
    val ico = bind(Firma::getIco)
    val nazov = bind(Firma::getNazov)

    override fun onCommit() {
        super.onCommit()
        item = Firma(
                id.value,
                kontakt.value,
                ico.value,
                nazov.value
        )
    }
}