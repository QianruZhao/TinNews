package com.laioffer.tinnews.save;

import android.annotation.SuppressLint;

import com.laioffer.tinnews.TinApplication;
import com.laioffer.tinnews.database.AppDatabase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//1.4 Create SavedNewsModel which implements SavedNewsContract.Model
public class SavedNewsModel implements SavedNewsContract.Model {
    private SavedNewsContract.Presenter presenter;

    //3.4
    private final AppDatabase db;
    SavedNewsModel() {
        db = TinApplication.getDataBase();
    }

    @Override
    public void setPresenter(SavedNewsContract.Presenter presenter) {
        this.presenter = presenter;
    }



    //3.3
    @SuppressLint("CheckResult")
    @Override
    public void fetchData() {
        db.newsDao().getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(presenter::loadSavedNews, error -> {

                }, () -> {
                    System.out.println("complete");
                });
    }

}
