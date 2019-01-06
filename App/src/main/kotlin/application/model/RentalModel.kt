package application.model

import javafx.beans.property.*
import model.*
import tornadofx.*

class RentalModel : ItemViewModel<Vypozicka> {

    constructor(item: Vypozicka) : super(item)
    constructor() : super()

    val id       = bind(Vypozicka::getId)
    val vozidlo  = bind(Vypozicka::getVozidlo )
    val isRentingCompany = SimpleBooleanProperty(true)
    val zakaznik = bind { item?.zakaznik?.toProperty() ?: Zakaznik("0","0").toProperty()        }
    val person   = SimpleObjectProperty<PersonModel>()
    val company  = SimpleObjectProperty<CompanyModel>()
    val datumOD  = bind { item?.datumOD?.toLocalDate().toProperty()}
    val datumDO  = bind { item?.datumDO?.toLocalDate().toProperty()}

    override fun onCommit() {
        super.onCommit()
        item = Vypozicka(
                id.value,
                vozidlo.value,
                Zakaznik(if(isRentingCompany.value) company.value.ico.value else person.value.rodCislo.value),//zakaznik.value,
                datumOD.value.toDate(),
                datumDO.value.toDate()
        )
    }
}