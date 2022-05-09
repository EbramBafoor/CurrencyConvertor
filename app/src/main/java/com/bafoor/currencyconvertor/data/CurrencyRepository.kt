package com.bafoor.currencyconvertor.data

import com.bafoor.currencyconvertor.models.CurrencyResponse
import com.bafoor.currencyconvertor.utils.Resource


interface CurrencyRepository {
    // Repo is Interface Not Class for easy Testing
    // <-----getRates Fun return Success or Error Class----->
    suspend fun getRates(base : String) : Resource<CurrencyResponse>
}