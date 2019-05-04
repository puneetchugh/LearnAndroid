package chugh.puneet.com.repos.views;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import chugh.puneet.com.repos.R;
import chugh.puneet.com.repos.presentors.RepositoryPresentor;
import chugh.puneet.com.repos.views.view_interfaces.RepositoryView;

import static chugh.puneet.com.repos.Contracts.GITHUB_URL;
import static chugh.puneet.com.repos.Contracts.REPO_TAG;

public class RepositoryFrag extends Fragment implements RepositoryView {

    private String gitHubURL;
    private View view;
    private RepositoryPresentor presentor;
    public RepositoryFrag() {
        // Required empty public constructor
    }

    public static RepositoryFrag newInstance(String param1) {
        //Log.e(REPO_TAG, "newInstance() called from RepositoryFrag : "+param1);
        RepositoryFrag fragment = new RepositoryFrag();
        Bundle args = new Bundle();
        args.putString(GITHUB_URL, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presentor = new RepositoryPresentor();
        presentor.attachView(this);
        if (getArguments() != null) {
            gitHubURL = getArguments().getString(GITHUB_URL);
            presentor.loadRepo(gitHubURL);
            //Log.e(REPO_TAG, "onCreate() called from RepositoryFrag : "+gitHubURL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_repository, container, false);
       //Log.e(REPO_TAG, "onCreateView() called from RepositoryFrag");
       return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void displayRepository(String repoURL) {
        //Log.e(REPO_TAG, "displayRepository() called from RepositoryFrag : "+repoURL);
        //Log.e(REPO_TAG, "Inside displayRepository() ..view is "+view);

            new CountDownTimer(20,20) {
                @Override
                public void onTick(long l) { }

                @Override
                public void onFinish() {
                    if(view != null) {
                        //Log.e(REPO_TAG, "Opening the web in webview");
                        WebView webView = view.findViewById(R.id.webview);
                        webView.setWebViewClient(new WebViewClient());
                        //WebSettings webSettings = webView.getSettings();
                        //webSettings.setJavaScriptEnabled(true);
                        webView.loadUrl(repoURL);
                    }
                }
            }.start();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        //Log.e(REPO_TAG, "onDestroyView() of RepositoryFrag called..");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void displayError() {
        //Log.e(REPO_TAG, "displayError() called from RepositoryFrag");
    }
}
