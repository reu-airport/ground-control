package ru.reu.time.vo

import java.util.*

data class Movement(
    var vehicleId: UUID? = null,
    var vertexFrom: Int? = null,
    var vertexTo: Int? = null,
    var isPermitted: Boolean? = null
)
