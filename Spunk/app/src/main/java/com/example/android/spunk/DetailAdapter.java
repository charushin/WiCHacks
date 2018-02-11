package com.example.android.spunk;

/**
 * Created by charu on 2/11/2018.
 */

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by charu on a1/19/2018.
 */

public class DetailAdapter extends BaseAdapter {
    public static final String TAG = DataAdapter.class.getSimpleName();


    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Comments> mDataSource;
    private int mLayout;
    //MediaPlayer mediaPlayer;
    Comments comments;


    public DetailAdapter(Context context, ArrayList<Comments> items, int layout) {
        mContext = context;
        mDataSource = items;
        mLayout = layout;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

//  UN-OPTIMISED IMPLEMENTATION OF getView()
//  /**
//   * Get a View that displays the data at the specified position in the data set. You can either
//   * create a View manually or inflate it from an XML layout file. When the View is inflated, the
//   * parent View (GridView, ListView...) will apply default layout parameters unless you use
//   * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
//   * to specify a root view and to prevent attachment to the root.
//   *
//   * @param position    The position of the item within the adapter's data set of the item whose view
//   *                    we want.
//   * @param convertView The old view to reuse, if possible. Note: You should check that this view
//   *                    is non-null and of an appropriate type before using. If it is not possible to convert
//   *                    this view to display the correct data, this method can create a new view.
//   *                    Heterogeneous lists can specify their number of view types, so that this View is
//   *                    always of the right type (see {@link #getViewTypeCount()} and
//   *                    {@link #getItemViewType(int)}).
//   * @param parent      The parent that this view will eventually be attached to
//   * @return A View corresponding to the data at the specified position.
//   */
//  @Override
//  public View getView(int position, View convertView, ViewGroup parent) {
//
//    // Get view for row item
//    mInflater = (LayoutInflater) mContext
//        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    View rowView = mInflater.inflate(R.layout.list_item_recipe, parent, false);
//
//    // Get relevant subviews of row view
//    TextView titleTextView = (TextView) rowView.findViewById(com.charushi.alltherecipes.R.id.recipe_list_title);
//    TextView subtitleTextView = (TextView) rowView.findViewById(com.charushi.alltherecipes.R.id.recipe_list_subtitle);
//    TextView detailTextView = (TextView) rowView.findViewById(com.charushi.alltherecipes.R.id
//        .recipe_list_detail);
//    ImageView thumbnailImageView = (ImageView) rowView.findViewById(com.charushi.alltherecipes.R.id.recipe_list_thumbnail);
//
//    //Get corresponding recipe for row
//    Recipe recipe = (Recipe) getItem(position);
//
//    // Update row view's textviews to display recipe information
//    titleTextView.setText(recipe.title);
//    subtitleTextView.setText(recipe.description);
//    detailTextView.setText(recipe.label);
//
//    // Use Picasso to load the image. Temporarily have a placeholder in case it's slow to load
//    Picasso.with(mContext).load(recipe.imageUrl).placeholder(R.mipmap
//        .ic_launcher).into(thumbnailImageView);
//
//    // Style text views
//    Typeface titleTypeFace = Typeface.createFromAsset(mContext.getAssets(),
//        "fonts/JosefinSans-Bold.ttf");
//    titleTextView.setTypeface(titleTypeFace);
//    Typeface subtitleTypeFace = Typeface.createFromAsset(mContext.getAssets(),
//        "fonts/JosefinSans-SemiBoldItalic.ttf");
//    subtitleTextView.setTypeface(subtitleTypeFace);
//    Typeface detailTypeFace = Typeface.createFromAsset(mContext.getAssets(),
//        "fonts/Quicksand-Bold.otf");
//    detailTextView.setTypeface(detailTypeFace);
//    detailTextView.setTextColor(android.support.v4.content.ContextCompat.getColor(mContext, LABEL_COLORS
//        .get(recipe.label)));
//
//    return rowView;
//  }
//}

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DetailAdapter.ViewHolder holder;

        // check if the view already exists if so, no need to inflate and findViewById again!
        if (convertView == null) {

            // Inflate the custom row layout from your XML.
            //convertView = mInflater.inflate(R.layout.list_detal_view, parent, false);
            convertView = mInflater.inflate(mLayout, parent, false);

            // create a new "Holder" with subviews
            holder = new DetailAdapter.ViewHolder();
           //7 holder.titleView = (TextView) convertView.findViewById(R.id.title);
            holder.descriptionView = (TextView) convertView.findViewById(R.id.description);
            //holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.recipe_list_thumbnail);

           /* holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.recipe_list_thumbnail);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.recipe_list_title);
            holder.subtitleTextView = (TextView) convertView.findViewById(R.id.recipe_list_subtitle);
            holder.detailTextView = (TextView) convertView.findViewById(R.id.recipe_list_detail);*/

            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        }
        else {

            // skip all the expensive inflation/findViewById and just get the holder you already made
            holder = (DetailAdapter.ViewHolder) convertView.getTag();
        }

        // Get relevant subviews of row view
        //TextView titleTextView = holder.titleView;
        TextView descriptionTextView = holder.descriptionView;
        //ImageView thumbnailImageView = holder.thumbnailImageView;
        /*TextView detailTextView = holder.detailTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;*/

        //Get corresponding recipe for row
        comments = (Comments) getItem(position);

        // Update row view's textviews to display recipe information
        //titleTextView.setText(comments.getTitle());

        //change it to karen
        descriptionTextView.setText(comments.getDescription());
        String selectedLocale= Locale.getDefault().toString();
        /*if(selectedLocale.equals("en")){
            karenTextView.setVisibility(View.GONE);
        }else{
            karenTextView.setVisibility(View.VISIBLE);
        }

        if(holder.thumbnailImageView!=null) {

            //ADDING AUDIO FEATURE ON THE IMAGE VIEW
            holder.thumbnailImageView.setOnClickListener(voiceButtonClickListener);
        }*/

        // detailTextView.setText(healthAppData.label);

        // Use Picasso to load the image. Temporarily have a placeholder in case it's slow to load
        /*Picasso.with(mContext).load(healthAppData.imageUrl).placeholder(R.mipmap
                .ic_launcher).into(thumbnailImageView);*/

        // Style text views
        //change the font for list view
    /*    Typeface titleTypeFace = Typeface.createFromAsset(mContext.getAssets(),
                "fonts/JosefinSans-Bold.ttf");
        englishTextView.setTypeface(titleTypeFace);
        Typeface subtitleTypeFace = Typeface.createFromAsset(mContext.getAssets(),
                "fonts/JosefinSans-SemiBoldItalic.ttf");
        karenTextView.setTypeface(subtitleTypeFace);*/
        /*Typeface detailTypeFace = Typeface.createFromAsset(mContext.getAssets(),
                "fonts/Quicksand-Bold.otf");
        detailTextView.setTypeface(detailTypeFace);
        //detailTextView.setTextColor(android.support.v4.content.ContextCompat.getColor(mContext, LABEL_COLORS
          //      .get(healthAppData.label)));*/

        return convertView;
    }

    /*private View.OnClickListener voiceButtonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mediaPlayer == null) {
                View parentRow = (View) v.getParent();
                ListView listView = (ListView) parentRow.getParent();
                final int position = listView.getPositionForView(parentRow);
                //System.out.println(position);
                //HealthAppData healthAppData = finalContents.get(position);
                healthAppData=(PostEntity) getItem(position);
                int id = mContext.getResources().getIdentifier("a"+String.valueOf(healthAppData._id), "string", mContext.getPackageName());
                System.out.println(id);
                // System.out.println(healthAppData._id+" "+healthAppData.englishText);
                try {
                    Uri mp3 = Uri.parse("android.resource://"
                            + mContext.getPackageName() + "/raw/"
                            + mContext.getString(id));
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(mContext, mp3);
                    // System.out.println("Audio will be played");
                    //System.out.println("Data information");
                    //System.out.println(healthAppData._id+" "+healthAppData.parentId+" "+healthAppData.englishText);
                    System.out.println("The audio file is "+mp3.getPath());

                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    };


    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            // TODO Auto-generated method stub
            mediaPlayer.release();
            mediaPlayer = null;
        }
    };*/

    private static class ViewHolder {
        //public TextView titleView;
        public TextView descriptionView;
        //public ImageView thumbnailImageView;
        /*public TextView detailTextView;
        public ImageView thumbnailImageView;*/
    }

}

