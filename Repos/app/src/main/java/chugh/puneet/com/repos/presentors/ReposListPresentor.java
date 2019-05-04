package chugh.puneet.com.repos.presentors;

import android.util.Log;

import com.android.volley.NetworkError;

import java.util.List;

import chugh.puneet.com.repos.model.data.AllRepos;
import chugh.puneet.com.repos.model.data.Repo;
import chugh.puneet.com.repos.model.network.NetworkClient;
import chugh.puneet.com.repos.presentors.presentor_interfaces.ReposListPres;
import chugh.puneet.com.repos.views.view_interfaces.ReposListView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static chugh.puneet.com.repos.Contracts.REPO_TAG;

public class ReposListPresentor implements ReposListPres {

    public ReposListView view;
    public CompositeSubscription subscriptions;
    public NetworkClient client;
    public ReposListPresentor(NetworkClient client){
        this.client = client;
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void attachView(ReposListView reposListView){
        view = reposListView;
    }

    @Override
    public void detachView(){
        view = null;
    }

    @Override
    public void fetchList(String orgName) {

        if(orgName == null || orgName.isEmpty()){
            view.hideProgressBar();
            view.displayError(new NetworkError());
            return;
        }
        //Log.e(REPO_TAG, "fetchList() called from ReposListPresentor : "+orgName);
        Subscription subscription = client.getRepoData(new NetworkClient.RepositoryCallback() {
            @Override
            public void onSuccess(AllRepos repos) {
                List<Repo> repoList = getTopThreeRepos(repos.getItems());

                view.displayList(repoList);
                view.hideProgressBar();
            }

            @Override
            public void onError(NetworkError error) {
                view.hideProgressBar();
                view.displayError(error);
            }
        }, orgName);
        subscriptions.add(subscription);
    }

    public void fragmentRebuilt(List<Repo> list){
        //Log.e(REPO_TAG, "fragmentRebuilt() called from ReposListPresentor..");
        view.displayList(list);
        view.hideProgressBar();
    }

    public List<Repo> getTopThreeRepos(List<Repo> list){
        if(list.size() >= 3){
            return list.subList(0, 3);
        }
        return list.subList(0, list.size()-1);
    }

    public void unsubscribe(){
        subscriptions.unsubscribe();
    }
}
