package org.michaelbel.moviemade.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Company(
    @SerialName("description") val description: String,
    @SerialName("headquarters") val headquarters: String,
    @SerialName("homepage") val homepage: String,
    @SerialName("id") val id: Int,
    @SerialName("logo_path") val logoPath: String,
    @SerialName("name") val name: String,
    @SerialName("origin_country") val originCountry: String,
    @SerialName("parent_company") val parentCompany: Company? = null
)