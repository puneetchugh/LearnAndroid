package chugh.puneet.com.bitkoin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import chugh.puneet.com.bitkoin.constants.BITKOIN_CURRENCY
import chugh.puneet.com.bitkoin.constants.BITKOIN_END
import chugh.puneet.com.bitkoin.constants.BITKOIN_START
import chugh.puneet.com.bitkoin.constants.LOG_TAG
import chugh.puneet.com.bitkoin.model.data.data.history.modelHistory
import chugh.puneet.com.bitkoin.model.data.data.history.transformedModel
import chugh.puneet.com.bitkoin.model.data.data.model
import chugh.puneet.com.bitkoin.model.data.network.NetworkService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val networkService: NetworkService) : ViewModel(){

    val mutableAllLiveData : MutableLiveData<List<transformedModel.NewData>>
    var errorMsg = MutableLiveData<String>()
    var bitkoinApiService : Observable<model.DataList>
    lateinit var bitkoinCombineService : Observable<modelHistory.DataList>

    var datumMap = mutableMapOf<Int, model.Datum>()
    init {
        mutableAllLiveData = MutableLiveData()
        bitkoinApiService = networkService.getDataList(BITKOIN_START, BITKOIN_END, BITKOIN_CURRENCY)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
        Log.d(LOG_TAG, "ViewModel init{} called...")
    }

    fun getBitCoinData() : LiveData<List<transformedModel.NewData>>{
        Log.d(LOG_TAG, "ViewModel : getBitCoinData() called....")
        if(mutableAllLiveData.value == null) {
            bitkoinCombineService =
                    networkService.getDataList(BITKOIN_START, BITKOIN_END, BITKOIN_CURRENCY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(mainThread())
                        .flatMap {
                            Log.d(LOG_TAG, "flatMap called")
                            setResult(it)
                            val stringBuilder = StringBuilder()

                            it.data.forEach { datum ->
                                datumMap[datum.id] = datum
                                //datumList.add(datum)
                                stringBuilder.append(datum.id)
                                stringBuilder.append(",")
                            }
                            stringBuilder.deleteCharAt(stringBuilder.length-1)

                            networkService.getCryptoMetaInfo()
                                    .subscribeOn(Schedulers.io())
                        }
            bitkoinCombineService.subscribe(this::setResultsIsActive, this::setErrorIsActive)
        }
        return mutableAllLiveData
    }

    private fun setResultsIsActive(dataList: modelHistory.DataList){

        val historyDataMap = dataList.data?.map { it.id to it }?.toMap()
        val newDataMap = mutableListOf<transformedModel.NewData>()
        for((key, value) in datumMap){
            if(historyDataMap?.containsKey(key)!!){
                newDataMap.add(transformedModel.NewData(value, historyDataMap[key]?.is_active == 1))
            }
        }
        mutableAllLiveData.postValue(newDataMap)
    }

    private fun setErrorIsActive(throwable: Throwable){

        val newDataMap = mutableListOf<transformedModel.NewData>()
        for((_, value) in datumMap){
            newDataMap.add(transformedModel.NewData(value, false))
        }
        newDataMap.takeIf { it.size == 0 }
                .let {
                    Log.e(LOG_TAG, "Inside setError()..error is : "+throwable.message)
                    errorMsg.value = throwable.message
                    return
                }
        mutableAllLiveData.value = newDataMap
    }

    fun setResult(dataList: model.DataList){
        Log.e(LOG_TAG, "setResult called with DataList : "+dataList.data)
        //mutableLiveData.value = dataList.data

        lateinit var newDataList: MutableList<transformedModel.NewData>
        newDataList = mutableListOf()
        Log.e(LOG_TAG, "setResult() called...")
        for(datum in dataList.data){
            newDataList.add(transformedModel.NewData(datum))
        }
        mutableAllLiveData.value = newDataList
        errorMsg = MutableLiveData()
    }

    fun setError(throwable: Throwable){
        Log.e(LOG_TAG, "Inside setError()..error is : "+throwable.message)
        errorMsg.value = throwable.message
    }

    fun getBitCoinData(start : Int, limit : Int, currency: String) :
            Observable<model.DataList> = networkService.getDataList(start, limit, currency)
            .subscribeOn(Schedulers.io())
            .observeOn(mainThread())

    override fun onCleared() {
        super.onCleared()
        bitkoinApiService.unsubscribeOn(Schedulers.io())

    }

}