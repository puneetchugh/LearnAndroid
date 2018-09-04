package chugh.puneet.com.mvpwithoutdagger.home;


import chugh.puneet.com.mvpwithoutdagger.models.YelpData;

/**
 * Created by pchugh on 7/8/18.
 */

public interface HomeView {

    void showWait();
    void removeWait();
    void onFailure(String appErrorMessage);
    void getYelpListSuccess(YelpData yelpData);
}
