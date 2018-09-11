package com.laioffer.tinnews.tin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.common.TinBasicFragment;
import com.laioffer.tinnews.mvp.MvpFragment;
import com.laioffer.tinnews.retrofit.NewsRequestApi;
import com.laioffer.tinnews.retrofit.RetrofitClient;
import com.laioffer.tinnews.retrofit.response.News;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class TinGalleryFragment extends MvpFragment<TinContract.Presenter> implements TinNewsCard.OnSwipeListener, TinContract.View {


//    public TinGalleryFragment() {
//        // Required empty public constructor
//    }

    private SwipePlaceHolderView mSwipeView;

    public static TinGalleryFragment newInstance() {

        Bundle args = new Bundle();

        TinGalleryFragment fragment = new TinGalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tin_gallery, container, false);

        mSwipeView = view.findViewById(R.id.swipeView);

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tin_news_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tin_news_swipe_out_msg_view));

        view.findViewById(R.id.rejectBtn).setOnClickListener(v -> mSwipeView.doSwipe(false));

        view.findViewById(R.id.acceptBtn).setOnClickListener(v -> mSwipeView.doSwipe(true));

        //fake date
//        for (int i = 0; i < 10; i++) {
//            News news = new News();
//            news.image = "https://i.ytimg.com/vi/BgIJ45HKDpw/maxresdefault.jpg";
//            TinNewsCard tinNewsCard = new TinNewsCard(news, mSwipeView, this);
//            mSwipeView.addView(tinNewsCard);
//        }

        //replace from here
//        getDate();
        //end here


        return view;
    }

    //below onCreateView

//    private void getDate() {
//        RetrofitClient.getInstance().create(NewsRequestApi.class).getNewsByCountry("us")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .filter(baseResponse -> baseResponse != null && baseResponse.articles != null)
//                .subscribe(baseResponse -> {
//                    showNewsCard(baseResponse.articles);
//                });
//    }

    public void showNewsCard(List<News> newsList) {
//        for (News news : newsList) {
//            TinNewsCard tinNewsCard = new TinNewsCard(news, mSwipeView, this);
//            mSwipeView.addView(tinNewsCard);
//        }
        //7.7
        mSwipeView.removeAllViews();

        for (News news : newsList) {
            TinNewsCard tinNewsCard = new TinNewsCard(news, mSwipeView, this);
            mSwipeView.addView(tinNewsCard);
        }

    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "News has been inserted before", Toast.LENGTH_SHORT).show();
    }

//on top of onLike


    //8.5 TinGalleryFragment triggers the saveFavoriteNews
    @Override
    public void onLike(News news) {

        presenter.saveFavoriteNews(news);
    }


    @Override
    public TinContract.Presenter getPresenter() {
        return new TinPresenter();
    }
}
