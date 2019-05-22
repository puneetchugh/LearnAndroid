package chugh.puneet.com.bitkoin.model.data.network

import chugh.puneet.com.bitkoin.model.data.data.DataList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkService{

    @Headers("X-CMC_PRO_API_KEY: <API_KEY>", "Accept: application/json")
    @GET("/v1/cryptocurrency/listings/latest")
    fun getDataList(
            @Query("start") start : Int,
            @Query("limit") max : Int,
            @Query("convert") convert : String
    ) : Observable<DataList>
}