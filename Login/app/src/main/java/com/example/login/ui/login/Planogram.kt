package com.example.login.ui.login
import com.google.gson.annotations.SerializedName
data class Planogram(
    @SerializedName("store_name") val store_name: String,
    @SerializedName("store_id") var store_id: Int,
    @SerializedName("store_address") var store_address: String,
    @SerializedName("store_contact") var store_contact: Int,
    @SerializedName("shelf_details") var shelf_details: List<ShelfDetails>,
    @SerializedName("product_details") var product_details: List<ProductDetails>
)
