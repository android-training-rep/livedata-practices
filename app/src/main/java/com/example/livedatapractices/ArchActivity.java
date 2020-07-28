package com.example.livedatapractices;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ArchActivity extends AppCompatActivity {

    ArchViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch);

        final TextView liveDataView = findViewById(R.id.livedata_count);

        model = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ArchViewModel.class);

        Observer<Integer> countObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newCount) {
                liveDataView.setText(newCount.toString());
            }
        };
        model.getCount().observe(this, countObserver);

        Button increaseBtn = findViewById(R.id.btn_increase);
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.increase();
            }
        });

        Button stopBtn = findViewById(R.id.btn_stop);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.onCleared();
            }
        });
    }
}