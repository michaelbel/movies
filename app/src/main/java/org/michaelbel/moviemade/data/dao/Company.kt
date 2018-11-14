package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Company(
    @SerializedName("description") val description: String,
    @SerializedName("headquarters") val headquarters: String,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("id") val id: Int,
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("name") val name: String,
    @SerializedName("origin_country") val originCountry: String,
    @SerializedName("parent_company") val parentCompany: Company
) : Serializable