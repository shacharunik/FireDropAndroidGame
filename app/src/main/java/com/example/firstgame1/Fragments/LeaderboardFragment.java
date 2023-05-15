package com.example.firstgame1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import com.example.firstgame1.Interfaces.CallbackMark;
import com.example.firstgame1.Models.Scores;
import com.example.firstgame1.R;
import com.example.firstgame1.Utilities.MySPv3;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardFragment extends Fragment {

    private CallbackMark callbackMark;
    private ListView list_LST_scores;

    public void setCallbackMark(CallbackMark callbackMark) {
        this.callbackMark = callbackMark;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        findViews(view);
        String str_json = MySPv3.getInstance().getString("Scores","");
        Scores scores_json = new Gson().fromJson(str_json, Scores.class);
        List<String> itemList = new ArrayList<>();

        if (scores_json != null) {
            for (int i = 0; i < scores_json.getRecords().size(); i++) {
                itemList.add(scores_json.getRecords().get(i).getScore()+ "");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),
                    android.R.layout.simple_list_item_1, itemList);
            list_LST_scores.setAdapter(adapter);
            list_LST_scores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    callbackMark.markPoint(scores_json.getRecords().get(position).getLatitude(),scores_json.getRecords().get(position).getLongitude());
                }
            });
        }
        else {
            return view;
        }

        return view;
    }


    private void findViews(View view) {
        list_LST_scores = view.findViewById(R.id.list_LST_scores);
    }

}