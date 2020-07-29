package com.example.livedatapractices;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArchViewModel extends ViewModel {
    private MutableLiveData<Integer> count;
    public static final String TAG =  "Arch Model";
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<Integer> getCount() {
        if(count == null) {
            count = new MutableLiveData<Integer>();
            count.postValue(0);
        }
        return count;
    }
    public void increase() {
        Observer observer = new Observer<Object>(){

            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "in onNext");
                try {
                    Integer current = Integer.parseInt(count.getValue().toString());
                    count.postValue(++current);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((io.reactivex.Observer) observer);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
