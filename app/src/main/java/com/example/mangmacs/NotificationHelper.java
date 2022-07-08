package com.example.mangmacs;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mangmacs.activities.AccountActivity;
import com.example.mangmacs.activities.CurrentOrderDetailsActivity;
import com.example.mangmacs.activities.MenuActivty;
import com.example.mangmacs.activities.MyOrdersActivity;
import com.example.mangmacs.activities.MyReservationActivity;
import com.example.mangmacs.activities.PreviousOrderDetailsActivity;
import com.example.mangmacs.activities.PromoActivity;
import com.example.mangmacs.activities.ReservationActivity;
import com.example.mangmacs.activities.home_activity;
import com.example.mangmacs.activities.sign_up_activity;
import com.example.mangmacs.fragment.CurrentOrders;
import com.example.mangmacs.fragment.CurrentReservation;
import com.example.mangmacs.fragment.PreviousOrders;

public class NotificationHelper {
   public static void displayNotification(Context context, String title, String body){
       Intent intent = new Intent(context, AccountActivity.class);
        if (title.equals("Order Received") || title.equals("Order Processing")){
            intent = new Intent(context, MyOrdersActivity.class);
        }
        else if (title.equals("Out for Delivery")){
            intent = new Intent(context, MyOrdersActivity.class);
        }
        else if (title.equals("Ready for Pick Up")){
            intent = new Intent(context, MyOrdersActivity.class);
        }
        else if(title.equals("Order Completed")){
            intent = new Intent(context, MyOrdersActivity.class);
        }
        else if (title.equals("Approve")){
            intent = new Intent(context, MyReservationActivity.class);
        }
        else if (title.equals("Not Available")){
            intent = new Intent(context,MyOrdersActivity.class);
        }
        else{
            intent = new Intent(context, MyOrdersActivity.class);
        }

       PendingIntent pendingIntent = PendingIntent.getActivity(context,100,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, sign_up_activity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }
}
