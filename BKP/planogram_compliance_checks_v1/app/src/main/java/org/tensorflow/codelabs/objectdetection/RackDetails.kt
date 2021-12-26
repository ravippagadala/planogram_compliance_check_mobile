package org.tensorflow.codelabs.objectdetection

data class RackDetails(
    val rack_id: Int,
    val x_min: Int,
    val y_min: Int,
    val x_max: Int,
    val y_max: Int
)