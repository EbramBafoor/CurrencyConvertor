package com.bafoor.currencyconvertor.models

import com.google.gson.annotations.Expose

data class CurrencyResponse(
    @Expose
    val base: String,
    @Expose
    val date: String,
    @Expose
    val rates: Rates,
    @Expose
    val success: Boolean,
    @Expose
    val timestamp: Int
)