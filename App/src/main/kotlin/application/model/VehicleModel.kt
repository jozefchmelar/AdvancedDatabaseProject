package application.model

import application.controller.*
import javafx.beans.property.*
import javafx.collections.*
import javafx.scene.image.*
import model.*
import tornadofx.*
import java.io.*

class VehicleModel : ItemViewModel<Vozidlo> {

    constructor(item: Vozidlo) : super(item)
    constructor() : super()

    val photoPath = SimpleStringProperty()

    val id = bind(Vozidlo::getId)
    val cennik = bind(Vozidlo::getCennik)
    val spz = bind(Vozidlo::getSpz)
    val znacka = bind(Vozidlo::getZnacka)
    val typ = bind(Vozidlo::getTyp)
    val naklady = bind(Vozidlo::getNaklady)
    val vynosy = bind(Vozidlo::getVynosy)
    val poruchovost = bind(Vozidlo::getPoruchovost)
    val vytazenost = bind(Vozidlo::getVytazenost)

    val fotkaCesta = bind {
        var toReturn: SimpleObjectProperty<Image> = Image(FileInputStream(File("no_image.jpg"))).toProperty()

        if (item != null) {
            val bytes = item.fotkaCesta
            if (bytes != null) {
                val prefix = if (item.fotkaCesta.length < 15 && item.fotkaCesta != "no_image") "Fotky/" else ""
                val x = Image(FileInputStream(File(prefix + item.fotkaCesta)))
                toReturn = x.toProperty()
            }
        }

        toReturn
    }
    val udrzby = bind {
        item?.udrzby?.sortedBy { -it.pocetKM }?.map(::MaintanceModel)?.observable()?.toProperty()
                ?: FXCollections.observableArrayList<MaintanceModel>().toProperty()
    }
    val datum_vyradenia = bind(Vozidlo::getDatum_vyradenia)


    override fun onCommit() {
        super.onCommit()
        item = Vozidlo(
                id.value,
                cennik.value,
                spz.value,
                znacka.value,
                typ.value,
                photoPath.value,
                datum_vyradenia.value
        )
        // photoPath.value = "NoPhoto"
    }
}