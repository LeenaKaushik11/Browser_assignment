package com.example.browser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.browser.connection.checkInternet;
import com.example.browser.data.dbhelper_book;
import com.example.browser.data.dbhelper_hist;

import java.util.List;

public class History extends AppCompatActivity {
    BroadcastReceiver broadcastReceiver=null;
    dbhelper_hist dbhelperHist=new dbhelper_hist(this,null,null,1);

    WebView mywebView;
    public static final String KEY_NAME="URL";
    public static final String KEY_NM="Nape";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkInternet broadcastReceiver=new checkInternet();
        final List<String> books=dbhelperHist.databasToString();
        final List<Integer> ids=dbhelperHist.databasid();
//        Toast.makeText(History.this, "yooo valuesss"+books, Toast.LENGTH_SHORT).show();

        if(books.size()>0){

            ArrayAdapter myadap=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,books);
            ListView myList=(ListView) findViewById(R.id.history_page);
            myList.setAdapter(myadap);

            myList.setOnItemClickListener(
                    new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Toast.makeText(Bookmarks.this, "yooo valuesss"+i+books.get(i), Toast.LENGTH_SHORT).show();
                            String url = dbhelperHist.tourl(books.get(i));
                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            intent.putExtra(KEY_NAME, url);
                            setResult(RESULT_OK,intent);
                            finish();

                        }
                    }
            );

        }
        ArrayAdapter adap=new ArrayAdapter<String>(History.this, android.R.layout.simple_list_item_1,books);
        ListView lis=(ListView) findViewById(R.id.history_page);
        //lis.setAdapter(adap);
        lis.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos=i;
                Log.i("bookspos", "onClick:out "+pos+books.get(pos)+"  "+books+"   "+ids.get(pos)+ids);

//                Toast.makeText(Bookmarks.this, "yooo valuesss"+(((TextView)view).getText().toString()), Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(History.this).setIcon(android.R.drawable.ic_delete).setTitle("Delete this?").setMessage("Do you want to delete this?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //update in list as well as db
//                        books.remove(((TextView)view).getText().toString());
                        dbhelperHist.deleteURL(ids.get(pos));

//                        Log.i("bookspos", "onClick: "+pos+books.get(pos));
//                        books.remove(pos);
//                        ids.remove(pos);
//                        Log.i("bookspos", "onClick: "+pos+books.get(pos));
                        adap.notifyDataSetChanged();
                        /////to remove it immediately as we press yes
                        overridePendingTransition(0, 0);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                }).setNegativeButton("No",null).show();
//                Intent intent = new Intent(History.this, MainActivity.class);
//                String u="hduv";
//                intent.putExtra(KEY_NM,u);
//                setResult(RESULT_OK,intent);
//                setResult(RESULT_CANCELED);
                return true;
            }
        });

        //ArrayAdapter adapter=new ArrayAdapter<String>(Bookmarks.this, android.R.layout.simple_list_item_1,books);
    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new
                IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
//        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
//        unregisterReceiver(broadcastReceiver);
    }


    @Override
    protected void onResume() {
        Log.i("huhuhu", "onResume: ");
        super.onResume();
        IntentFilter intentFilter = new
                IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver,intentFilter);
    }
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
//        unregisterReceiver(broadcastReceiver);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("whatthe", "onOptionsItemSelected: ");
        if (item.getItemId() == android.R.id.home) {
            Log.i("whatthe", "onOptionsItemSelected:innnn ");
            Intent intent = new Intent(History.this, MainActivity.class);
            String u = "hduv";
            intent.putExtra(KEY_NM, u);
            setResult(RESULT_OK, intent);
//                setResult(RESULT_CANCELED);
//                Intent i = new Intent(this, MainActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                setResult(RESULT_OK,i);
//                Intent intent = new Intent(History.this, MainActivity.class);
//                String u="hduv";
//                intent.putExtra(KEY_NM,u);
//                setResult(RESULT_CANCELED,intent);
//                Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
        }
        finish();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_del,menu);
        menu.findItem(R.id.clear_hist).setTitle("Clear History");
        menu.findItem(R.id.clear_hist).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
//                Toast.makeText(History.this, "Delete clicked", Toast.LENGTH_SHORT).show();
                List<String> du=dbhelperHist.del();
                ListView dff=findViewById(R.id.history_page);
                ArrayAdapter dinga=new ArrayAdapter<String>(History.this, android.R.layout.simple_list_item_1,du);
                dff.setAdapter(dinga);
                Log.i("whatthehh", "onMenuItemClick: ");
                Intent intent = new Intent(History.this, MainActivity.class);
                String u="hduv";
                intent.putExtra(KEY_NM,u);
                setResult(RESULT_OK,intent);
//                setResult(RESULT_CANCELED);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(History.this, MainActivity.class);
        String u = "hduv";
        intent.putExtra(KEY_NM, u);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }
}