package com.qodrbee.tebakkataacak.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.qodrbee.tebakkataacak.R;

/**
 * Created by adul on 30/08/17.
 */

public class CustomCursorAdapter extends SimpleCursorAdapter{

    public CustomCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.id_quiz = (TextView) view.findViewById(R.id.textView1);
            holder.column1 = cursor.getColumnIndexOrThrow("ID");
            holder.column7 = cursor.getColumnIndexOrThrow("LOCKED");
            holder.imgVw = (ImageView) view.findViewById(R.id.locker);
            view.setTag(holder);
        }

        if (cursor.getString(holder.column7).equals("0")) {
            holder.imgVw.setVisibility(View.VISIBLE);
        }else {
            holder.imgVw.setVisibility(View.GONE);
        }

        int cur = cursor.getPosition()+1;
        holder.id_quiz.setText(String.valueOf(cur));
    }

    static class ViewHolder{
        TextView id_quiz;
        ImageView imgVw;
        int column1, column7;
    }
}
