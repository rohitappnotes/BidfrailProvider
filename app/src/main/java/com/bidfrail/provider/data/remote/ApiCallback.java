package com.bidfrail.provider.data.remote;

import com.bidfrail.provider.data.remote.exception.ExceptionHelper;
import com.bidfrail.provider.data.remote.exception.NetworkException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        System.out.println("code : "+response.code());
        System.out.println("errorBody : "+response.errorBody());
        System.out.println("body : "+response.body());
        onSuccess(response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        onFailure(ExceptionHelper.getInstance().handleException(throwable));
    }

    /**
     * onResponse callback
     *
     * @param response
     */
    public abstract void onSuccess(Response<T> response);

    /**
     * onFailure callback
     *
     * @param networkException
     */
    public abstract void onFailure(NetworkException networkException);
}