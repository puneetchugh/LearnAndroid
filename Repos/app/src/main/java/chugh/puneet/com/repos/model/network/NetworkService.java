package chugh.puneet.com.repos.model.network;

import chugh.puneet.com.repos.model.data.AllRepos;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

public interface NetworkService {

    @Headers("Authorization: token <auth_token>") //, " +
            //"Accept: application/vnd.github.v3.full+json")
    @GET("/search/repositories?")
    Observable<AllRepos> getRepos(@Query("q") String orgName,
                                  @Query("sort") String sort,
                                  @Query("order") String order);

}
