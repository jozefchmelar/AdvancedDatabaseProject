package application.view.Vehicles

import javafx.scene.*
import tornadofx.*
import java.time.*

class YearsState : View("Year"){
    val year = LocalDate.now().toProperty()
    override val root = borderpane{
        left{
            vbox{
                form {
                    fieldset {
                        field("Rok") {
                            datepicker(year)
                        }

                    }
                    button("Show"){
                        action{
                            //    select id as dayNumber, selectVytazenostDen(id,'2015') as vy from vozidlo where id < 366 order by vy desc;
                        }
                    }
                }
            }
            textfield {  }
        }
    }

}
