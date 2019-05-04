package chugh.puneet.com.repos.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import chugh.puneet.com.repos.R;
import chugh.puneet.com.repos.presentors.SearchPresentor;
import chugh.puneet.com.repos.views.view_interfaces.SearchView;

import static chugh.puneet.com.repos.Contracts.ORG_NAME;
import static chugh.puneet.com.repos.Contracts.REPO_TAG;

public class SearchActivity extends AppCompatActivity implements SearchView {

    SearchPresentor presentor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presentor = new SearchPresentor();
        presentor.attachView(this);
    }

    @Override
    public void moveToNextPage(String orgName) {
        //Log.e(REPO_TAG, "moveToNextPage() called from SearchActivity");
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra(ORG_NAME, orgName);
        startActivity(intent);
    }

    @Override
    public void searchClicked(View view) {

        EditText orgNameEditText = findViewById(R.id.editText);
        presentor.actOnSearchClick(orgNameEditText.getText().toString());
    }

    @Override
    public void notifyEmptyString() {
        View view = findViewById(R.id.search_activity);
        Snackbar snackbar = Snackbar
                .make(view, getResources().getString(R.string.enter_org_name), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presentor.detachView();
    }
}
