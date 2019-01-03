package application.controller

import tornadofx.*

abstract class MyController : Controller() {
    abstract fun get()
}