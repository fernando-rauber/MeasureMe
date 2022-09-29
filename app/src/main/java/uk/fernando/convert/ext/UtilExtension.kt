package uk.fernando.convert.ext

fun Double.isInteger() = run {
    if (this.toInt().toDouble() == this)
        "${this.toInt()}"
    else
        "$this"
}