package com.contactlistapplication.activities;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.contactlistapplication.R;
import com.squareup.picasso.Picasso;

public class ContactDetailsActivity extends BaseActivity {
    TextView tvName,tvAddress,tvCompany,tvCompanyCatchPhrase,tvCompanybs;
    String name,streetName,suite,city,zipcode,companyName,companyCatchPhrase,companybs;
    ImageView ivMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        initialise();
    }

    @SuppressLint("SetTextI18n")
    private void initialise() {
        setActionbarTitle(getString(R.string.contacts_details));
        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvAddress);
        tvCompany = findViewById(R.id.tvCompany);
        tvCompanyCatchPhrase = findViewById(R.id.tvCompanyCatchPhrase);
        tvCompanybs = findViewById(R.id.tvCompanybs);
        ivMap = findViewById(R.id.ivMap);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            streetName = bundle.getString("street_name");
            suite = bundle.getString("suite");
            city = bundle.getString("city");
            zipcode = bundle.getString("zipcode");
            companyName = bundle.getString("company_name");
            companyCatchPhrase = bundle.getString("company_phrase");
            companybs = bundle.getString("company_bs");
        }
        tvName.setText(name);
        tvAddress.setText(streetName +" " + suite + " " + city + " " + zipcode);
        tvCompany.setText(getString(R.string.company_name)+ " "+ companyName);
        tvCompanyCatchPhrase.setText(companyCatchPhrase);
        tvCompanybs.setText(companybs);

        tvCompanyCatchPhrase.setTypeface(tvCompanyCatchPhrase.getTypeface(), Typeface.ITALIC);

        //Google API to Load Static Map Images by passing a single parameter city or street name
        String url = "https://maps.googleapis.com/maps/api/staticmap?size=512x512&zoom=12" +
                "&center=" + streetName + "&format=png&style=feature:road.highway" +
                "%7Celement:geometry%7Cvisibility:simplified%7Ccolor:0xc280e9" +
                "&style=feature:transit.line%7Cvisibility:simplified%7Ccolor:0xbababa" +
                "&style=feature:road.highway%7Celement:labels.text.stroke%7Cvisibility:on" +
                "%7Ccolor:0xb06eba&style=feature:road.highway%7Celement:labels.text.fill%" +
                "7Cvisibility:on%7Ccolor:0xffffff&key="+getString(R.string.API_KEY);

        //Use of third party library Picasso to load the images
        Picasso.with(this).load(url).into(ivMap);
    }
}
