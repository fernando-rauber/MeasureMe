package uk.fernando.convert.ext

import androidx.navigation.NavController

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return tag.take(23)
    }

fun NavController.safeNav(direction: String) {
    kotlin.runCatching { this.navigate(direction) }
}

fun Double.isInteger() = run {
    if (this.toInt().toDouble() == this)
        "${this.toInt()}"
    else
        "$this"
}