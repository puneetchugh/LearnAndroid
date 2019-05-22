package chugh.puneet.com.bitkoin.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import chugh.puneet.com.bitkoin.R
import chugh.puneet.com.bitkoin.model.data.data.DataList
import chugh.puneet.com.bitkoin.viewmodel.MainActivityViewModel
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val compositeDisposable by lazy { CompositeDisposable() }
    @Inject
    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)

        compositeDisposable.add(mainActivityViewModel.getBitCoinData(1,100, "USD")
                .subscribe(this::displayData, this::onError))
    }

    private fun displayData(dataList : DataList){
        Log.e("PuneetChugh", "Data received ... "+dataList.data!!.size)
    }

    private fun onError(throwable : Throwable){
        Log.e("PuneetChugh", "error received in onError... "+throwable.message)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}
