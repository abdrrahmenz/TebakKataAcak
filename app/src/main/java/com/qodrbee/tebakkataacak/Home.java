package com.qodrbee.tebakkataacak;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Process;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import info.hoang8f.widget.FButton;

public class Home extends AppCompatActivity implements View.OnClickListener {

    public static final String COM_QODRBEE_TEBAKKATA = "com.qodrbee.tebakkata";
    SharedPreferences sh_Pref, isLastNull;
    SharedPreferences.Editor toEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLastNull = getSharedPreferences("By Level Now", 0);
        String commit1stLevel = isLastNull.getString("TheLastLevel", null);
        if (commit1stLevel == null) {
            spLastLevel("0");
        }

        FButton goNew= (FButton) findViewById(R.id.goNew);
        FButton goCara= (FButton) findViewById(R.id.goCara);
        FButton goAbout= (FButton) findViewById(R.id.goAbout);
        FButton goLogout= (FButton) findViewById(R.id.goLogout);
        goNew.setOnClickListener(this);
        goCara.setOnClickListener(this);
        goAbout.setOnClickListener(this);
        goLogout.setOnClickListener(this);
        setCustomFont(this, goNew);
        setCustomFont(this, goCara);
        setCustomFont(this, goAbout);
        setCustomFont(this, goLogout);
        TextView tvFooter = (TextView) findViewById(R.id.tvFooter);

        Typeface dimbo = Typeface.createFromAsset(getAssets(), "fonts/Dimbo Regular.ttf");
        tvFooter.setTypeface(dimbo);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.goNew:
                goTo(DaftarLevel.class);
                break;
            case R.id.goCara:
                Snackbar.make(view," Rating ",Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.goAbout:
                break;
            case R.id.goLogout:
                break;
        }
    }

    public static void setCustomFont(Context context, FButton fButton){
        Typeface dimbo = Typeface.createFromAsset(context.getAssets(), "fonts/Dimbo Regular.ttf");
        fButton.setTypeface(dimbo);
    }

    @Override
    public void onBackPressed() {
        popUpExit();
    }

    public void goTo(Class intent){
        Intent in=new Intent(Home.this,intent);
        startActivity(in);
    }

    private void spLastLevel(String lastLevel) {
        sh_Pref = getSharedPreferences("By Level Now", MODE_PRIVATE);
        toEdit = sh_Pref.edit();
        toEdit.putString("TheLastLevel", lastLevel);
        toEdit.commit();
    }

    private void popUpExit() {
//        requestInterstitialAds();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.warning)
                .setMessage(getString(R.string.message_exit)+" "+getString(R.string.app_name)+" ?")
                .setCancelable(false)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                Process.killProcess(Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
//                        mInterstitialAd.show();
                    }
                });

        alertDialogBuilder.show();

    }
}
