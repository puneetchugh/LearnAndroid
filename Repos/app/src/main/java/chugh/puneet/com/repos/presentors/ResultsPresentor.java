package chugh.puneet.com.repos.presentors;

import android.util.Log;

import chugh.puneet.com.repos.presentors.presentor_interfaces.ResultsPres;
import chugh.puneet.com.repos.views.view_interfaces.ResultsView;

import static chugh.puneet.com.repos.Contracts.REPO_TAG;

public class ResultsPresentor implements ResultsPres {

    public ResultsView view;
    public ResultsPresentor(){}

    @Override
    public void attachView(ResultsView resultsView){
        view = resultsView;
    }

    @Override
    public void onResultsActivityCalled(String orgName) {
        //Log.e(REPO_TAG, "onResultsActivityCalled() from ResultsPresentor : " + orgName);
        if(orgName == null || orgName.isEmpty()){
            view.showError();
        }
        else {
            view.callListFragment(orgName);
        }
    }

    @Override
    public void detachView(){
        view = null;
    }
}
