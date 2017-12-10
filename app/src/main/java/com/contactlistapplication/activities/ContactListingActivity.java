package com.contactlistapplication.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.contactlistapplication.R;
import com.contactlistapplication.adapters.ContactsAdapter;
import com.contactlistapplication.interfaces.AscendingSortListener;
import com.contactlistapplication.interfaces.DescendingSortListener;
import com.contactlistapplication.interfaces.NetworkAPI;
import com.contactlistapplication.models.Contacts;
import com.contactlistapplication.webservices.HttpHelper;
import com.contactlistapplication.widgets.CustomDialogManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class ContactListingActivity extends BaseActivity {

    RecyclerView rvContactsList;
    ContactsAdapter contactsAdapter;
    ArrayList<Contacts> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_listing);
        initialise();
        if (contactsList.isEmpty()) {
            getContactListing(ContactListingActivity.this);
        }
    }

    private void initialise() {
        setActionbarTitle(getString(R.string.contacts));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        rvContactsList = findViewById(R.id.rvContactsList);

        if (contactsList == null) {
            contactsList = new ArrayList<>();
        }
        contactsAdapter = new ContactsAdapter(ContactListingActivity.this, contactsList);
        initialiseRecyclerView();

        ascendingOrderSort(new AscendingSortListener() {
            @Override
            public void onAscendingOrderClick() {
                Collections.sort(contactsList, new Comparator<Contacts>() {
                    @Override
                    public int compare(Contacts contacts, Contacts t1) {

                        return contacts.getName().compareTo(t1.getName());
                    }
                });
                contactsAdapter.notifyDataSetChanged();
            }
        });

        descendingOrderSort(new DescendingSortListener() {
            @Override
            public void onDescendingOrderClick() {
                Collections.reverse(contactsList);
                contactsAdapter.notifyDataSetChanged();
            }
        });
    }

    //Initialising the Recycler View
    private void initialiseRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager
                (ContactListingActivity.this);
        rvContactsList.setLayoutManager(mLayoutManager);
        rvContactsList.setItemAnimator(new DefaultItemAnimator());
        rvContactsList.setAdapter(contactsAdapter);
    }
    //Webservice to implement Contact Listing
    private void getContactListing(Context context) {
        if (HttpHelper.isNetworkAvailable(context)) {
            final Dialog mDialog = CustomDialogManager.showProgressDialog(context);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpHelper.BASE_URL)
                    .setConverter(new GsonConverter(new GsonBuilder().create()))
                    .build();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            api.getContactList(new Callback<JsonArray>() {
                @Override
                public void success(JsonArray responseObject, Response response) {
                    mDialog.dismiss();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                    Log.d("Response-->", responseObject.toString());
                    try {

                        JSONArray jsonArray = new JSONArray(responseObject.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //Fetching user data
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Contacts contactsItem = new Contacts();
                            contactsItem.setId(jsonObject.optInt("id"));
                            contactsItem.setName(jsonObject.optString("name"));
                            contactsItem.setUsername(jsonObject.optString("username"));
                            contactsItem.setEmail(jsonObject.optString("email"));
                            contactsItem.setPhone(jsonObject.optString("phone"));
                            contactsItem.setWebsite(jsonObject.optString("website"));

                            //Fetching data of Address Object
                            JSONObject addressObject = jsonObject.getJSONObject("address");
                            contactsItem.setStreet(addressObject.optString("street"));
                            contactsItem.setSuite(addressObject.optString("suite"));
                            contactsItem.setCity(addressObject.optString("city"));
                            contactsItem.setZipcode(addressObject.optString("zipcode"));

                            //Fetching data of Geo Object
                            JSONObject geoObject = addressObject.getJSONObject("geo");
                            contactsItem.setLat(geoObject.optString("lat"));
                            contactsItem.setLng(geoObject.optString("lng"));

                            //Fetching data of Company Object
                            JSONObject companyObject = jsonObject.getJSONObject("company");
                            contactsItem.setCompanyName(companyObject.optString("name"));
                            contactsItem.setCatchPhrase(companyObject.optString
                                    ("catchPhrase"));
                            contactsItem.setBs(companyObject.optString("bs"));
                            contactsList.add(contactsItem);
                        }


                        if (contactsAdapter != null)
                            contactsAdapter.notifyDataSetChanged();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    mDialog.dismiss();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

                }
            });

        }
        else {
            CustomDialogManager.showOkDialog(this,
                    getString(R.string.no_internet_connection));
        }

    }
}
