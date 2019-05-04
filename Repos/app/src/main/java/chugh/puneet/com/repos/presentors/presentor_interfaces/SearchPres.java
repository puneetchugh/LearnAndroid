package chugh.puneet.com.repos.presentors.presentor_interfaces;

import chugh.puneet.com.repos.views.view_interfaces.SearchView;

public interface SearchPres {
    void actOnSearchClick(String orgName);
    void attachView(SearchView view);
    void detachView();
}
