package chugh.puneet.com.mvpwithoutdagger.network;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static chugh.puneet.com.mvpwithoutdagger.home.HomeActivity.TAG;


/**
 * Created by pchugh on 7/8/18.
 */

@Module
public class NetworkModule {

    static File cacheFile;

    public NetworkModule(File cacheFile){
        this.cacheFile = cacheFile;
    }

    private static Retrofit provideCall(){
        Cache cache = null;
        try{
            cache = new Cache(cacheFile, 10*1024*1024);
        }catch (Exception ioe){

        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Customize the request
                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .removeHeader("Pragma")
                                //.header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME))
                                .build();

                        Response response = chain.proceed(request);
                        response.cacheResponse();
                        // Customize or return the response
                        return response;
                    }
                })
                .cache(cache)
                .build();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

                return new Retrofit.Builder()
                        .baseUrl("https://api.yelp.com")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();

    }

    public static NetworkService providesNetworkService(Retrofit retrofit){
        return retrofit.create(NetworkService.class);
    }

    public static Service providesService(){

        Log.e(TAG, "Inside NetworkModule : providesService()");
        return new Service(providesNetworkService(provideCall()));
    }
}
