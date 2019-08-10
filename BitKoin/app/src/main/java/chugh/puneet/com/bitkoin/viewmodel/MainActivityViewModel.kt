package chugh.puneet.com.bitkoin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import chugh.puneet.com.bitkoin.constants.BITKOIN_CURRENCY
import chugh.puneet.com.bitkoin.constants.BITKOIN_END
import chugh.puneet.com.bitkoin.constants.BITKOIN_START
import chugh.puneet.com.bitkoin.constants.LOG_TAG
import chugh.puneet.com.bitkoin.model.data.data.DataList
import chugh.puneet.com.bitkoin.model.data.data.Datum
import chugh.puneet.com.bitkoin.model.data.network.NetworkService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val networkService: NetworkService) : ViewModel(){

    val mutableLiveData : MutableLiveData<List<Datum>>
    var errorMsg = MutableLiveData<String>()
    var bitkoinApiService : Observable<DataList>
    init {
        mutableLiveData = MutableLiveData()
        bitkoinApiService = networkService.getDataList(BITKOIN_START, BITKOIN_END, BITKOIN_CURRENCY)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
        Log.e(LOG_TAG, "ViewModel init{} called...")
    }

    fun getBitCoinData() : LiveData<List<Datum>>{
        Log.e(LOG_TAG, "ViewModel : getBitCoinData() called....")
        if(mutableLiveData.value == null) {
            bitkoinApiService = networkService.getDataList(BITKOIN_START, BITKOIN_END, BITKOIN_CURRENCY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(mainThread())
            bitkoinApiService.subscribe(this::setResult, this::setError)
        }
        return mutableLiveData
    }

    fun setResult(dataList: DataList){
        Log.e(LOG_TAG, "setResult called with DataList : "+dataList.data)
        mutableLiveData.value = dataList.data
        errorMsg = MutableLiveData()
    }

    fun setError(throwable: Throwable){
        Log.e(LOG_TAG, "Inside setError()..error is : "+throwable.message)
        errorMsg.value = throwable.message
    }

    fun getBitCoinData(start : Int, limit : Int, currency: String) :
            Observable<DataList> = networkService.getDataList(start, limit, currency)
            .subscribeOn(Schedulers.io())
            .observeOn(mainThread())

    override fun onCleared() {
        super.onCleared()
        bitkoinApiService.unsubscribeOn(Schedulers.io())

    }

}