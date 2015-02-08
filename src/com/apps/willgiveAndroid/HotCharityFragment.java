package com.apps.willgiveAndroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HotCharityFragment extends Fragment {

    private String tab;
    private int color;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
       
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_tab, container,false);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        ImageView iv = (ImageView) view.findViewById(R.id.tab_icon);
        
        
        iv.setImageResource(R.drawable.ic_action_call);
        tv.setText("abcdefg");
        view.setBackgroundResource(color);
        return view;
    }
}
