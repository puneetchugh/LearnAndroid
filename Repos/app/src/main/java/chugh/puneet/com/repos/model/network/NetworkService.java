package chugh.puneet.com.repos.model.network;

import chugh.puneet.com.repos.model.data.AllRepos;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

public interface NetworkService {

    @Headers("Authorization: token 7ad9cf7bc9f40574a56aa7d3d7e111cb3fcb3710") //, " +
            //"Accept: application/vnd.github.v3.full+json")
    @GET("/search/repositories?")
    Observable<AllRepos> getRepos(@Query("q") String orgName,
                                  @Query("sort") String sort,
                                  @Query("order") String order);

}
