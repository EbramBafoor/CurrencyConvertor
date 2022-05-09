package com.bafoor.currencyconvertor.utils

sealed class Resource<T>(val data : T?, val message : String?) {
    // to handle Errors
    class Success<T>(data: T?) : Resource<T>(data, null)
    class Error<T>(message: String?) : Resource<T>(null, message)

}