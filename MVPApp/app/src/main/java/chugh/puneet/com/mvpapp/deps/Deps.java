package chugh.puneet.com.mvpapp.deps;

import javax.inject.Singleton;

import chugh.puneet.com.mvpapp.home.HomeActivity;
import chugh.puneet.com.mvpapp.network.NetworkModule;
import dagger.Component;

/**
 * Created by pchugh on 7/8/18.
 */

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(HomeActivity homeActivity);
}
