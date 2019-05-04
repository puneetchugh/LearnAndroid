package chugh.puneet.com.repos;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import chugh.puneet.com.repos.model.deps.DaggerDeps;
import chugh.puneet.com.repos.model.deps.Deps;
import chugh.puneet.com.repos.model.network.NetworkModule;

public class BaseFragment extends Fragment {

    Deps deps;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deps = DaggerDeps.builder().networkModule(new NetworkModule()).build();
    }

    public Deps getDeps(){
        return deps;
    }
}
