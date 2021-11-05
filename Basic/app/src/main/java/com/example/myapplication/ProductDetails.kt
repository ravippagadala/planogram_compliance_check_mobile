package com.example.myapplication

data class ProductDetails  (
    var product_id: Int,
    var product_description: String? = null,
    var product_label: String? = null,
    var shelf_id: Int,
    var rack_id: Int,
    var presence_x_start: Double,
    var presence_x_end: Double
)