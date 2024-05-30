package com.bsuirnethub.extension

fun <T> List<T>.combinations(): List<List<T>> {
    if (this.isEmpty())
        return listOf(emptyList())
    val head = this.first()
    val tail = this.drop(1)
    return tail.combinations().let { tailComb ->
        tailComb + tailComb.map { it + head }
    }
}