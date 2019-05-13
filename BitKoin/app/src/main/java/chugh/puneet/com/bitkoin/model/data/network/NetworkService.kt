package chugh.puneet.com.bitkoin.model.data.network

import android.arch.lifecycle.Observer
import chugh.puneet.com.bitkoin.model.data.data.DataList
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.*

interface NetworkService{

    @Headers("<API_KEY>", "Accept: application/json")
    @GET("/v1/cryptocurrency/listings/latest")
    fun getDataList(
            @Query("start") start : Int,
            @Query("limit") max : Int,
            @Query("convert") convert : String
    ) : Observer<DataList>
}