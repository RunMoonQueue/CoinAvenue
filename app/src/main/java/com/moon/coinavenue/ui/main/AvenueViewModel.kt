package com.moon.coinavenue.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moon.coinavenue.network.AvenueService
import com.moon.coinavenue.network.model.CandleData
import com.moon.coinavenue.network.model.MarketCode
import com.moon.coinavenue.network.repository.AvenueRepo
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AvenueViewModel : ViewModel() {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val avenueRepository: AvenueRepo = AvenueRepo(AvenueService.avenueApi)

    val marketMutableLiveData = MutableLiveData<MutableList<MarketCode>>()
    val oneMinuteMutableLiveData = MutableLiveData<MutableList<CandleData>>()

    fun getMargetCode() {
        scope.launch {
            val marketCodes = avenueRepository.getMarketCode()
            marketMutableLiveData.postValue(marketCodes)
        }
    }

    fun getOneMinuteData(market: String) {
        scope.launch {
            val oneMinuteData = avenueRepository.getOneMinuteData(market)
            oneMinuteMutableLiveData.postValue(oneMinuteData)
        }
    }

    fun cancelRequests() = coroutineContext.cancel()
}