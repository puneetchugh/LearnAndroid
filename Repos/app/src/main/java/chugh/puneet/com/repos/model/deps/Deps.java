package chugh.puneet.com.repos.model.deps;


import javax.inject.Singleton;

import chugh.puneet.com.repos.model.network.NetworkModule;
import chugh.puneet.com.repos.views.ReposListFrag;
import dagger.Component;

@Singleton
@Component(modules = NetworkModule.class)
public interface Deps {
    void inject(ReposListFrag reposListFrag);
}
