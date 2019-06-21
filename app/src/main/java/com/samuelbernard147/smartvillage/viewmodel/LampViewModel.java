package com.samuelbernard147.smartvillage.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.samuelbernard147.smartvillage.model.Lamp;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class LampViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Lamp>> listLamp = new MutableLiveData<>();

    public void setLamp() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Lamp> listItems = new ArrayList<>();
        String url = "http://smartvillage.starbhaktefa.com/public/api/lampu";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONArray responseObject = new JSONArray(result);
                    for (int i = 0; i < responseObject.length(); i++) {
                        Lamp lamp = new Lamp(responseObject, i);
                        listItems.add(lamp);
                    }
                    listLamp.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception GET LAMP ", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("On Failure GET LAMP ", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Lamp>> getLamp() {
        return listLamp;
    }
}
