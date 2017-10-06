package com.qodrbee.tebakkataacak;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.qodrbee.tebakkataacak.adapter.CustomCursorAdapter;
import com.qodrbee.tebakkataacak.helper.DatabaseHelper;
import com.qodrbee.tebakkataacak.helper.TypefaceUtil;

public class Questions extends AppCompatActivity {
    String arrData[][];
    DatabaseHelper myDb;
    CustomCursorAdapter cursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Dimbo Regular.ttf");
        setContentView(R.layout.activity_question);

        TextView judul= (TextView) findViewById(R.id.judulLevel);
        TextView levelID= (TextView) findViewById(R.id.levelID);

        myDb = new DatabaseHelper(this);
        myDb.openDataBase();
        final GridView gridView = (GridView)findViewById(R.id.gridView);

        SharedPreferences mysettings = getSharedPreferences("By Level Now", 0);
        String lastlevel = mysettings.getString("TheLastLevel", null);

        if (lastlevel != null) {
            int checkLevelparse = Integer.parseInt(lastlevel);
            if(checkLevelparse%5==0){
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }

        SharedPreferences enterLevel = getSharedPreferences("Set Level Detail", 0);
        String levelLive = enterLevel.getString("Levelnow", null);
        String judulLive = enterLevel.getString("judulNow", null);
        String bgcolor = enterLevel.getString("Bgcolor", null);
        String level = null;
        if (levelLive != null) {
            level = levelLive;
            levelID.setText("Level " + level);
            judul.setText(judulLive);
//            levelID.setBackgroundColor(Color.parseColor(bgcolor));
        }
        final Cursor myData = myDb.SelectAllData(Integer.parseInt(level));
        startManagingCursor(myData);
        arrData = myDb.getFromArrayData(Integer.parseInt(level));
        final String[] cols = new String[]{"_id"};
        int[] names = new int[]{
                R.id.textView1
        };

        final CoordinatorLayout viewSnackbar = (CoordinatorLayout) findViewById(R.id.viewSnackbar);
        cursorAdapter = new CustomCursorAdapter(this, R.layout.showinfo , myData ,cols,names);
        gridView.setAdapter(cursorAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int isfinish = Integer.parseInt(arrData[position][6].toString());
                if (isfinish == 1) {
                    Intent ont = new Intent(Questions.this, DetailQuestions.class);
                    ont.putExtra("id", arrData[position][0].toString());
                    ont.putExtra("ask_type", arrData[position][1].toString());
                    ont.putExtra("ask", arrData[position][2].toString());
                    ont.putExtra("answer", arrData[position][3].toString());
                    ont.putExtra("point", arrData[position][4].toString());
                    ont.putExtra("answered", arrData[position][5].toString());
                    ont.putExtra("locked", arrData[position][6].toString());
                    startActivity(ont);
                    finish();
                } else {
//                    Toast.makeText(getApplicationContext(), R.string.message_done_first, Toast.LENGTH_SHORT).show();
                    Snackbar.make(viewSnackbar, getString(R.string.message_done_first),Snackbar.LENGTH_LONG).show();

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, DaftarLevel.class));
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
