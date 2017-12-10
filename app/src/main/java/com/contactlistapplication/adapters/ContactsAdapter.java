package com.contactlistapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.contactlistapplication.R;
import com.contactlistapplication.activities.ContactDetailsActivity;
import com.contactlistapplication.activities.ContactListingActivity;
import com.contactlistapplication.models.Contacts;

import java.util.List;

/**
 * Created by Asmita on 12/9/2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private Context context;
    private List<Contacts> contactsList;

    public ContactsAdapter(Context context, List<Contacts> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Contacts contactsItem = contactsList.get(position);
        holder.tvName.setText(contactsItem.getName());
        holder.tvUserName.setText(context.getString(R.string.username) + " " + contactsItem.getUsername());
        holder.tvEmail.setText(context.getString(R.string.email) + " " + contactsItem.getEmail());
        holder.tvPhone.setText(context.getString(R.string.phone) + " " + contactsItem.getPhone());
        holder.tvWebsite.setText(context.getString(R.string.website) + " " + contactsItem.getWebsite());

        holder.tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+contactsItem.getLat()+","+contactsItem.getLng()));
                context.startActivity(intent);
            }
        });

        holder.cvContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tips_details_intent = new Intent(context, ContactDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", contactsItem.getName());
                bundle.putString("street_name", contactsItem.getStreet());
                bundle.putString("suite", contactsItem.getSuite());
                bundle.putString("city", contactsItem.getCity());
                bundle.putString("zipcode", contactsItem.getZipcode());
                bundle.putString("company_name", contactsItem.getCompanyName());
                bundle.putString("company_phrase", contactsItem.getCatchPhrase());
                bundle.putString("company_bs", contactsItem.getBs());
                tips_details_intent.putExtras(bundle);
                context.startActivity(tips_details_intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvUserName, tvEmail, tvPhone, tvWebsite,tvLocation;
        CardView cvContacts;

        public ViewHolder(View itemView) {
            super(itemView);
            cvContacts = itemView.findViewById(R.id.cvContacts);
            tvName = itemView.findViewById(R.id.tvName);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvWebsite = itemView.findViewById(R.id.tvWebsite);
            tvLocation = itemView.findViewById(R.id.tvLocation);

        }
    }
}
