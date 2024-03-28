package org.michaelbel.movies.platform

sealed class Flavor(
    val name: String
) {
    data object Gms: Flavor("GMS")

    data object Hms: Flavor("HMS")

    data object Foss: Flavor("FOSS")
}