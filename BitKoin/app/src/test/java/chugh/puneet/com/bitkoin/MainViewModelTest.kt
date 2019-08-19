package chugh.puneet.com.bitkoin

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import chugh.puneet.com.bitkoin.model.data.network.NetworkService
import chugh.puneet.com.bitkoin.viewmodel.MainActivityViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class MainViewModelTest {

    @get:Rule
    var rule : TestRule = InstantTaskExecutorRule()

    var mainActivityViewModel : MainActivityViewModel

    var usd : USD
    var quote: Quote
    var datum: Datum
    val list : MutableList<Datum> = mutableListOf()
    val dataList = DataList()
    val networkServiceMock = mock<NetworkService> {
        on { getDataList(any(), any(), any()) }doReturn(Observable.just(dataList))
        //on {getDataList(any(), any(), "")}doReturn (observable)
    }
    val liveData : MutableLiveData<List<Datum>> = mock()

    init {
        usd = USD()
        usd.percentChange7d = 1.50017
        usd.percentChange24h = 2.52521
        usd.volume24h = 4534572.15703332
        usd.percentChange1h = 0.482488

        quote = Quote()
        quote.usd = usd

        datum = Datum()
        datum.name = "BitKoin"
        datum.symbol = "BTK"
        datum.dateAdded = "2018-12-31T00:00:00.000Z"
        datum.quote = quote

        list.add(datum)
        dataList.data = list
        liveData.value = list
        mainActivityViewModel = MainActivityViewModel(networkServiceMock)
    }

    @Test
    fun testNetworkService(){
        //mainActivityViewModel = MainActivityViewModel(networkServiceMock)
        mainActivityViewModel.getBitCoinData()
        assertEquals(mainActivityViewModel.mutableLiveData.value, liveData.value)
    }

    @Test
    fun testMainViewModelDataNotNull(){
        //mainActivityViewModel = MainActivityViewModel(networkServiceMock)
        mainActivityViewModel.setResult(dataList)
        assertNotNull(mainActivityViewModel.mutableLiveData.value)
    }

    @Test
    fun testMainViewModelSetResult(){
        //mainActivityViewModel = MainActivityViewModel(networkServiceMock)
        mainActivityViewModel.setResult(dataList)
        assertEquals(mainActivityViewModel.mutableLiveData.value, dataList.data)
    }

    @Test
    fun testMainViewModelException(){
        val throwable : Throwable = Throwable("Data doesn't exits")
        mainActivityViewModel.setError(throwable)
        assertEquals(mainActivityViewModel.errorMsg.value, throwable.message)
    }

}