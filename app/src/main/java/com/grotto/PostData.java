package com.grotto;

import android.inputmethodservice.Keyboard;
import android.media.Image;

import androidx.fragment.app.Fragment;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostData extends Fragment
{
    public List post;

    //String username; //username of post author
    String handle; //@ tag of post author
    int postID; //unique ID of post
    //Image avatar; //author's profile image
    String text;
    //ArrayList<String> media = new ArrayList<>(); //list of IDs for post media (text, images, videos, etc)
    //TODO: should probably make this a URL list
    //int replyID; //ID of post this is in reply to, if any
    //int favs; //how many likes?
    //int reposts; //how many reposts?
    //int replies; //how many replies?

    public void formTable (ResultSet rs, ArrayList<PostData> table) throws SQLException
    {
        if (rs == null) return;

        ResultSetMetaData rsmd = rs.getMetaData();

        int NumOfCol = rsmd.getColumnCount();

        while (rs.next())
        {
            PostData postrow = new PostData();

            for(int i = 1; i <= NumOfCol; i++)
            {
                postrow.add(rs.getInt("PostId"), rs.getString("UserHandle"), rs.getString("PostText"));
            }

            table.add(postrow);
        }
    }

    public void add (int postID, String handle, String text)
    {
        post.add(postID);
        post.add(handle);
        post.add(text);
    }
}
