package com.example.data.data

data class SearchSettingsState(
    var selectedCountry: Int? = null,
    var selectedGenre: Int? = null,
    var selectedOrder: String = "YEAR",
    var yearFrom: Int = 1950,
    var yearTo: Int = 2023,
    var ratingFrom: Int = 0,
    var ratingTo: Int = 10,
    var type: String = "ALL"
)
