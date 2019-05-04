package chugh.puneet.com.repos.presentors.presentor_interfaces;

import chugh.puneet.com.repos.views.view_interfaces.RepositoryView;

public interface RepositoryPres {
    void loadRepo(String gitHubURL);
    void attachView(RepositoryView view);
}
