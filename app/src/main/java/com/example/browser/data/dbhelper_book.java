package com.example.browser.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.browser.MainActivity;
import com.example.browser.Websites;
import com.example.browser.data.book_contract.book_entry;

import java.util.ArrayList;
import java.util.List;

public class dbhelper_book extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="Bookmarks.db";
    public static final String TABLE_BOOKMARK=book_entry.TABLE_NAME;
    public static final String COLUMN_ID=book_entry._ID;
    public static final String COLUMN_TITLE=book_entry.COLUMN_TITLE;
    public static final String COLUMN_NAME=book_entry.COLUMN_Link;

    public dbhelper_book(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public dbhelper_book(Context context, @Nullable String name,@Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_BOOKMARK_TABLE="CREATE TABLE "+book_entry.TABLE_NAME+"("+
                book_entry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+book_entry.COLUMN_TITLE+" TEXT NOT NULL UNIQUE, " + book_entry.COLUMN_Link+" TEXT NOT NULL UNIQUE" +");";
        db.execSQL(SQL_CREATE_BOOKMARK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String SQL_DROP="DROP TABLE IF EXISTS "+TABLE_BOOKMARK;
        db.execSQL(SQL_DROP);
        onCreate(db);
    }
    //add new row to db
    public boolean addURL(Websites website){
        Log.i("vvv", "addURL: "+website.get_url());
        ContentValues values=new ContentValues();


        values.put(book_entry.COLUMN_TITLE,website.getTitle());
        values.put(book_entry.COLUMN_Link,website.get_url());
        Log.i("v2", ""+values);
        SQLiteDatabase db=getWritableDatabase();
        Log.i("ret", "addURL: "+(!(checkAlreadyExist(website.get_url()))));
        if((checkAlreadyExist(website.get_url()))==false){
            Log.i("iningfg","heh");
        db.insert(TABLE_BOOKMARK,null,values);

        db.close();
        return true;}
        else{
            db.delete(TABLE_BOOKMARK,  COLUMN_NAME+ " = ?", new String[]{website.get_url()});
            db.close();
            return false;
        }
    }
    public boolean checkAlreadyExist(String email)
    {
        Log.i("check", "checkAlreadyExist: "+email);
        String query = "SELECT " + COLUMN_NAME + " FROM " + TABLE_BOOKMARK + " WHERE " + COLUMN_NAME + " =?";
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
        db.execSQL("DELETE FROM "+TABLE_BOOKMARK+" WHERE "+COLUMN_NAME+"= \""+urlName+"\";");
        db.close();
    }
    public void deleteURL(int id){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_BOOKMARK+" WHERE "+COLUMN_ID+"="+id+";");
    }
    public String tourl(String pos){
        SQLiteDatabase d=getWritableDatabase();
        String query="SELECT "+COLUMN_NAME+" FROM " +TABLE_BOOKMARK+" WHERE "+ COLUMN_TITLE+" =?";
        Cursor c = d.rawQuery(query, new String[]{pos});
        c.moveToFirst();
        d.close();
        return c.getString(0);
    }
    public List<String> databaseToString(){
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM " +TABLE_BOOKMARK;
        List<String> dbstring=new ArrayList<>();
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        int i=0;
        //////ealier used c.moveToNext() due to which first bookmark was creating a problem
        if(c.moveToFirst()){
            do {
                int q=c.getColumnIndex(COLUMN_TITLE);
                int y=c.getColumnIndex("title");
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
        String query="SELECT * FROM " +TABLE_BOOKMARK;
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
                int y=c.getColumnIndex("title");
                if (q>=0){
                    if (c.getString(q) != null) {

                        String bstring="";
                        bstring +=c.getString(y);
                        dbstring.add(bstring);
                    }}
            }while (c.moveToPrevious());
        }
        c.close();
        return dbstring;
    }
    public List<String> quer(String s){
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM " +TABLE_BOOKMARK;
        List<String> dbstring=new ArrayList<>();
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.moveToFirst()){
        do{
            if(c.getPosition()==-1){
                break;
            }
            int q=c.getColumnIndex(COLUMN_TITLE);
            int y=c.getColumnIndex("title");
            if(q>=0){
                if(c.getString(q)!=null && c.getString(q).contains(s)){
                    String bub="";
                    bub+=c.getString(y);
                    dbstring.add(bub);
                }
            }
        }while (c.moveToNext());}
        c.close();
        return dbstring;
    }
    public List<String> del(){
        List<String> de=new ArrayList<>();
//        de.add(" ");
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_BOOKMARK);
        db.close();
        return de;
    }


}
