package com.example.myapplication1;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication1.db.MyDBManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity2 extends AppCompatActivity {
    public static ArrayList<PlanToSub> ArrPlan;
    public static MyDBManager myDBManager;
    public static int NotiHours;
    public static int NotiMinuts;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        myDBManager = new MyDBManager(this);
        ArrPlan=new ArrayList<PlanToSub>(myDBManager.set());
     /*  for(int i=0;i<ArrPlan.size();i++){
            ArrPlan.get(i).open_prog();
        }*/

        if(!myDBManager.time_bool()){
            myDBManager.insert_TABLE_TIME(20,0,1);
            NotiHours=myDBManager.getMin();
            NotiMinuts=myDBManager.getHours();
        }

        //пока убрала
        //startAlarm();


        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }
    @Override
    protected void onResume() {
        super.onResume();
        myDBManager.openDB();
    }

    private void startAlarm() {
        if(myDBManager.getNoti()==1) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, NotiHours);
            calendar.set(Calendar.MINUTE, NotiMinuts);
            calendar.set(Calendar.SECOND, 0);

            AlarmManager am1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getApplicationContext(), TimeNotification.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            am1.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }

}