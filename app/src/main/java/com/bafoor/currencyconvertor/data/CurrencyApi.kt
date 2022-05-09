package com.bafoor.currencyconvertor.data

import com.bafoor.currencyconvertor.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/v1/latest")
    suspend fun getRates(
        @Query("base")
        base : String
    ) : Response<CurrencyResponse>
}