package com.example.myapplication



data class ShelfDetails(
    var shelf_id: String? = null,
    var number_of_racks: String,
    var rack_details: List<RackDetails>
)