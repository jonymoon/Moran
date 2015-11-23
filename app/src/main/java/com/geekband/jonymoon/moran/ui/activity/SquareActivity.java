package com.geekband.jonymoon.moran.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;

import com.geekband.jonymoon.moran.R;

public class SquareActivity extends AppCompatActivity {
    private Spinner mSpinner;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square);

        mSpinner = (Spinner) findViewById(R.id.distance_spinner_square);
        mListView = (ListView) findViewById(R.id.square_list_view);
    }
}