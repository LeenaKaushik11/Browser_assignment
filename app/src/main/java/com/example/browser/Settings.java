package com.example.browser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    private SwitchCompat switchCompat;
    private SwitchCompat swi2;
    public static final String SWITCH1="javas";
    public static final String SWITCH2="switch2";
    public static boolean s1;
    public static boolean s2;
    public static boolean c=false;
    public static boolean c1=false;
    public static final String KE="kolaweriiiiikkNae";
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        c=false;
        c1=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switchCompat=(SwitchCompat)findViewById(R.id.switch1);
        swi2=(SwitchCompat)findViewById(R.id.switch2);
        back=(ImageButton)findViewById(R.id.settingback);
        Log.i("inreci", "in seyyiggfgd ocreate c, c1 "+c+" "+c1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("hereidie", "onActivityResult:in seyyiggfgd ");
                Intent intent = new Intent(Settings.this, MainActivity.class);
                String u="hduv";
                intent.putExtra(KE,u);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        //linear layout click listner
        LinearLayout search=(LinearLayout)findViewById(R.id.settingspage);
        search.findViewById(R.id.enablejs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("inreci", "search js onclick "+c+" "+c1);
                c=true;
//                Toast.makeText(Settings.this, "Enable JS", Toast.LENGTH_SHORT).show();
                switchCompat.toggle();
                savedata();
//                loaddata();
            }
        });
        search.findViewById(R.id.allowcookie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c1=true;
                Log.i("inreci", "search allowcook "+c+" "+c1);
                swi2.toggle();
                savedata();
//                loaddata();
            }
        });
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c=true;
                Log.i("inreci", "switch1 savedata "+c+" "+c1);
                savedata();
//                loaddata();
            }
        });
        swi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c1=true;
                Log.i("inreci", "switch2 savedata"+c+" "+c1);
                savedata();
//                loaddata();
            }
        });

//        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                c=true;
//                Log.i("inreci", "setonchange sw1"+c+" "+c1);
//
//            }
//        });
//        swi2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                c1=true;
//                Log.i("inreci", "setoncheck sw2 "+c+" "+c1);
//
//            }
//        });
//        savedata();
        loaddata();
        updateView();
    }
    public void savedata() {
        Log.i("inreci", "savedata: start");
        SharedPreferences sharedPref = getSharedPreferences("A", Context.MODE_PRIVATE); // sharedpreference set named "A"
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(SWITCH1, switchCompat.isChecked());
        editor.putBoolean(SWITCH2, swi2.isChecked());
        editor.apply();
        Log.i("inreci", "savedata: "+sharedPref.getBoolean(SWITCH1,true));
//        SharedPreferences sharedPref1 = getSharedPreferences("B", Context.MODE_PRIVATE); // sharedpreference set named "A"

//        SharedPreferences.Editor editor1 = sharedPref1.edit();
//        editor1.clear();
//        editor1.putBoolean(SWITCH2, swi2.isChecked());
//        editor1.apply();}
    }
    public void loaddata(){
        SharedPreferences sharedPref = getSharedPreferences("A", Context.MODE_PRIVATE);
        s1=sharedPref.getBoolean(SWITCH1,false);
//        SharedPreferences sharedPref1 = getSharedPreferences("B", Context.MODE_PRIVATE);
        s2=sharedPref.getBoolean(SWITCH2,false);
        Log.i("inreci", "loaddata: "+s1+"  "+s2);
    }
    public void updateView(){
        switchCompat.setChecked(s1);
        swi2.setChecked(s2);
        Log.i("inreci", "updateView: ");
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.i("whatthe", "onOptionsItemSelected: ");
//        if (item.getItemId() == android.R.id.home) {
//            Log.i("whatthe", "onOptionsItemSelected:innnn ");
//            Intent intent = new Intent(Settings.this, MainActivity.class);
//            String u = "hduv";
//            intent.putExtra(KE, u);
//            setResult(RESULT_OK, intent);
////                setResult(RESULT_CANCELED);
////                Intent i = new Intent(this, MainActivity.class);
////                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
////                setResult(RESULT_OK,i);
////                Intent intent = new Intent(History.this, MainActivity.class);
////                String u="hduv";
////                intent.putExtra(KEY_NM,u);
////                setResult(RESULT_CANCELED,intent);
////                Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
//        }
//        finish();
//        return true;
//    }

    @Override
    public void onBackPressed() {
        Log.i("settingggg", "onBackPressed: ");
        Log.i("hereidie", "onActivityResult: settin backmettgod ");
//        super.onBackPressed();
        Intent intent = new Intent(Settings.this, MainActivity.class);
        String u="hduv";
        intent.putExtra(KE,u);
        setResult(RESULT_OK,intent);
        Log.i("inreci", "onBackPressed: here i die c,c1 "+c+" "+c1);
        finish();
    }
}