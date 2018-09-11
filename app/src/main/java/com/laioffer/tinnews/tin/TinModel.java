package com.laioffer.tinnews.tin;

import android.annotation.SuppressLint;

import com.laioffer.tinnews.TinApplication;
import com.laioffer.tinnews.database.AppDatabase;
import com.laioffer.tinnews.retrofit.NewsRequestApi;
import com.laioffer.tinnews.retrofit.RetrofitClient;
import com.laioffer.tinnews.retrofit.response.News;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TinModel implements TinContract.Model {

    //keep the reference of TinContract.Presenter
    private TinContract.Presenter presenter;

    //move the Retrofit client here
    private final NewsRequestApi newsRequestApi;

    //8.2 add db reference
    private final AppDatabase db;


    public TinModel() {
        newsRequestApi = RetrofitClient.getInstance().create(NewsRequestApi.class);
        //8.2 add db reference
        db = TinApplication.getDataBase();
    }

//move the Retrofit client here


    @Override
    public void setPresenter(TinContract.Presenter presenter) {
        //assign the presenter
        this.presenter = presenter;
    }


    //implement the fetchData here
    @Override
    public void fetchData(String country) {
        //5.4 make the request in the Model
//        newsRequestApi.getNewsByCountry("us")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .filter(baseResponse -> baseResponse != null && baseResponse.articles != null)
//                .subscribe(baseResponse -> {
//                    //5.8 pass the fetch data to the model
//                    presenter.showNewsCard(baseResponse.articles);
//                });
        //5.4 make the request in the Model
        newsRequestApi.getNewsByCountry(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(baseResponse -> baseResponse != null && baseResponse.articles != null)
                .subscribe(baseResponse -> {
                    //5.8 pass the fetch data to the model
                    presenter.showNewsCard(baseResponse.articles);
                });

    }

    //implement the saveFavoriteNews
    @SuppressLint("CheckResult")
    @Override
    public void saveFavoriteNews(News news) {
        Completable.fromAction(() -> db.newsDao().insertNews(news)).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(() ->{

        }, error -> {
            presenter.onError();
        });
    }




}
