package chugh.puneet.com.mvpwithoutdagger.network;

import chugh.puneet.com.mvpwithoutdagger.models.YelpData;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pchugh on 7/8/18.
 */

public interface NetworkService {

    @Headers("Authorization: Bearer 2ROaa2Rh9qu3WVTCms8FoVE4mSfHQHC7QJua95-kKT-PqzIlLSrs4tmHVdtdFw_66-JNfRiJmbCByHTvFNy5dQq-tpfS4FrPpupIzKlgELR3br-r5trpeFhrCRgwWnYx")
    @GET("/v3/businesses/search")
    Observable<YelpData> getYelpData(@Query("term") String term,
                                     @Query("location") String location);
}
