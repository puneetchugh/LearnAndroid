package chugh.puneet.com.repos.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import chugh.puneet.com.repos.R;
import chugh.puneet.com.repos.presentors.ResultsPresentor;
import chugh.puneet.com.repos.views.view_interfaces.ResultsView;

import static chugh.puneet.com.repos.Contracts.ORG_NAME;
import static chugh.puneet.com.repos.Contracts.REPO_TAG;
import static chugh.puneet.com.repos.Contracts.TAG_REPOS_LIST_FRAG;

public class ResultsActivity extends AppCompatActivity implements ResultsView, ReposListFrag.OnListFragmentInteractionListener {

    ResultsPresentor resultsPresentor;
    String organization = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        //Log.e(REPO_TAG, "onCreate() of ResultsActivity called");
        resultsPresentor = new ResultsPresentor();
        resultsPresentor.attachView(this);
        String orgName = "";
        if(savedInstanceState != null){
            //Log.e(REPO_TAG, "onCreate() of ResultsActivity called...savedInstanceState not null");
            orgName = savedInstanceState.getString(ORG_NAME);
        }
        else{
            if (getIntent() != null) {
                //Log.e(REPO_TAG, "onCreate() of ResultsActivity....getIntent() is not null");
                orgName = getIntent().getStringExtra(ORG_NAME);
            }
        }
        organization = orgName;
        resultsPresentor.onResultsActivityCalled(orgName);
    }

    @Override
    public void callListFragment(String orgName) {
        //Log.e(REPO_TAG, "callListFragment() called from ResultsActivity : "+orgName);
        ReposListFrag listFragment = (ReposListFrag) getSupportFragmentManager().findFragmentByTag(TAG_REPOS_LIST_FRAG);
        if( listFragment == null){

            listFragment = ReposListFrag.newInstance(orgName);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, listFragment, TAG_REPOS_LIST_FRAG)
                    //.addToBackStack(null)
                    .commit();

            //Log.e(REPO_TAG, "callListFragment() called .... Number of fragments in stack now : "+getSupportFragmentManager().getFragments().size());
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void onListFragmentInteraction(String gitHubURL) {
        Fragment repositoryFragment = RepositoryFrag.newInstance(gitHubURL);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, repositoryFragment)
                .addToBackStack(null)
                .commit();

        //Log.e(REPO_TAG, "onListFragmentInteraction() called .... Number of fragments in stack now : "+getSupportFragmentManager().getFragments().size());
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putString(ORG_NAME, organization);
    }

    @Override
    public void onBackPressed() {
        //Log.e(REPO_TAG, "onBackPressed() called on ResultsActivity");
        if(getSupportFragmentManager().getBackStackEntryCount() >= 1){
            //Log.e(REPO_TAG, "fragments are greater than 1");
            getSupportFragmentManager().popBackStackImmediate();
        }
        else{
            //Log.e(REPO_TAG, "Less than equal to one fragment in the fragment stack");
            super.onBackPressed();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        resultsPresentor.detachView();
    }
}
