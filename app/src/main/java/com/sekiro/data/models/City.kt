package com.sekiro.data.models

data class City(
    val name: String? = null,
    val country: String? = null,
    val state: String? = null
) {

    fun getFullName() = listOfNotNull(name, country, state)
        .joinToString(", ")
}