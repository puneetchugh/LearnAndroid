package chugh.puneet.com.mvpwithoutdagger.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import chugh.puneet.com.mvpwithoutdagger.R;
import chugh.puneet.com.mvpwithoutdagger.models.Business;
import chugh.puneet.com.mvpwithoutdagger.models.YelpData;
import chugh.puneet.com.mvpwithoutdagger.network.NetworkModule;
import chugh.puneet.com.mvpwithoutdagger.network.Service;

public class HomeActivity extends AppCompatActivity implements HomeView {

    public static String TAG = "MVPAPP";
    public Service service;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        renderView();
        service = NetworkModule.providesService();

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
