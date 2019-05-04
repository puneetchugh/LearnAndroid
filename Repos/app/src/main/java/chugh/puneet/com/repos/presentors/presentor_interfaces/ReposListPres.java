package chugh.puneet.com.repos.presentors.presentor_interfaces;

import chugh.puneet.com.repos.views.view_interfaces.ReposListView;

public interface ReposListPres {
    void fetchList(String orgName);
    void attachView(ReposListView view);
    void detachView();
}
