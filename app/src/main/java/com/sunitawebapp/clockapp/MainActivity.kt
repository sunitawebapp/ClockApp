package com.sunitawebapp.clockapp


import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextClock
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sunitawebapp.clockapp.Constant.CHANNEL_DES
import com.sunitawebapp.clockapp.Constant.CHANNEL_ID
import com.sunitawebapp.clockapp.Constant.CHANNEL_NAME
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
 lateinit var simpleTimePicker :   TimePicker
 lateinit var tvTime : TextView
 lateinit var tvCurrentTime : TextClock
  lateinit var   btnSave : Button
    var currTime=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         simpleTimePicker = findViewById(R.id.simpleTimePicker)// initiate a time picker
        tvTime = findViewById(R.id.tvTime)
        btnSave= findViewById(R.id.btnSave)
        tvCurrentTime= findViewById(R.id.tvCurrentTime)
         currTime=  getCurrentTime()



        btnSave.setOnClickListener {
            sendNotification()
        }

        simpleTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->

            tvTime.setText("$hourOfDay  :  $minute ")
        }
    }
    fun sendNotification(){

        createChannel()
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(androidx.loader.R.drawable.notification_bg)
            .setContentTitle(tvCurrentTime.text.toString())
            .setContentText(tvTime.text.toString())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }
    }

    fun createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = CHANNEL_NAME
            val descriptionText = CHANNEL_DES
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    fun getCurrentTime() : String{
        val calendar = Calendar.getInstance()
       // val mdformat = SimpleDateFormat("HH:mm:ss")
        val mdformat = SimpleDateFormat("HH:mm")
        val strDate = "Current Time : " + mdformat.format(calendar.time)
       return strDate
    }


}