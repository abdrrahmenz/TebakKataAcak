package com.qodrbee.tebakkataacak;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.InterstitialAd;
import com.qodrbee.tebakkataacak.helper.DatabaseHelper;

import info.hoang8f.widget.FButton;

public class DaftarLevel extends AppCompatActivity implements View.OnClickListener{

    private FButton btnLevel1, btnLevel2, btnLevel3, btnLevel4, btnLevel5;
    private ImageView ivLockLv2, ivLockLv3, ivLockLv4, ivLockLv5;
    private ImageView ivNewLv2, ivNewLv3, ivNewLv4, ivNewLv5;
    InterstitialAd interAd;
    DatabaseHelper myDb;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);
        myDb.openDataBase();
        setContentView(R.layout.activity_daftar_level);

        ivLockLv2 = (ImageView) findViewById(R.id.newlvl2);
        ivLockLv3 = (ImageView) findViewById(R.id.newlvl3);
        ivLockLv4 = (ImageView) findViewById(R.id.newlvl4);
        ivLockLv5 = (ImageView) findViewById(R.id.newlvl5);
        ivNewLv2 = (ImageView) findViewById(R.id.newlvel2);
        ivNewLv3 = (ImageView) findViewById(R.id.newlvel3);
        ivNewLv4 = (ImageView) findViewById(R.id.newlvel4);
        ivNewLv5 = (ImageView) findViewById(R.id.newlvel5);
        btnLevel1 = (FButton) findViewById(R.id.lvl1);
        btnLevel2 = (FButton) findViewById(R.id.lvl2);
        btnLevel3 = (FButton) findViewById(R.id.lvl3);
        btnLevel4 = (FButton) findViewById(R.id.lvl4);
        btnLevel5 = (FButton) findViewById(R.id.lvl5);
        btnLevel1.setOnClickListener(this);
        btnLevel2.setOnClickListener(this);
        btnLevel3.setOnClickListener(this);
        btnLevel4.setOnClickListener(this);
        btnLevel5.setOnClickListener(this);

        Home.setCustomFont(this, btnLevel1);
        Home.setCustomFont(this, btnLevel2);
        Home.setCustomFont(this, btnLevel3);
        Home.setCustomFont(this, btnLevel4);
        Home.setCustomFont(this, btnLevel5);

        SharedPreferences mysettings = getSharedPreferences("By Level Now", 0);
        String lastlevel = mysettings.getString("TheLastLevel", null);
        Log.d(TAG, "onCreate: last level = "+lastlevel);

        if (lastlevel != null) {
            int checkLevelparse = Integer.parseInt(lastlevel);
            Log.d(TAG, "onCreate: last level = "+checkLevelparse);

            ivLockLv2.setVisibility(View.VISIBLE);
            ivLockLv3.setVisibility(View.VISIBLE);
            ivLockLv4.setVisibility(View.VISIBLE);
            ivLockLv5.setVisibility(View.VISIBLE);


            if (checkLevelparse >= 15) {
                btnLevel2.setText("Level 2");
                btnLevel2.setEnabled(true);
                ivLockLv2.setVisibility(View.INVISIBLE);
            } else {
                btnLevel2.setText("TERKUNCI");
                btnLevel2.setEnabled(false);
            }
            if (checkLevelparse >= 30) {
                if (checkLevelparse == 30){
                    btnLevel3.setText("Level 3");
                    btnLevel3.setEnabled(true);
                    ivLockLv3.setVisibility(View.INVISIBLE);
                    ivNewLv3.setVisibility(View.VISIBLE);
                }else {
                    btnLevel3.setText("Level 3");
                    btnLevel3.setEnabled(true);
                    ivLockLv3.setVisibility(View.INVISIBLE);
                }
            } else {
                btnLevel3.setText("TERKUNCI");
                btnLevel3.setEnabled(false);
            }
            if (checkLevelparse >= 45) {
                btnLevel4.setText("Level 4");
                btnLevel4.setEnabled(true);
                ivLockLv4.setVisibility(View.INVISIBLE);
            } else {
                btnLevel4.setText("TERKUNCI");
                btnLevel4.setEnabled(false);
            }
            if (checkLevelparse >= 60) {
                btnLevel5.setText("Level 5");
                btnLevel5.setEnabled(true);
                ivLockLv5.setVisibility(View.INVISIBLE);
            } else {
                btnLevel5.setText("TERKUNCI");
                btnLevel5.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lvl1:
                goTo(Questions.class,"1","''Jajanan Pasar Tradisional''","#298b6c");
                break;
            case R.id.lvl2:
                goTo(Questions.class,"2","''Jajanan Pasar Tradisional''","#2980b9");
                break;
            case R.id.lvl3:
                goTo(Questions.class,"3","''Nama-nama Hewan Tradisional''","#27ae60");
                break;
            case R.id.lvl4:
                goTo(Questions.class,"4","''Nama-nama Hewan Tradisional''","#f39c12");
                break;
            case R.id.lvl5:
                goTo(Questions.class,"5","''Nama-nama Hewan Tradisional''","#e67e22");
                break;
        }
    }

    public void goTo(Class intent, String level, String judul, String bg_color){
        Intent in=new Intent(DaftarLevel.this,intent);
        spLevelNow(level,judul,bg_color);
        startActivity(in);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public void spLevelNow(String level_now,String judul_now, String bg_color) {
        SharedPreferences sh_Pref = getSharedPreferences("Set Level Detail", MODE_PRIVATE);
        SharedPreferences.Editor toEdit = sh_Pref.edit();
        toEdit.putString("Levelnow", level_now);
        toEdit.putString("judulNow", judul_now);
        toEdit.putString("Bgcolor", bg_color);
        toEdit.commit();
    }
}
