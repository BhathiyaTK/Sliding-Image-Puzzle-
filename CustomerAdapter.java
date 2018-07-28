package com.example.bhathiyatk.puzzle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private ArrayList<Button> mButtons;
    private int mColumnWidth, mColumnHeight;

    public CustomAdapter(ArrayList<Button> buttons, int columnWith, int columnHeight) {
        mButtons = buttons;
        mColumnWidth = columnWith;
        mColumnHeight = columnHeight;
    }

    @Override
    public int getCount() {
        return mButtons.size();
    }

    @Override
    public Object getItem(int position) {
        return (Object) mButtons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null){
            button = mButtons.get(position);
        }else {
            button = (Button)convertView;
        }

        android.widget.AbsListView.LayoutParams params = new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        button.setLayoutParams(params);

        return button;
    }
}