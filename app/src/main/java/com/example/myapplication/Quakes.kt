package com.example.myapplication

data class Quakes (
    val metadata: Metadata,
    val features: List<Feature>
)

data class Metadata (
    val title: String,
    val count: Int
)

data class Feature (
    val id: String,
    val properties: Properties,
    val geometry: Geometry
)

data class Properties (
    val mag: Double,
    val place: String,
    val title: String
)

data class Geometry (
    val coordinates: List<Double>
)