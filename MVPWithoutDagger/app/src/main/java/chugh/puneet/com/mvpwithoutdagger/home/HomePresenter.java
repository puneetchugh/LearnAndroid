package chugh.puneet.com.mvpwithoutdagger.home;

import com.android.volley.NetworkError;

import chugh.puneet.com.mvpwithoutdagger.models.YelpData;
import chugh.puneet.com.mvpwithoutdagger.network.Service;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pchugh on 7/8/18.
 */

public class HomePresenter {

    private final Service service;
    private final HomeView homeView;
    private CompositeSubscription subscriptions;

    public HomePresenter(Service service, HomeView homeView){
        this.service = service;
        this.homeView = homeView;
        this.subscriptions = new CompositeSubscription();
    }

    public void getYelpDataList(String term, String location){

        homeView.showWait();

        Subscription subscription = service.getYelpList(new Service.GetYelpListCallBack() {
            @Override
            public void onSuccess(YelpData yelpData) {
                homeView.removeWait();
                homeView.getYelpListSuccess(yelpData);
            }

            @Override
            public void onError(NetworkError networkError) {
                homeView.removeWait();
                homeView.onFailure(networkError.getMessage());
            }
        }, term, location);

        subscriptions.add(subscription);
    }

    public void onStop(){
        subscriptions.unsubscribe();
    }
}
