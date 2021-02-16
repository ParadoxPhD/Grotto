package com.grotto;

import android.app.DownloadManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class TL_Data extends RecyclerView.Adapter<TL_Data.TL_View>
{
    private List<String> mDataset;

    //this just describes what content is in the post view
    //TODO: this will need expanding in the future because posts will hold more than text
    public static class TL_View extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public TL_View(TextView v) {
            super(v);
            textView = v;
        }
    }

    //feeds the TL with data
    public TL_Data(List<String> myDataset) {
        mDataset = myDataset;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public TL_View onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        TL_View vh = new TL_View(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TL_View holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        String url = "http://www.paradox.fyi/post.php";
        final List<String> dataCheck = new ArrayList<String>() {};
        JsonObjectRequest data = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                dataCheck.set(0, response.toString());
                Log.i("checkit", dataCheck.get(0));
                mDataset = dataCheck;
            }
        }, null);

        holder.textView.setText(mDataset.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}