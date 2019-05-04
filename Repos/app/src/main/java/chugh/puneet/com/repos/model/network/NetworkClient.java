package chugh.puneet.com.repos.model.network;

import android.util.Log;

import com.android.volley.NetworkError;

import chugh.puneet.com.repos.model.data.AllRepos;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static chugh.puneet.com.repos.Contracts.REPO_TAG;

public class NetworkClient {

    private final NetworkService networkService;
    public interface RepositoryCallback{
        void onSuccess(AllRepos repos);
        void onError(NetworkError error);
    }

    public NetworkClient(NetworkService networkService){
        this.networkService = networkService;
    }

    public Subscription getRepoData(final RepositoryCallback callback, String orgName){
        //Log.e(REPO_TAG, "entered getRepoData() of NetworkClient");
        return networkService.getRepos(String.format("org:%s",orgName), "stars", "desc")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext((Throwable throwable)->{
                    return Observable.error(throwable);
                })
                .subscribe(new Subscriber<AllRepos>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(REPO_TAG, "onError() called "+e.getMessage());
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(AllRepos repos) {
                        //Log.e(REPO_TAG, "onNext() called");
                        callback.onSuccess(repos);
                    }
                });
    }
}
