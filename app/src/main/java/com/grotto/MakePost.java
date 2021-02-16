package com.grotto;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MakePost extends DialogFragment
{
    //private ViewPost mViewModel;
    public PostData postdata = new PostData();

    public static MakePost newInstance() {
        return new MakePost();
    }

    ImageButton makePost;
    EditText postText;

    //this makes the window where you make the post
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.post_create);
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setView(R.layout.post_create);
        //return builder.create();
        return dialog;
    }

    //this plugs the layout into the window
    //@Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.post_create, container, false);
        makePost = view.findViewById(R.id.makePost);
        postText = view.findViewById(R.id.postText);

        return view;
    }

    //everything the window and view actually do is in here
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = ViewModelProviders.of(this).get(ViewPost.class);
        // TODO: Use the ViewModel

        //when we click the button to submit the post, alll this happens
        makePost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Context context = getContext();
                RequestQueue queue = Volley.newRequestQueue(context);
                String url = "http://paradox.fyi/post.php";
                JSONObject jsonBody = new JSONObject();
                try
                {
                    jsonBody.put("user", "poster");
                    jsonBody.put("password", "f6tWrByGRS4NrbX");
                    jsonBody.put("id", 37);
                    jsonBody.put("handle", "paradox");
                    jsonBody.put("text", postText.getText().toString());
                }
                catch (JSONException error)
                {
                    Log.e("JSON error", error.getMessage());
                }

                final String requestBody = jsonBody.toString();
                //TODO: request gets 412'd, why?

                StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            Log.i("Response Info", response);
                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Log.e("Error", error.toString());
                            //fails because null sometimes
                        }
                    })
                    {
                        @Override
                        public String getBodyContentType() { return "application/json; charset=utf-8"; }

                        @Override
                        public byte[] getBody() throws AuthFailureError
                        {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response)
                        {
                            String responseString = "";
                            List<Header> responseHeaders;
                            if (response != null)
                            {
                                responseString = String.valueOf(response.statusCode);
                                // can get more details such as response.headers
                                responseHeaders = response.allHeaders;
                                for (int i = 0; i < responseHeaders.size(); i++)
                                {
                                    Log.i("Header", responseHeaders.get(i).toString());
                                }
                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                queue.add(request);


                //TODO: I should have post data object IDs to order them, so I can have text and images alternating and whatever
            }
        });
    }

    public void generateID()
    {
        Random rand = new Random();
        String id = "";

        for (int i = 0; i < 15; i++)
        {
            rand.nextInt(10);
            id.concat(rand.toString()); //do I need to check for already existing IDs? Probably.
        }

        postdata.postID = Integer.parseInt(id);
    }

    public void setText()
    {
        //postdata.media.add(postText.getText().toString());
        //grabs text entered into post creation modal and sets the eventual post's text data to that string
        postdata.text = postText.getText().toString();
    }

    private String readStream(InputStream is) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}
