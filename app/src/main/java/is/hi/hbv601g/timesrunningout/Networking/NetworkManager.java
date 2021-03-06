package is.hi.hbv601g.timesrunningout.Networking;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import is.hi.hbv601g.timesrunningout.Persistence.Word;

public class NetworkManager {
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private static NetworkManager mInstance;
    private static RequestQueue mQueue;
    private Context mContext;

    public static synchronized NetworkManager getInstance(Context context){
        if(mInstance == null) {
            mInstance = new NetworkManager(context);
        }
        return mInstance;
    }

    private NetworkManager(Context context) {
        mContext = context;
        mQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if(mQueue == null) {
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mQueue;
    }


    public void getnWords(int n, final NetworkCallback<List<Word>> callback){
        String url = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("nWords")
                .appendPath(String.valueOf(n))
                .build().toString();

        StringRequest request = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Word>>(){}.getType();
                List<Word> wordBank = gson.fromJson(response, listType);
                callback.onSuccess(wordBank);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }

    public void getWord(int id, final NetworkCallback<Word> callback){
        String url = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("words")
                .appendPath(String.valueOf(id))
                .build().toString();

        StringRequest request = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Word word = gson.fromJson(response, Word.class);
                callback.onSuccess(word);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }

    public void addWord(String word, final NetworkCallback<String> callback) {
        // calling a string request method (POST) to post the data to our API
        StringRequest request = new StringRequest(Request.Method.POST, BASE_URL + "addWord", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", "success");
                callback.onSuccess("success");
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("VOLLEY", ""+error);
                callback.onFailure(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("word", word);
                // returning our params.
                return params;
            }
        };
        mQueue.add(request);
    }

}
