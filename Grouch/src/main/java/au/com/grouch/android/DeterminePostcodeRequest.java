package au.com.grouch.android;

import android.os.AsyncTask;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * Created by amir on 5/26/13.
 */
public class DeterminePostcodeRequest extends AsyncTask<String,Void,String>{


    private    String username = "amirmas";

    @Override
    protected String doInBackground(String... strings) {

        String lat = strings[0];
        String lng = strings[1];

        String geoURL = String.format("http://api.geonames.org/findNearbyPostalCodesJSON?lat=%s&lng=%s&username=%s",lat,lng,username);
        try{
            HttpGet postCodeRequest = new HttpGet(geoURL);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            HttpClient httpClient = new DefaultHttpClient();

            String resultString  = httpClient.execute(postCodeRequest,responseHandler);

            JSONObject resultJson = new JSONObject(resultString);
            org.json.JSONArray postalCodes = resultJson.getJSONArray("postalCodes");

            if (postalCodes.length()>0){
                return postalCodes.getJSONObject(0).getString("adminName2");
            }

            return "can not determine location";


        }
        catch (Exception ex){
            return "can not determine location";
        }

    }
}
