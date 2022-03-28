package com.example.browser.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.browser.Websites;

import java.util.ArrayList;
import java.util.List;

public class dbhelper_hist extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="History.db";
    public static final String TABLE_HIST= hist_contract.hist_entry.TABLE_NAME;
    public static final String COLUMN_ID= hist_contract.hist_entry._ID;
    public static final String COLUMN_NAME= hist_contract.hist_entry.COLUMN_Link;
    public static final String COLUMN_TITLE= hist_contract.hist_entry.COLUMN_TITLE;
    public dbhelper_hist(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_BOOKMARK_TABLE="CREATE TABLE "+ TABLE_HIST+"("+
                COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE+" TEXT NOT NULL, " + COLUMN_NAME+" TEXT NOT NULL " +");";
        db.execSQL(SQL_CREATE_BOOKMARK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String SQL_DROP="DROP TABLE IF EXISTS "+TABLE_HIST;
        db.execSQL(SQL_DROP);
        onCreate(db);
    }
    //add new row to db
    public boolean addURL(Websites website){
        Log.i("vvv", "addURL: "+website.get_url());
        ContentValues values=new ContentValues();
        values.put(COLUMN_TITLE,website.getTitle());
        values.put(COLUMN_NAME,website.get_url());
        Log.i("v2", ""+values);
        SQLiteDatabase db=getWritableDatabase();
//        Log.i("ret", "addURL: "+(!(checkAlreadyExist(website.get_url()))));
//        if((checkAlreadyExist(website.get_url()))==false){
            Log.i("iningfg","heh");
            db.insert(TABLE_HIST,null,values);

            db.close();
            return true;
//        else{
//            db.delete(TABLE_HIST,  COLUMN_NAME+ " = ?", new String[]{website.get_url()});
//            db.close();
//            return false;
//        }
    }
    public int checklast(String tit){
        SQLiteDatabase db=getWritableDatabase();
        String[] projection={COLUMN_ID, COLUMN_TITLE};
        Cursor cursor=db.query(TABLE_HIST,projection,null,null,null,null,null);
        try {

            cursor.moveToLast();
            int col=cursor.getColumnIndex(COLUMN_TITLE);
            int cc=cursor.getColumnIndex(COLUMN_ID);
            if(tit.equals(cursor.getString(col))){
                Log.i("lemmeee", "checklast: "+cursor.getInt(cc));
                return cursor.getInt(cc);
            }
            else {
                return -1;
            }}
        catch (Exception e){
            Log.i("lemmeee", "checklast: ");
            return -7;
        }
    }
    public int checkifsecond(String tit){
        SQLiteDatabase db=getWritableDatabase();
        String[] projection={COLUMN_ID, COLUMN_TITLE};
        Cursor cursor=db.query(TABLE_HIST,projection,null,null,null,null,null);
        try {


        cursor.moveToLast();
        cursor.moveToPrevious();
        int col=cursor.getColumnIndex(COLUMN_TITLE);
        int cc=cursor.getColumnIndex(COLUMN_ID);
        if(tit.equals(cursor.getString(col))){

            return cursor.getInt(cc);
        }
        else {
            return -7;
        }}
        catch (Exception e){
            return -7;
        }
    }
    public  int toid(String titt){
        SQLiteDatabase db=getWritableDatabase();
        String[] projection={COLUMN_ID, COLUMN_TITLE};
        String select=COLUMN_TITLE+ "=?";
        String selarg=titt;
        Cursor cursor=db.query(TABLE_HIST,projection,select, new String[]{selarg},null,null,null);
        int cc=cursor.getColumnIndex(COLUMN_ID);
//        cursor.moveToLast();

            return cursor.getInt(cc);
    }
    public boolean checkAlreadyExist(String email)
    {
        Log.i("check", "checkAlreadyExist: "+email);
        String query = "SELECT " + COLUMN_NAME + " FROM " + TABLE_HIST + " WHERE " + COLUMN_NAME + " =?";
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{email});
        if (cursor.getCount() > 0)
        {
            return true;
        }
        else
            Log.i("elsee", "checkAlreadyExist: nooooooo");
        return false;
    }
    //delete from db
    public void deleteURL(String urlName){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_HIST+" WHERE "+COLUMN_NAME+"= \""+urlName+"\";");
        db.close();
    }
    public void deleteURL(long id){
        SQLiteDatabase db=getWritableDatabase();
        Log.i("bookspos", "deleteURL: "+id);
        db.execSQL("DELETE FROM "+TABLE_HIST+" WHERE "+COLUMN_ID+"= "+id+";");
        db.close();
    }

    public String tourl(String pos){
        SQLiteDatabase d=getWritableDatabase();
        String query="SELECT "+COLUMN_NAME+" FROM " +TABLE_HIST+" WHERE "+ COLUMN_TITLE+" =?";
        Cursor c = d.rawQuery(query, new String[]{pos});
        c.moveToFirst();
        d.close();
        return c.getString(0);
    }
public List<String> databaseToString(){
    SQLiteDatabase db=getWritableDatabase();
    String query="SELECT * FROM " +TABLE_HIST;
    List<String> dbstring=new ArrayList<>();
    Cursor c=db.rawQuery(query,null);
    c.moveToFirst();
    int i=0;
    //////ealier used c.moveToNext() due to which first bookmark was creating a problem
    if(c.moveToFirst()){
        do {
            int q=c.getColumnIndex(COLUMN_TITLE);
            int y=c.getColumnIndex(COLUMN_TITLE);
            if (q>=0){
                if (c.getString(q) != null) {

                    String bstring="";
                    bstring +=c.getString(y);
                    dbstring.add(bstring);
                }}
        }while (c.moveToNext());
    }
    c.close();
    return dbstring;
}

    public List<String> databasToString(){
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM " +TABLE_HIST;
        List<String> dbstring=new ArrayList<>();
        Cursor c=db.rawQuery(query,null);
//        c.moveToFirst();
        c.moveToLast();
        int i=0;
        //////ealier used c.moveToNext() due to which first bookmark was creating a problem
        if(c.moveToLast()){
            do {
                if(c.getPosition()==-1){
                    break;
                }
                int q=c.getColumnIndex(COLUMN_TITLE);
                int y=c.getColumnIndex(COLUMN_TITLE);
                    if (c.getString(q) != null) {

                        String bstring="";
                        bstring +=c.getString(y);
                        dbstring.add(bstring);
                    }
            }while (c.moveToPrevious());
        }
        c.close();
        Log.i("databasToString()", "databasToString: "+dbstring);
        return dbstring;
    }
    public List<Integer> databasid(){
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM " +TABLE_HIST;
        List<Integer> dbstring=new ArrayList<>();
        Cursor c=db.rawQuery(query,null);
//        c.moveToFirst();
        c.moveToLast();
        int i=0;
        //////ealier used c.moveToNext() due to which first bookmark was creating a problem
        if(c.moveToLast()){
            do {
                if(c.getPosition()==-1){
                    break;
                }
                int q=c.getColumnIndex(COLUMN_ID);
                int y=c.getColumnIndex(COLUMN_ID);
                if (c.getString(q) != null) {
                   int bstring =c.getInt(y);
                    dbstring.add(bstring);
                }
            }while (c.moveToPrevious());
        }
        c.close();
        Log.i("databasToString()", "databasToString: "+dbstring);
        return dbstring;
    }
    public List<String> del(){
        List<String> de=new ArrayList<>();
//        de.add(" ");
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_HIST);
        db.close();
        return de;
    }

}
