package chugh.puneet.com.bitkoin.viewmodel

import chugh.puneet.com.bitkoin.model.data.data.DataList
import chugh.puneet.com.bitkoin.model.data.network.NetworkService
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val networkService: NetworkService){

    fun getBitCoinData(start : Int, limit : Int, currency: String) :
            Observable<DataList> = networkService.getDataList(start, limit, currency)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}