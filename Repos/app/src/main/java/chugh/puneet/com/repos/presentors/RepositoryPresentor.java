package chugh.puneet.com.repos.presentors;

import android.util.Log;

import chugh.puneet.com.repos.presentors.presentor_interfaces.RepositoryPres;
import chugh.puneet.com.repos.views.view_interfaces.RepositoryView;

import static chugh.puneet.com.repos.Contracts.REPO_TAG;

public class RepositoryPresentor implements RepositoryPres {

    public RepositoryView view;
    public RepositoryPresentor(){
    }

    @Override
    public void attachView(RepositoryView view){
        this.view = view;
    }

    @Override
    public void loadRepo(String gitHubURL) {
        //Log.e(REPO_TAG, "loadRepo() called from RepositoryPresentor : "+gitHubURL);
        if(gitHubURL == null || gitHubURL.isEmpty()){
            view.displayError();
        }
        else{
            view.displayRepository(gitHubURL.replace("git:", "https:"));
        }
    }
}
