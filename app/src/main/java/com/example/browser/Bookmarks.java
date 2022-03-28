package com.example.browser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

import java.util.ArrayList;
import java.util.List;

public class Bookmarks extends AppCompatActivity {
    BroadcastReceiver broadcastReceiver=null;
    dbhelper_book dbhelperBook=new dbhelper_book(this,null,null,1);
    WebView mywebView;
    public static final String KEY_NAME="Name";
    public static final String KEY_NAM="Nae";
    SearchView searchView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        searchView=findViewById(R.id.searchdump);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkInternet broadcastReceiver=new checkInternet();
        final List<String> books=dbhelperBook.databasToString();
//       Toast.makeText(Bookmarks.this, "yooo valuesss"+books, Toast.LENGTH_SHORT).show();

        if(books.size()>0){
            ArrayAdapter myadap=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,books);
            ListView myList=(ListView) findViewById(R.id.bookmark_page);
            myList.setAdapter(myadap);

            myList.setOnItemClickListener(
                    new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Toast.makeText(Bookmarks.this, "yooo valuesss"+i+books.get(i), Toast.LENGTH_SHORT).show();
                            String url = dbhelperBook.tourl(books.get(i));
                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            intent.putExtra(KEY_NAME, url);
                            setResult(RESULT_OK,intent);
                            finish();

                        }
                    }
            );

        }
        ArrayAdapter adap=new ArrayAdapter<String>(Bookmarks.this, android.R.layout.simple_list_item_1,books);
        ListView lis=(ListView) findViewById(R.id.bookmark_page);
        //lis.setAdapter(adap);
        lis.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(Bookmarks.this, "yooo valuesss"+(((TextView)view).getText().toString()), Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(Bookmarks.this).setIcon(android.R.drawable.ic_delete).setTitle("Remove this bookmark?").setMessage("Do you want to remove this bookmark?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //update in list as well as db
                        books.remove(((TextView)view).getText().toString());
                        dbhelperBook.deleteURL(dbhelperBook.tourl(((TextView) view).getText().toString()));
                        adap.notifyDataSetChanged();
                        /////to remove it immediately as we press yes
                        overridePendingTransition(0, 0);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                }).setNegativeButton("No",null).show();
                Intent intent = new Intent(Bookmarks.this, MainActivity.class);
                String u="hduv";
                intent.putExtra(KEY_NAM,u);
                setResult(RESULT_OK,intent);
//                setResult(RESULT_CANCELED);
                return true;
            }
        });
        adapter=new ArrayAdapter<String>(Bookmarks.this, android.R.layout.simple_list_item_1,books);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<String> du=dbhelperBook.quer(query);
                ArrayAdapter dinga=new ArrayAdapter<String>(Bookmarks.this, android.R.layout.simple_list_item_1,du);
                Log.i("hughug", "onQueryTextSubmit: "+du);
                lis.setAdapter(dinga);
                lis.setOnItemClickListener(
                        new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Toast.makeText(Bookmarks.this, "yooo valuesss"+i+books.get(i), Toast.LENGTH_SHORT).show();
                                String url = dbhelperBook.tourl(du.get(i));
                                Intent intent = new Intent(view.getContext(), MainActivity.class);
                                intent.putExtra(KEY_NAME, url);
                                setResult(RESULT_OK,intent);
                                finish();

                            }
                        }
                );
                lis.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(Bookmarks.this, "yooo valuesss"+(((TextView)view).getText().toString()), Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(Bookmarks.this).setIcon(android.R.drawable.ic_delete).setTitle("Remove this bookmark?").setMessage("Do you want to remove this bookmark?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //update in list as well as db
                                du.remove(((TextView)view).getText().toString());
                                dbhelperBook.deleteURL(dbhelperBook.tourl(((TextView) view).getText().toString()));
                                dinga.notifyDataSetChanged();
                                /////to remove it immediately as we press yes
                                overridePendingTransition(0, 0);
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                            }
                        }).setNegativeButton("No",null).show();
                        Intent intent = new Intent(Bookmarks.this, MainActivity.class);
                        String u="hduv";
                        intent.putExtra(KEY_NAM,u);
                        setResult(RESULT_OK,intent);
//                setResult(RESULT_CANCELED);
                        return true;
                    }
                });

//                Bookmarks.this.adapter.getFilter().filter(query);
//                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<String> du=dbhelperBook.quer(newText);
                ArrayAdapter dinga=new ArrayAdapter<String>(Bookmarks.this, android.R.layout.simple_list_item_1,du);

                lis.setAdapter(dinga);
                lis.setOnItemClickListener(
                        new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Toast.makeText(Bookmarks.this, "yooo valuesss"+i+books.get(i), Toast.LENGTH_SHORT).show();
                                String url = dbhelperBook.tourl(du.get(i));
                                Intent intent = new Intent(view.getContext(), MainActivity.class);
                                intent.putExtra(KEY_NAME, url);
                                setResult(RESULT_OK,intent);
                                finish();

                            }
                        }
                );
                lis.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(Bookmarks.this, "yooo valuesss"+(((TextView)view).getText().toString()), Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(Bookmarks.this).setIcon(android.R.drawable.ic_delete).setTitle("Remove this bookmark?").setMessage("Do you want to remove this bookmark?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //update in list as well as db
                                du.remove(((TextView)view).getText().toString());
                                dbhelperBook.deleteURL(dbhelperBook.tourl(((TextView) view).getText().toString()));
                                dinga.notifyDataSetChanged();
                                /////to remove it immediately as we press yes
                                overridePendingTransition(0, 0);
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                            }
                        }).setNegativeButton("No",null).show();
                        Intent intent = new Intent(Bookmarks.this, MainActivity.class);
                        String u="hduv";
                        intent.putExtra(KEY_NAM,u);
                        setResult(RESULT_OK,intent);
//                setResult(RESULT_CANCELED);
                        return true;
                    }
                });
//                Bookmarks.this.adapter.getFilter().filter(newText);
//                adapter.notifyDataSetChanged();
                return false;
            }
        });
//        ArrayAdapter adapter=new ArrayAdapter<String>(Bookmarks.this, android.R.layout.simple_list_item_1,books);
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
        switch (item.getItemId()) {
            case android.R.id.home:
//                setResult(RESULT_CANCELED);
                Intent intent = new Intent(Bookmarks.this, MainActivity.class);
                String u="hduv";
                intent.putExtra(KEY_NAM,u);
                setResult(RESULT_OK,intent);
//                Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_search,menu);
        menu.findItem(R.id.clear_book).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
//                Toast.makeText(Bookmarks.this, "Delete clicked", Toast.LENGTH_SHORT).show();
                List<String> du=dbhelperBook.del();
                ListView dff=findViewById(R.id.bookmark_page);
                ArrayAdapter dinga=new ArrayAdapter<String>(Bookmarks.this, android.R.layout.simple_list_item_1,du);
                dff.setAdapter(dinga);
                Intent intent = new Intent(Bookmarks.this, MainActivity.class);
                String u="hduv";
                intent.putExtra(KEY_NAM,u);
                setResult(RESULT_OK,intent);
//                setResult(RESULT_CANCELED);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Bookmarks.this, MainActivity.class);
        String u="hduv";
        intent.putExtra(KEY_NAM,u);
        setResult(RESULT_OK,intent);
        finish();
        super.onBackPressed();
    }
}