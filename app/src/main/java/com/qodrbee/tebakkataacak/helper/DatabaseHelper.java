package com.qodrbee.tebakkataacak.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by adul on 30/08/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME= "database.db";
    public static final String TABLE_NAME="tb_characters";
    static Context ctx;

    private static final String DB_PATH_SUFFIX = "/databases/";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        ctx = context;
    }

    public void CopyDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_NAME);

        String outFileName = getDatabasePath();

        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    private static String getDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null,
                SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, ASK_TYPE TEXT, ASK TEXT, ANSWER TEXT," +
                " POINT TEXT,ANSWERED INTEGER,LOCKED INTEGER, LEVEL INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor updateNewQuiz(int id, String locked){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ons=db.rawQuery("UPDATE " + TABLE_NAME+" SET LOCKED='"+locked+"' WHERE ID='"+id+"'", null);
        return ons;
    }

    public Cursor updateThisQuiz(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ons=db.rawQuery("UPDATE " + TABLE_NAME+" SET ANSWERED='1' WHERE ID='"+id+"'", null);
        return ons;
    }

    public Cursor showCharByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ons=db.rawQuery("select * from " + TABLE_NAME+" where ID="+id, null);
        return ons;
    }

    public String[][] getFromArrayData(int levelID) {

        try {
            String arrData[][] = null;
            SQLiteDatabase db;
            db = this.getReadableDatabase();
            String strSQL = "SELECT  * FROM " + TABLE_NAME+" where LEVEL="+levelID;
            Cursor cursor = db.rawQuery(strSQL, null);
            if(cursor != null)
            {
                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getCount()][cursor.getColumnCount()];
                    int i= 0;
                    do {
                        arrData[i][0] = cursor.getString(0);
                        arrData[i][1] = cursor.getString(1);
                        arrData[i][2] = cursor.getString(2);
                        arrData[i][3] = cursor.getString(3);
                        arrData[i][4] = cursor.getString(4);
                        arrData[i][5] = cursor.getString(5);
                        arrData[i][6] = cursor.getString(6);
                        arrData[i][7] = cursor.getString(7);
                        i++;
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            return arrData;
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor SelectAllData(int byLevel) {

        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase();

            String strSQL = "SELECT  ID As _id , * FROM " + TABLE_NAME+" where LEVEL="+byLevel;
            Cursor cursor = db.rawQuery(strSQL, null);

            return cursor;

        } catch (Exception e) {
            return null;
        }

    }
}
