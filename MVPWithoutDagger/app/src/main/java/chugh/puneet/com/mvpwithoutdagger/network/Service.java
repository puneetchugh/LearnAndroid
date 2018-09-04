package chugh.puneet.com.mvpwithoutdagger.network;

import com.android.volley.NetworkError;


import chugh.puneet.com.mvpwithoutdagger.models.YelpData;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pchugh on 7/8/18.
 */

public class Service {

    private final NetworkService networkService;

    public Service(NetworkService networkService){
        this.networkService = networkService;
    }

    public Subscription getYelpList(final Service.GetYelpListCallBack callback, String term , String location) {

        return networkService.getYelpData(term, location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends YelpData>>() {
                    @Override
                    public Observable<? extends YelpData> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<YelpData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(YelpData yelpData) {
                        callback.onSuccess(yelpData);

                    }
                });
    }

    public interface GetYelpListCallBack{

        void onSuccess(YelpData yelpData);
        void onError(NetworkError networkError);
    }
}
