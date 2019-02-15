package com.example.countrycapital;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    TextView name_input;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String namePLayer = getArguments().getString("namePlayer");
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        name_input = (TextView) view.findViewById(R.id.name_input);
        name_input.setText(namePLayer);

        // Inflate the layout for this fragment
        return view;



    }

}
