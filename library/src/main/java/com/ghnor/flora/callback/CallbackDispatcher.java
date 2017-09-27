package com.ghnor.flora.callback;

/**
 * Created by ghnor on 2017/6/21.
 * email: ghnor.me@gmail.com
 * desc:
 */

public interface CallbackDispatcher<T> {

    void dispatch(T t);

    void cancel();
}
