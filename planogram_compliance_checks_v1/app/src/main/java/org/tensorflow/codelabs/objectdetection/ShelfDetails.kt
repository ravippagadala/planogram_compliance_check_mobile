package org.tensorflow.codelabs.objectdetection

import org.tensorflow.codelabs.objectdetection.RackDetails

data class ShelfDetails(
    var shelf_id: String? = null,
    var number_of_racks: String,
    var rack_details: List<RackDetails>
)