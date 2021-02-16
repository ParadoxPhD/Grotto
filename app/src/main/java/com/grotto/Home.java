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
        //set up layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        //this button lets you make posts
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
