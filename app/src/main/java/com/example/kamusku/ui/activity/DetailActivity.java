package com.example.kamusku.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kamusku.R;
import com.example.kamusku.data.model.Data;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL = "detail";
    Data items;
    TextView kata, terjemah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        kata = findViewById(R.id.txtKata);
        terjemah = findViewById(R.id.txtTerjemah);

        items = getIntent().getParcelableExtra(DETAIL);
        setSubtitle(items.getKata());
        kata.setText(items.getKata());
        terjemah.setText(items.getTerjemahan());

    }

    public void setSubtitle(String title){
        if (getSupportActionBar()!=null){
            getSupportActionBar().setSubtitle(title);
        }
    }
}
