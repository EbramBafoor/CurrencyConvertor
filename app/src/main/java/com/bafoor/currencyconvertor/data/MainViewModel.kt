package com.bafoor.currencyconvertor.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bafoor.currencyconvertor.models.Rates
import com.bafoor.currencyconvertor.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

class MainViewModel @Inject constructor(private val mainRepository: CurrencyRepository)
    : ViewModel() {

        sealed class CurrencyEvent {
            class Success(val resultText : String) : CurrencyEvent()
            class Failure(val errorText : String) : CurrencyEvent()
            object Loading : CurrencyEvent()
            object Empty : CurrencyEvent()
        }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion : StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amountStr : String,
        fromCurrency : String,
        toCurrency : String
    ){
        val fromAmount = amountStr.toFloatOrNull()
        if (fromAmount == null){
            _conversion.value = CurrencyEvent.Failure("Not a Valid amount")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _conversion.value = CurrencyEvent.Loading
            when(val rateResponse = mainRepository.getRates(fromCurrency)){
                is Resource.Error -> _conversion.value = CurrencyEvent.Failure(rateResponse.message!!)
                is Resource.Success -> {
                    val rates = rateResponse.data!!.rates
                    val rate = getRateForCurrency(toCurrency,rates)
                    if (rate == null){
                        _conversion.value = CurrencyEvent.Failure("Unexpected Error")
                    } else {
                        val convertedCurrency = round(fromAmount * rate * 100) / 100
                        _conversion.value = CurrencyEvent.Success(
                            "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
                        )
                    }
                }
            }
        }
    }
    private fun getRateForCurrency(currency : String, rates: Rates) = when(currency){
        "AED" -> rates.aED
        "AFN" -> rates.aFN
        "ALL" -> rates.aLL
        "AMD" -> rates.aMD
        "ANG" -> rates.aNG
        "AOA" -> rates.aOA
        "ARS" -> rates.aRS
        "AUD"-> rates.aRS
        "AWG"-> rates.aRS
        "AZN"-> rates.aRS
        "BAM"-> rates.aRS
        "BBD" -> rates.aRS
        "BDT"-> rates.aRS
        "BGN"-> rates.aRS
        "BHD" -> rates.aRS
        "BIF"-> rates.aRS
        "BMD"-> rates.aRS
        "BND"-> rates.aRS
        "BOB"-> rates.aRS
        "BRL"-> rates.aRS
        "BSD"-> rates.aRS
        "BTC"-> rates.aRS
        "BTN"-> rates.aRS
        "BWP"-> rates.aRS
        "BYN"-> rates.aRS
        "BYR"-> rates.aRS
        "BZD"-> rates.aRS
        "CAD"-> rates.aRS
        "CDF"-> rates.aRS
        "CHF"-> rates.aRS
        "CLF"-> rates.aRS
        "CLP"-> rates.aRS
        "CNY"-> rates.aRS
        "COP"-> rates.aRS
        "CRC"-> rates.aRS
        "CUC"-> rates.aRS
        "CUP"-> rates.aRS
        "CVE"-> rates.aRS
        "CZK"-> rates.aRS
        "DJF"-> rates.aRS
        "DKK"-> rates.aRS
        "DOP"-> rates.aRS
        "DZD"-> rates.aRS
        "EGP"-> rates.aRS
        "ERN"-> rates.aRS
        "ETB"-> rates.aRS
        "EUR"-> rates.aRS
        "FJD"-> rates.aRS
        "FKP"-> rates.aRS
        "GBP"-> rates.aRS
        "GEL"-> rates.aRS
        "GGP"-> rates.aRS
        "GHS"-> rates.aRS
        "GIP"-> rates.aRS
        "GMD"-> rates.aRS
        "GNF"-> rates.aRS
        "GTQ"-> rates.aRS
        "GYD"-> rates.aRS
        "HKD"-> rates.aRS
        "HNL"-> rates.aRS
        "HRK"-> rates.aRS
        "HTG"-> rates.aRS
        "HUF"-> rates.aRS
        "IDR"-> rates.aRS
        "ILS"-> rates.aRS
        "IMP"-> rates.aRS
        "INR"-> rates.aRS
        "IQD"-> rates.aRS
        "IRR"-> rates.aRS
        "ISK"-> rates.aRS
        "JEP"-> rates.aRS
        "JMD"-> rates.aRS
        "JOD"-> rates.aRS
        "JPY"-> rates.aRS
        "KES"-> rates.aRS
        "KGS"-> rates.aRS
        "KHR"-> rates.aRS
        "KMF"-> rates.aRS
        "KPW"-> rates.aRS
        "KRW"-> rates.aRS
        "KWD"-> rates.aRS
        "KYD"-> rates.aRS
        "KZT"-> rates.aRS
        "LAK"-> rates.aRS
        "LBP"-> rates.aRS
        "LKR"-> rates.aRS
        "LRD"-> rates.aRS
        "LSL"-> rates.aRS
        "LTL"-> rates.aRS
        "LVL"-> rates.aRS
        "LYD"-> rates.aRS
        "MAD"-> rates.aRS
        "MDL"-> rates.aRS
        "MGA"-> rates.aRS
        "MKD"-> rates.aRS
        "MMK"-> rates.aRS
        "MNT"-> rates.aRS
        "MOP"-> rates.aRS
        "MRO"-> rates.aRS
        "MUR"-> rates.aRS
        "MVR"-> rates.aRS
        "MWK"-> rates.aRS
        "MXN"-> rates.aRS
        "MYR"-> rates.aRS
        "MZN"-> rates.aRS
        "NAD"-> rates.aRS
        "NGN"-> rates.aRS
        "NIO"-> rates.aRS
        "NOK"-> rates.aRS
        "NPR"-> rates.aRS
        "NZD"-> rates.aRS
        "OMR"-> rates.aRS
        "PAB"-> rates.aRS
        "PEN"-> rates.aRS
        "PGK"-> rates.aRS
        "PHP"-> rates.aRS
        "PKR"-> rates.aRS
        "PLN"-> rates.aRS
        "PYG"-> rates.aRS
        "QAR"-> rates.aRS
        "RON"-> rates.aRS
        "RSD"-> rates.aRS
        "RUB"-> rates.aRS
        "RWF"-> rates.aRS
        "SAR"-> rates.aRS
        "SBD"-> rates.aRS
        "SCR"-> rates.aRS
        "SDG"-> rates.aRS
        "SEK"-> rates.aRS
        "SGD"-> rates.aRS
        "SHP"-> rates.aRS
        "SLL"-> rates.aRS
        "SOS"-> rates.aRS
        "SRD"-> rates.aRS
        "STD"-> rates.aRS
        "SVC"-> rates.aRS
        "SYP"-> rates.aRS
        "SZL"-> rates.aRS
        "THB"-> rates.aRS
        "TJS"-> rates.aRS
        "TMT"-> rates.aRS
        "TND"-> rates.aRS
        "TOP"-> rates.aRS
        "TRY"-> rates.aRS
        "TTD"-> rates.aRS
        "TWD"-> rates.aRS
        "TZS"-> rates.aRS
        "UAH"-> rates.aRS
        "UGX"-> rates.aRS
        "USD"-> rates.aRS
        "UYU"-> rates.aRS
        "UZS"-> rates.aRS
        "VEF"-> rates.aRS
        "VND"-> rates.aRS
        "VUV"-> rates.aRS
        "WST"-> rates.aRS
        "XAF"-> rates.aRS
        "XAG"-> rates.aRS
        "XAU"-> rates.aRS
        "XCD"-> rates.aRS
        "XDR"-> rates.aRS
        "XOF"-> rates.aRS
        "XPF"-> rates.aRS
        "YER"-> rates.aRS
        "ZAR"-> rates.aRS
        "ZMK"-> rates.aRS
        "ZMW"-> rates.aRS
        "ZWL"-> rates.aRS
        else -> null
    }

}


