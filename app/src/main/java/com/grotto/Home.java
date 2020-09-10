package com.grotto;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Home extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        //TODO: if login session expired, launch the login page

        final ImageButton makePost = findViewById(R.id.startPost);

        makePost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO: Launch post_create.xml popup
                MakePost mp = new MakePost();
                mp.show(getSupportFragmentManager(), "post");
            }
        });

        //now to actually get content
        //this will be replaced by some api because apparently connecting directly to db from app is bad
        /*ConnectionPlus conn = new ConnectionPlus();
        plugit = conn.Connect();
        Statement request;
        String query = "SELECT POSTTEXT FROM dbo.Posts";
        ResultSet response = null;
        try
        {request = plugit.createStatement();
         response = request.executeQuery(query);}
        catch (SQLException e)
        {e.printStackTrace();}

        if (response != null)
        {
            List<PostData> posts = new ArrayList<>();
            PostData row = new PostData();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragtrans = fragmentManager.beginTransaction();

            for (int i = 0; i < row.post.size()/3; i++)
            {
                Fragment post = new PostData();
                fragtrans.add(R.id.post, post);
            }

        }
        else {}*/



    }

    public void makePost(ResultSet response) throws SQLException
    {
        if(response.next())
        {
            for (int i = 0; i < response.getFetchSize(); i++)
            {
                Fragment post = new MakePost();

            }
        }
        else
        {
            //no posts??
        }
    }

    public void Popup(View view)
    {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.notif, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
