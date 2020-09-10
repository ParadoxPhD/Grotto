package com.grotto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

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

    //@Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.post_create, container, false);
        makePost = view.findViewById(R.id.makePost);
        postText = view.findViewById(R.id.postText);

        return view;
    }

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
                TL_Activity run = new TL_Activity();
                String data_ = postText.getText().toString();
                run.data.add(0, data_);

                TL_Data info = new TL_Data(run.data);
                synchronized (info){info.notifyItemInserted(0);}
                dismiss();

                /*Home home = new Home(); //why?
                generateID(); //post identifier
                //should have a whole function for grabbing data from the modal and submitting it
                setText();
                String query = "INSERT INTO dbo.Posts VALUES (" + postdata.postID + postdata.handle + postdata.text + ")";

                try
                {
                    //this all just tells the db to put the post data into the table
                    //TODO: I should have post data object IDs to order them, so I can have text and images alternating and whatever
                    Statement request;
                    request = home.plugit.createStatement();
                    request.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }*/
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

}
