package chugh.puneet.com.mvpapp.home;

import chugh.puneet.com.mvpapp.models.YelpData;

/**
 * Created by pchugh on 7/8/18.
 */

public interface HomeView {

    void showWait();
    void removeWait();
    void onFailure(String appErrorMessage);
    void getYelpListSuccess(YelpData yelpData);
}
