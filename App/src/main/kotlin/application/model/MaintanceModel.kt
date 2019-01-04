package application.model

import model.*
import tornadofx.*

class MaintanceModel : ItemViewModel<Udrzba> {

    constructor(item: Udrzba) : super(item)
    constructor() : super()

    val pocetKM = bind { item?.pocetKM?.toString()?.toProperty() ?: "0.0".toProperty()}
    val cena = bind { item?.cena?.toString()?.toProperty() ?: "0.0".toProperty()}
    //val cena = bind(Udrzba::getPocetKM)
    val datumOD = bind(Udrzba::getDatumOD)
    val datumDO = bind(Udrzba::getDatumDO)
    val popis   = bind(Udrzba::getPopis)

    override fun onCommit() {
        super.onCommit()
        item = Udrzba(
                pocetKM.value.toLong(),
                cena.value.toDouble(),
                datumOD.value,
                datumDO.value,
                popis.value
        )
    }
}