package chugh.puneet.com.repos.presentors.presentor_interfaces;

import chugh.puneet.com.repos.views.view_interfaces.ResultsView;

public interface ResultsPres {
    void onResultsActivityCalled(String orgName);
    void attachView(ResultsView view);
    void detachView();
}
