package com.example.android.spunk;

/**
 * Created by charushi on 2/10/18.
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

public class TabFragment4 extends Fragment {
//    Integer[] imageIDs = {
//            R.drawable.srm1,
//            R.drawable.srm2,
//            R.drawable.srm3,
//            R.drawable.srm4,
//            R.drawable.srm5,
//            R.drawable.srm6,
//    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ImageView imageView=(ImageView) rootView.findViewById(R.id.fragment_body_part);
        // Inflate the layout for this fragment
        //imageView.setImageResource(R.drawable.srm1);
        //return inflater.inflate(R.layout.tab_fragment_4, container, false);

        View rootView =inflater.inflate(R.layout.tab_fragment_4, container, false);
//        @SuppressLint("ResourceType") ImageView imageView=(ImageView) rootView.findViewById(R.layout.tab_fragment_4);
//        // Inflate the layout for this fragment
//        imageView.setImageResource(R.drawable.srm6);
        return rootView;

    }
}
