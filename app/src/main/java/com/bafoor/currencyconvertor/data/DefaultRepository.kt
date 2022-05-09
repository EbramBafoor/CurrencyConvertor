package com.bafoor.currencyconvertor.data

import com.bafoor.currencyconvertor.models.CurrencyResponse
import com.bafoor.currencyconvertor.utils.Resource
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val api : CurrencyApi
) : CurrencyRepository{
    override suspend fun getRates(base: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An Error occurred")
        }
    }
}