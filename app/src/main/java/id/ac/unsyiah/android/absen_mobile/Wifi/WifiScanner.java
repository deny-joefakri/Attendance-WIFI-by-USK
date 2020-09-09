package id.ac.unsyiah.android.absen_mobile.Wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;


import java.util.HashMap;
import java.util.List;

import id.ac.unsyiah.android.absen_mobile.Activity.ScanActivity;

import static id.ac.unsyiah.android.absen_mobile.Wifi.ResultModel.WIFI_MAC_1;
import static id.ac.unsyiah.android.absen_mobile.Wifi.ResultModel.WIFI_MAC_2;
import static id.ac.unsyiah.android.absen_mobile.Wifi.ResultModel.WIFI_MAC_3;
import static id.ac.unsyiah.android.absen_mobile.Wifi.ResultModel.WIFI_MAC_4;
import static id.ac.unsyiah.android.absen_mobile.Wifi.ResultModel.WIFI_MAC_5;
import static id.ac.unsyiah.android.absen_mobile.Wifi.ResultModel.WIFI_MAC_6;
import static id.ac.unsyiah.android.absen_mobile.Wifi.ResultModel.WIFI_MAC_7;

/**
 * Merupakan kelas yang berfungsi sebagai pemindai Wi-Fi.
 * Wi-Fi yang dipindai akan diambil data RSSI-nya dan akan digunakan sebagai data tracking atau data online
 * */
public class WifiScanner {
    private Context context;
    private WifiManager wifiManager;
    private HashMap<String, WifiModel> scannedAPs = new HashMap<>();
    private ScanActivity scanActivity;
    private Handler mHandler;

    /* Penentuan MAC Address yang akan dipakai untuk mapping */

    public WifiScanner(ScanActivity absenActivity, Context context){
        this.scanActivity = absenActivity;
        this.context = context;
        mHandler = new Handler();
        if (!scannedAPs.isEmpty()) {
            scannedAPs.clear();
        }
        Log.i("WIFISCANNER", "masuk constructor");
        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        getAvailableWifi();
    }

    /* Memindai jaringan wifi yang tersedia */
    private void getAvailableWifi(){

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(wifiScanReceiver, intentFilter);
        boolean success = wifiManager.startScan();

        Log.i("WIFISCANNER", "getAvailableWifi: success > " +success);
        Log.i("WIFISCANNER", "getAvailableWifi: receiver > " +wifiScanReceiver);
        Log.i("WIFISCANNER", "getAvailableWifi: scan result > " +wifiManager.getScanResults().size());
        if (!success) {
            /* Menangani kegagalan pemindaian */
            scanFailure();
        }
    }


    BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            boolean success = intent.getBooleanExtra(
                    WifiManager.EXTRA_RESULTS_UPDATED, false);
            Log.i("WIFISCANNER", "onReceive: success>"+success);
            if (success) {
                scanSuccess();
            } else {
                /* Menangani kegagalan pemindaian */
                scanFailure();
            }
        }
    };

    private void scanSuccess() {
        List<ScanResult> results = wifiManager.getScanResults();
        dummyData();
//            Log.d(SIZE, "scanSuccess: result: "+results.size());
//            Log.d(SIZE, "scanSuccess: scanned AP "+scannedAPs.size());
        for (int i = 0; i < results.size(); i++) {
//            Log.e("BSSID", results.get(i).BSSID);
//            Log.e("SSID", results.get(i).SSID);
//            Log.e("SignalStrength", results.get(i).level + "");
            WifiModel wifiModel = filterMacAddress(results.get(i));
            if (wifiModel != null) {
                scannedAPs.put(wifiModel.getMacAddress(), wifiModel);
            }
        }
        Log.e("WIFISCANNER ScanResult", "scanSuccess: "+scannedAPs.size());
        scanActivity.stopScan();
    }

    private WifiModel filterMacAddress(ScanResult results) {
        WifiModel wifiModel = new WifiModel();
        if (results.BSSID.equalsIgnoreCase(WIFI_MAC_1) ||
                results.BSSID.equalsIgnoreCase(WIFI_MAC_2) ||
                results.BSSID.equalsIgnoreCase(WIFI_MAC_3) ||
                results.BSSID.equalsIgnoreCase(WIFI_MAC_4) ||
                results.BSSID.equalsIgnoreCase(WIFI_MAC_5) ||
                results.BSSID.equalsIgnoreCase(WIFI_MAC_6) ||
                results.BSSID.equalsIgnoreCase(WIFI_MAC_7))
        {
            wifiModel.setSsid(results.SSID);
            wifiModel.setMacAddress(results.BSSID);
            wifiModel.setSignalStrength(results.level);
            return wifiModel;
        } else {
            return null;
        }
    }

    private void scanFailure() {
        Log.i("WIFISCANNER", "scanFailure: "+scannedAPs.size());
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
//        List<ScanResult> results = wifiManager.getScanResults();

//        results.clear();
        //  ... potentially use older scan results ...
//        return results;

    }

    private void dummyData() {

        WifiModel model1 = new WifiModel();
        model1.setSsid("MIPA-Lt.3");
        model1.setSignalStrength(-77);
        model1.setMacAddress(WIFI_MAC_1);

        WifiModel model2 = new WifiModel();
        model2.setSsid("MIPA-Lt.3");
        model2.setSignalStrength(-84);
        model2.setMacAddress(WIFI_MAC_2);

        WifiModel model3 = new WifiModel();
        model3.setSsid("MIPA-Lt.3");
        model3.setSignalStrength(-85);
        model3.setMacAddress(WIFI_MAC_3);

       /* WifiModel model4 = new WifiModel();
        model4.setSsid("YUPI-07");
        model4.setSignalStrength(-44);
        model4.setMacAddress(WIFI_MAC_7);*/

        scannedAPs.put(model1.getMacAddress(), model1);
        scannedAPs.put(model2.getMacAddress(), model2);
        scannedAPs.put(model3.getMacAddress(), model3);
        //scannedAPs.put(model4.getMacAddress(), model4);
        Log.i("SIZE", "dummyData: "+scannedAPs.size());
    }

    public HashMap<String, WifiModel> getScannedAps()
    {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                getAvailableWifi();
//                getWifi();
            }
        }, 6000);
        return scannedAPs;
    }
}
