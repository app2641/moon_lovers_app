package com.app2641.moonlovers.network

import com.squareup.moshi.Json

class MoonAgeProperty(
    @field:Json(name="age")
    val age: Double
)