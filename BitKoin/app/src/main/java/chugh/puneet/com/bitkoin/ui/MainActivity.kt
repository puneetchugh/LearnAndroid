package chugh.puneet.com.bitkoin.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.VisibleForTesting
import android.support.design.widget.Snackbar
import android.support.test.espresso.IdlingResource
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import chugh.puneet.com.bitkoin.FetchingIdlingResource
import chugh.puneet.com.bitkoin.constants.LOG_TAG
import chugh.puneet.com.bitkoin.R
import chugh.puneet.com.bitkoin.adapter.BitkoinListAdapter
import chugh.puneet.com.bitkoin.model.data.data.model
import chugh.puneet.com.bitkoin.model.data.network.AppModule
import chugh.puneet.com.bitkoin.viewmodel.MainActivityViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var fetchingIdlingResource : FetchingIdlingResource
    lateinit var fetcherListener : FetchingIdlingResource
    val bitkoinObserver = Observer<List<model.Datum>>{

        if(this::fetcherListener.isInitialized)
            fetcherListener.doneFetching()
        Log.d(LOG_TAG, "Inside bitkoinObserver...list received : "+it)
        id_status_message.visibility = View.GONE
        id_bitkoin_recyclerview_list.visibility = View.VISIBLE

        /*id_bitkoin_recyclerview_list.layoutManager = GridLayoutManager(this, 1) as RecyclerView.LayoutManager?
        id_bitkoin_recyclerview_list.adapter = BitkoinListAdapter(
                                                this@MainActivity,
                                                         it?.toMutableList())*/

        (id_bitkoin_recyclerview_list.adapter as BitkoinListAdapter).updateDataSet(it as MutableList<model.Datum>?)
        id_progress_bar.visibility = View.GONE
    }

    val errorObserver = Observer<String> {

        id_progress_bar.visibility = View.GONE
        it.takeIf { (it != null &&
                        !it.isBlank()) }
                .let {
                    lateinit var notificationMsg : String
                    if(!AppModule.hasNetwork(application)!!) {
                        notificationMsg = resources.getString(R.string.no_network)
                    }
                    else{
                        notificationMsg = resources.getString(R.string.no_data)
                    }
                    id_status_message.visibility = View.VISIBLE
                    id_bitkoin_recyclerview_list.visibility = View.GONE
                    showSnackbarMessage(notificationMsg)
                }
        if(this::fetcherListener.isInitialized)
            fetcherListener.doneFetching()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
        id_progress_bar.visibility = View.VISIBLE
        mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        id_bitkoin_recyclerview_list.adapter = BitkoinListAdapter(
                this@MainActivity,
                mainActivityViewModel.mutableLiveData.value?.toMutableList())

        id_bitkoin_recyclerview_list.layoutManager = GridLayoutManager(this, 1) as RecyclerView.LayoutManager?
        id_bitkoin_recyclerview_list.adapter = BitkoinListAdapter(
                this@MainActivity,
                null)
        mainActivityViewModel.getBitCoinData().observe(this, bitkoinObserver)
        mainActivityViewModel.errorMsg.observe(this, errorObserver)
    }

    fun showSnackbarMessage(message : String){
        val snack = Snackbar.make( id_main_activity, message,
                Snackbar.LENGTH_LONG)
        snack.show()
        if(this::fetcherListener.isInitialized)
            fetcherListener.doneFetching()
    }

    @VisibleForTesting
    @NonNull
    fun getIdlingResource(): IdlingResource {
        return fetchingIdlingResource
    }

    @NonNull
    fun setIdlingResource(idlingResource: FetchingIdlingResource){
        fetchingIdlingResource = idlingResource
        fetcherListener = fetchingIdlingResource
        Log.e(LOG_TAG, "setIdlingResource() called...fetcherListener : "+fetcherListener)
    }
}
