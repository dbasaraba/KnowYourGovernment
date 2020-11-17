package com.basa.knowyourgovernment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GoogleCivicAPIRunnable implements Runnable {

    private MainActivity mainActivity;
    private ArrayList<Official> officials = new ArrayList<>();
    private String location;

    GoogleCivicAPIRunnable(MainActivity mainActivity) { this.mainActivity = mainActivity; }

    @Override
    public void run() {
        StringBuilder newUrl = new StringBuilder("https://www.googleapis.com/civicinfo/v2/representatives?key=");
        newUrl.append("AIzaSyAzRQESGzEYCwLofAKtXnsucbGAd-0C3D4");
        newUrl.append("&address=");
        newUrl.append(mainActivity.getLocation());

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
                mainActivity.clear();
                mainActivity.setData(location, officials);
                officials.clear();
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

            JSONArray officesJSON = obj.getJSONArray("offices");
            JSONArray officialsJSON = obj.getJSONArray("officials");

            for (int i = 0; i < officesJSON.length(); i++) {
                JSONObject office = officesJSON.getJSONObject(i);
                JSONArray indices = office.getJSONArray("officialIndices");

                for (int j = 0; j < indices.length(); j++) {
                    Official official = new Official();
                    JSONObject o = officialsJSON.getJSONObject(indices.getInt(j));

                    official.setOffice(office.getString("name"));
                    official.setName(o.getString("name"));
                    official.setParty(o.getString("party"));

                    if(o.has("photoUrl")) {
                        official.setPhotoURL(o.getString("photoUrl"));
                    }
                    if (o.has("address")) {
                        JSONArray addresses = o.getJSONArray("address");
                        JSONObject address = addresses.getJSONObject(0);
                        official.setAddressLineOne(address.getString("line1"));
                        official.setAddressCity(address.getString("city"));
                        official.setAddressState(address.getString("state"));
                        official.setAddressZip(address.getString("zip"));
                    }
                    if (o.has("phones")) {
                        JSONArray phones = o.getJSONArray("phones");
                        official.setPhone(phones.getString(0));
                    }
                    if (o.has("urls")) {
                        JSONArray urls = o.getJSONArray("urls");
                        official.setWebsite(urls.getString(0));
                    }
                    if (o.has("email")) {
                        JSONArray emails = o.getJSONArray("emails");
                        official.setEmail(emails.getString(0));
                    }
                    if (o.has("channels")) {
                        JSONArray channels = o.getJSONArray("channels");

                        for (int z = 0; z < channels.length(); z++) {
                            JSONObject channel = channels.getJSONObject(z);

                            if (channel.getString("type").equals("Facebook")) {
                                official.setFacebookId(channel.getString("id"));
                            }
                            if (channel.getString("type").equals("Twitter")) {
                                official.setTwitterId(channel.getString("id"));
                            }
                            if (channel.getString("type").equals("YouTube")) {
                                official.setYoutubeId(channel.getString("id"));
                            }
                        }
                    }

                    this.officials.add(official);
                }

            }

        }
        catch (JSONException e) { e.printStackTrace(); }
    }

}
