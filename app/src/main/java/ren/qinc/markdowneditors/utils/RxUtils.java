package ren.qinc.markdowneditors.utils;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Rx工具类
 * Created by 沈钦赐 on 16/1/26.
 */
public class RxUtils {
    public static ObservableTransformer transformer;


    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<T, T> applySchedulersIoAndMainThread() {
        return (ObservableTransformer<T, T>) getScheduler();
    }

    @SuppressWarnings("unchecked")
    private static <T> ObservableTransformer<T, T> getScheduler() {
        if (transformer == null) {
            ObservableTransformer<T, T> ttTransformer = observable -> observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            transformer = ttTransformer;
            return ttTransformer;
        } else {
            return transformer;
        }
    }
}
