package chugh.puneet.com.repos.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.NetworkError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import chugh.puneet.com.repos.BaseFragment;
import chugh.puneet.com.repos.R;
import chugh.puneet.com.repos.model.data.Repo;
import chugh.puneet.com.repos.model.network.NetworkClient;
import chugh.puneet.com.repos.presentors.ReposListPresentor;
import chugh.puneet.com.repos.views.view_interfaces.ReposListView;

import static chugh.puneet.com.repos.Contracts.ORG_NAME;
import static chugh.puneet.com.repos.Contracts.REPOS_LIST;
import static chugh.puneet.com.repos.Contracts.REPO_TAG;

public class ReposListFrag extends BaseFragment implements ReposListView {

    private OnListFragmentInteractionListener mListener;
    private ReposListPresentor presentor;
    private View view;
    private List<Repo> globalRepoList;
    @Inject
    NetworkClient client;
    public ReposListFrag() {
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(String gitHubURL);
    }

    @SuppressWarnings("unused")
    public static ReposListFrag newInstance(String orgName) {
        //Log.e(REPO_TAG, "newInstance() called from ReposListFrag");
        ReposListFrag fragment = new ReposListFrag();
        Bundle args = new Bundle();
        args.putString(ORG_NAME, orgName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String orgName = "";
        getDeps().inject(this);
        presentor = new ReposListPresentor(client);
        presentor.attachView(this);
        if(savedInstanceState != null){
            //Log.e(REPO_TAG, "onCreate() called from ReposListFrag...savedInstanceState not null....");
            String savedString = savedInstanceState.getString(REPOS_LIST);
            Type type = new TypeToken<List<Repo>>(){}.getType();

            globalRepoList = new Gson().fromJson(savedString, type);
            presentor.fragmentRebuilt(globalRepoList);
        }
        else {
            if (getArguments() != null) {
                orgName = getArguments().getString(ORG_NAME);
                presentor.fetchList(orgName);
            }
            //Log.e(REPO_TAG, "onCreate() of ReposListFrag called : " + orgName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        //Log.e(REPO_TAG, "onCreateView() called...");
        setRetainInstance(true);
        List<Repo> repoList;
        if(savedInstanceState != null || globalRepoList != null) {
            if(savedInstanceState != null ) {
                //Log.e(REPO_TAG, "onCreateView() called...savedInstanceState is not null");
                String savedString = savedInstanceState.getString(REPOS_LIST);
                Type type = new TypeToken<List<Repo>>() {
                }.getType();
                repoList = new Gson().fromJson(savedString, type);
            }
            else{
                repoList = globalRepoList;
            }
            presentor.fragmentRebuilt(repoList);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        //Log.e(REPO_TAG, "onAttach() of ReposListFrag called..");
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        //Log.e(REPO_TAG, "onDestroyView() called from RepoListFrag");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presentor.unsubscribe();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void displayList(List<Repo> reposList) {
        //Log.e(REPO_TAG, "displayList() called from ReposListFrag");
        RecyclerView recyclerView = view.findViewById(R.id.list);;
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new ReposAdapter(reposList, mListener));
        globalRepoList = reposList;
    }

    @Override
    public void displayError(NetworkError errorMessage) {
        RelativeLayout error = view.findViewById(R.id.id_error_message);
        error.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError(){
        RelativeLayout errorLayout = view.findViewById(R.id.id_error_message);
        errorLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar(){
        ProgressBar progressBar = view.findViewById(R.id.id_progress_bar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displayProgressBar(){
        ProgressBar progressBar = view.findViewById(R.id.id_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        //Log.e(REPO_TAG, "onSaveInstanceState() from ReposListFrag called");
        if(globalRepoList != null){
            Gson gson = new Gson();
            String jsonRepos = gson.toJson(globalRepoList);
            bundle.putString(REPOS_LIST, jsonRepos);
        }
    }
}
