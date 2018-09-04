package chugh.puneet.com.mvpapp.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import chugh.puneet.com.mvpapp.BaseApp;
import chugh.puneet.com.mvpapp.R;
import chugh.puneet.com.mvpapp.models.Business;
import chugh.puneet.com.mvpapp.models.YelpData;
import chugh.puneet.com.mvpapp.network.Service;

public class HomeActivity extends BaseApp implements HomeView {

    public static String TAG = "MVPAPP";
    @Inject
    public Service service;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        renderView();
        getDeps().inject(this);

        HomePresenter homePresenter = new HomePresenter(service, this);
        homePresenter.getYelpDataList("pizza", "boulder");
    }

    public void renderView(){
        setContentView(R.layout.activity_main);
    }

    @Override
    public void showWait() {
        Log.e(TAG, "waiting.....");
    }

    @Override
    public void removeWait() {
        Log.e(TAG, "No more waiting....");
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getYelpListSuccess(YelpData yelpData) {
        Log.e(TAG, "getYelpListSuccess() from HomeView..");

        for(Business business : yelpData.getBusinesses()){
            Log.e("TAG", "Business Name : "+business.getName()+
                                  "Business : "+business.getRating());

        }

    }
}
