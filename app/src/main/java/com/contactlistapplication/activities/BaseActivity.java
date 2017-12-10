package com.contactlistapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.contactlistapplication.R;
import com.contactlistapplication.interfaces.AscendingSortListener;
import com.contactlistapplication.interfaces.DescendingSortListener;

/**
 * Created by Asmita on 12/10/2017.
 */

public class BaseActivity extends AppCompatActivity{
    TextView tvToolbarTitle;
    ImageView ivAscendingOrder, ivDescendingOrder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        ViewGroup viewGroup = findViewById(R.id.container);
        viewGroup.removeAllViews();
        View view = getLayoutInflater().inflate(layoutResID, null);
        viewGroup.addView(view);
        initialise();
    }

    private void initialise() {
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        ivAscendingOrder = findViewById(R.id.ivAscendingOrder);
        ivDescendingOrder =  findViewById(R.id.ivDescendingOrder);
    }

    //Method to set custom title for every child Activity which extends BaseActivity
    public void setActionbarTitle(String value) {
        tvToolbarTitle.setText(value);
    }

    //Ascending sort icon in Actionbar will only be enable for the Activity which will
    // call this method
    public void ascendingOrderSort(final AscendingSortListener ascendingSortListener){
        ivAscendingOrder.setVisibility(View.VISIBLE);
        ivAscendingOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ascendingSortListener.onAscendingOrderClick();
            }
        });
    }

    //Descending sort icon in Actionbar will only be enable for the Activity which will
    // call this method
    public void descendingOrderSort(final DescendingSortListener descendingSortListener){
        ivDescendingOrder.setVisibility(View.VISIBLE);
        ivDescendingOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descendingSortListener.onDescendingOrderClick();
            }
        });
    }

}

