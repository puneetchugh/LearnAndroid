package chugh.puneet.com.repos.views.view_interfaces;

import com.android.volley.NetworkError;

import java.util.List;

import chugh.puneet.com.repos.model.data.Repo;

public interface ReposListView {
    void displayList(List<Repo> list);
    void displayError(NetworkError error);
    void hideError();
    void hideProgressBar();
    void displayProgressBar();
}
