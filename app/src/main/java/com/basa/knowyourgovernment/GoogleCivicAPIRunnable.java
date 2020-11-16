package com.basa.knowyourgovernment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class GoogleCivicAPIRunnable implements Runnable {

    private MainActivity mainActivity;
    private ArrayList<Official> officials = new ArrayList<>();
    private String location;

    GoogleCivicAPIRunnable(MainActivity mainActivity, String location) {
        this.mainActivity = mainActivity;
        this.location = location;
    }

    @Override
    public void run() {
        StringBuilder newUrl = new StringBuilder("https://www.googleapis.com/civicinfo/v2/representatives?key=");
        newUrl.append("AIzaSyAzRQESGzEYCwLofAKtXnsucbGAd-0C3D4");
        newUrl.append("&address=60035");
//        newUrl.append(location);

        try {
            URL url = new URL(newUrl.toString());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.mToast("Download Failed");
                    }
                });

                return;
            }

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            parseObject(sb.toString());
        }
        catch (Exception e) { e.printStackTrace(); }

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.setData(location);
            }
        });
    }

    private void parseObject(String s) {

        try {
            JSONObject obj = new JSONObject(s);
            JSONObject normalizedInput = obj.getJSONObject("normalizedInput");
            String city = normalizedInput.getString("city");
            String state = normalizedInput.getString("state");
            String zip = normalizedInput.getString("zip");

            location = city + ", " + state + ' ' + zip;
        }
        catch (JSONException e) { e.printStackTrace(); }
    }

}
