package com.example.goszj.contactsapplication;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by goszj on 13.12.2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private ArrayList<PojoContact> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements ContactsItemListener.ContactTouchListener {
        // each data item is just a string in this case
        protected TextView nameTextView;
        protected TextView numberTextView;
        protected ImageView itemImageView;
        public ViewHolder(View v) {
            super(v);
            nameTextView = (TextView) v.findViewById(R.id.nameTextView);
            numberTextView = (TextView) v.findViewById(R.id.numberTextView);
            itemImageView = (ImageView) v.findViewById(R.id.itemImageView);

        }

        @Override
        public void onClickItem(View v, int position) {
            Toast.makeText(v.getContext(),"clicked",Toast.LENGTH_SHORT).show();;
        }

        @Override
        public void onLongTouchItem(View v, int position) {

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactsAdapter(ArrayList<PojoContact> myDataset) {
        mDataset = myDataset;
        Log.d("debug","madataset set");
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contacts_item_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(mDataset.get(position).getName());
       holder.numberTextView.setText(mDataset.get(position).getPhoneNumber());
        holder.itemImageView.setImageResource(mDataset.get(position).getImageDrawable());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void removeItem(int position){
        mDataset.remove(position);
        notifyDataSetChanged();
    }
    public void updateItem(int position, PojoContact pj){
        mDataset.set(position,pj);
        notifyDataSetChanged();
    }
}
