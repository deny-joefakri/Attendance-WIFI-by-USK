package id.ac.unsyiah.android.absen_mobile.Service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import id.ac.unsyiah.android.absen_mobile.Activity.KonfigurasiAbsenActivity;
import id.ac.unsyiah.android.absen_mobile.Activity.ScanActivity;
import id.ac.unsyiah.android.absen_mobile.AttendanceStore.DatabaseAccess;
import id.ac.unsyiah.android.absen_mobile.R;

public class ExampleService extends Service {
    private int idAbsen, idDb, idRuang;
    private String nip, kdmk;
    private boolean stopAbsen;
    private DatabaseAccess databaseAccess;

    private NotificationManager alarmNotificationManager;
    String NOTIFICATION_CHANNEL_ID = "pus_channel_id";
    String NOTIFICATION_CHANNEL_NAME = "pus channel";
    private int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseAccess = new DatabaseAccess(getApplicationContext());
    }

    //handle notification
    private void sendNotification(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
        String datetimex = sdf.format(new Date());
        String notif_title = "Send Data to Server";
        String notif_content = "Notif Time "+datetimex;
        alarmNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent newIntent = new Intent(context, KonfigurasiAbsenActivity.class);
        newIntent.putExtra("notifkey", "notifvalue");
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //cek jika OS android Oreo atau lebih baru
        //kalau tidak di set maka notifikasi tidak akan muncul di OS tersebut
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
            alarmNotificationManager.createNotificationChannel(mChannel);
        }

        //Buat notification
        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        alamNotificationBuilder.setContentTitle(notif_title);
        alamNotificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        alamNotificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        alamNotificationBuilder.setContentText(notif_content);
        alamNotificationBuilder.setAutoCancel(true);
        alamNotificationBuilder.setContentIntent(contentIntent);
        //Tampilkan notifikasi
//        alarmNotificationManager.notify(NOTIFICATION_ID, alamNotificationBuilder.build());
        startForeground(NOTIFICATION_ID,  alamNotificationBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        idAbsen = (int)(intent.getExtras().get("id_absen"));
        nip = (String) (intent.getExtras().get("nip"));
        idDb = (int) (intent.getExtras().get("id"));
        kdmk = (String) (intent.getExtras().get("kdmk"));
        idRuang = (int) (intent.getExtras().get("idRuang"));
        stopAbsen = (boolean) (intent.getExtras().get("stopService"));
        Log.d("ExampleService", "idAbsen: "+idAbsen+" "+" nip: "+nip+" "+"idSQLite:" +idDb+ "idRuang: "+idRuang);

        // panggil method untuk mengirim notifikasi.
        sendNotification(getApplicationContext());

        Log.d("ExampleService", "Masuk ke Service");
        Log.d("ExampleService", "KirimidAbsen: "+idAbsen+" "+"Kirimnip: "+nip+" "+"KirimidSQLite:" +idDb);

        //intent ke ScanActivity untuk pemindaian Bluetooth.
        intent = new Intent(ExampleService.this, ScanActivity.class);
        intent.putExtra("id_absen", idAbsen); //kirim ID absen
        intent.putExtra("nip", nip); //kirim NIP dose
        intent.putExtra("id", idDb); //kirim ID absen dari SQLite.
        intent.putExtra("idRuang", idRuang); //kirim ID ruang dari SQLite
        intent.putExtra("stopService", stopAbsen);
        intent.putExtra("kdmk", kdmk);
        PendingIntent pendingIntent = PendingIntent.getActivity(ExampleService.this, 2, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);

        if(stopAbsen){
            // berhentikan foreground service.
            stopForeground(true);
            stopSelf();
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("EXAMPLE_SERVICE", "onDestroy");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}

