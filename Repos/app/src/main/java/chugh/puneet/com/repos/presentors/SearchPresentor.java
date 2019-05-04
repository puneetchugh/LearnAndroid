package chugh.puneet.com.repos.presentors;

import android.util.Log;

import chugh.puneet.com.repos.presentors.presentor_interfaces.SearchPres;
import chugh.puneet.com.repos.views.view_interfaces.SearchView;

import static chugh.puneet.com.repos.Contracts.REPO_TAG;

public class SearchPresentor implements SearchPres {

    public SearchView view;
    public SearchPresentor(){
    }

    @Override
    public void attachView(SearchView searchView) {
        view = searchView;
    }

    @Override
    public void detachView(){
        view = null;
    }

    @Override
    public void actOnSearchClick(String orgName) {
        //Log.e(REPO_TAG, "actionOnSearchClick() called inside SearchPresentor : "+orgName);
        if(orgName == null || orgName.isEmpty()){
            view.notifyEmptyString();
        }
        else{
            view.moveToNextPage(orgName);
        }
    }
}
