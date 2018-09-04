package chugh.puneet.com.mvpapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

import chugh.puneet.com.mvpapp.deps.DaggerDeps;
import chugh.puneet.com.mvpapp.deps.Deps;
import chugh.puneet.com.mvpapp.network.NetworkModule;

/**
 * Created by pchugh on 7/8/18.
 */

public class BaseApp extends AppCompatActivity {

    Deps deps;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();
    }

    public Deps getDeps() {
        return deps;
    }
}
