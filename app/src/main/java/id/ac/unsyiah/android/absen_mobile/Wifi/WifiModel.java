package id.ac.unsyiah.android.absen_mobile.Wifi;

/**
 * Kelas model untuk data WiFi
 * @author Kurnia Saputra, Viska Mutiawani, Cut Thifal Nazila, Muliawati Rezeki, dan Emi Sahara
 * */

public class WifiModel {
    private String ssid;
    private String macAddress;
    private int signalStrength;

    /**
     * @return String mengembalikan SSID
     * */
    public String getSsid() {
        return ssid;
    }

    /**
     * @param ssid SSID dari suatu AP
     * */
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    /**
     * @return String mengembalikan MAC Address
     * */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * @param macAddress MAC Address dari suatu AP
     * */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * @return int mengembalikan kekuatan signal
     * */
    public int getSignalStrength() {
        return signalStrength;
    }
    /**
     * @param signalStrength kekuatan signal dari suatu AP
     * */
    public void setSignalStrength(int signalStrength) {
        this.signalStrength = signalStrength;
    }

}
