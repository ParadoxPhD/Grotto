package com.grotto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

public class ViewPost extends ListFragment
{
    // need this to make fragment show up in timeline
    // and/or post page?

    public ViewPost() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.post, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter adapter;
        //adapter = ArrayAdapter.createFromResource(getActivity(), 3, R.layout.post);
        //setListAdapter(adapter);

        getListView().setOnClickListener((View.OnClickListener) this); //?
    }
}
