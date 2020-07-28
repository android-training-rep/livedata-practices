package com.example.livedatapractices;

import android.os.SystemClock;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArchViewModel extends ViewModel {
    private MutableLiveData<Integer> count;
    public static final String TAG =  "Arch Model";

    public MutableLiveData<Integer> getCount() {
        if(count == null) {
            count = new MutableLiveData<Integer>();
            count.postValue(0);
        }
        return count;
    }
    public void increase() {
        Observer observer = new Observer<Integer>(){

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext current="+integer);
                count.postValue(integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Integer current = Integer.parseInt(count.getValue().toString());
                SystemClock.sleep(1000);
                 ++current;
                 emitter.onNext(current);
            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((io.reactivex.Observer) observer);
    }
}
