package com.samuelbernard147.smartvillage.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.samuelbernard147.smartvillage.model.Humidity;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HumidityViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Humidity>> listhumidity = new MutableLiveData<>();

    public void sethumidity(int day, int month) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Humidity> listItems = new ArrayList<>();
        String url = "http://smartvillage.starbhaktefa.com/public/api/agriculture/search/riwayat?day=" + day + "&month=" + month;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONArray responseObject = new JSONArray(result);
                    for (int i = 0; i < responseObject.length(); i++) {
                        Humidity humidity = new Humidity(responseObject, i);
                        listItems.add(humidity);
                    }
                    listhumidity.postValue(listItems);

                } catch (Exception e) {
                    Log.d("Exception GET HUMIDITY", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Exception GET HUMIDITY", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Humidity>> getHumidity() {
        return listhumidity;
    }
}
