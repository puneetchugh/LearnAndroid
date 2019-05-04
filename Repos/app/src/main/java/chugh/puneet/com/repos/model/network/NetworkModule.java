package chugh.puneet.com.repos.model.network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    //File cacheFile = new File(applicationContext, "cache");

    public NetworkModule(){};
    @Provides
    @Singleton
    Retrofit provideCall(){
        /*
        Cache cache = null;

        try{
            cache = new Cache(cacheFile, 10*1024*1024);
        }catch (Exception ioe){
        }*/

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkService providesNetworkService(Retrofit retrofit){
        return retrofit.create(NetworkService.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkClient providesClient(NetworkService networkService){
        return new NetworkClient(networkService);
    }
}
