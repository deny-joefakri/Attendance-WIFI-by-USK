package id.ac.unsyiah.android.absen_mobile.Wifi;

/**
 * Kelas model untuk data BLE
 */
public class ResultModel {
    private int id;
    private int[] rssiValue = {-100,-100,-100,-100,-100,-100,-100,-100}; // Array yang menunjukkan nilai teburuk RSSI secara default.
    private String roomName;

    // variable konstan yang berinisialisasi MacAddress dari setiap Beacon.
    /* Penentuan MAC Address yang akan dipakai untuk mapping */
    public static final String WIFI_MAC_1 = "00:fd:22:c3:0c:af";//DATABASE, JARINGAN | MIPA-Lt.3
    public static final String WIFI_MAC_2 = "00:fd:22:ce:53:cf";//DATABASE, JARINGAN | MIPA-Lt.3
    public static final String WIFI_MAC_3 = "00:fd:22:ce:58:40";//DATABASE, JARINGAN | MIPA-Lt.3
    public static final String WIFI_MAC_4 = "00:fd:22:ad:47:8f";//B.03.02 | MIPA-Lt.2
    public static final String WIFI_MAC_5 = "00:fd:22:ad:47:80";//B.03.02 | FKH
    public static final String WIFI_MAC_6 = "00:fd:22:ce:47:cf";//B.03.02 | MIPA-Lt.2
    public static final String WIFI_MAC_7 = "fc:bc:d1:67:28:c4";//B.03.02 | MIPA-Lt.2

    /**
     * @return id mengembalikan id data
     */
    public int getId() {
        return id;
    }

    /**
     * @param id id data
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return mengembalikan nilai array indeks 0
     */
    public int getWlan1() {
        return rssiValue[0];
    }

    /**
     * @param ble1 nilai rssi ble pertama
     */
    public void setWlan1(int ble1) {
        this.rssiValue[0] = ble1;
    }

    /**
     * @return mengembalikan nilai array indeks 1
     */
    public int getWlan2() {
        return rssiValue[1];
    }

    /**
     * @param ble2 nilai rssi ble ke-dua
     */
    public void setWlan2(int ble2) {
        this.rssiValue[1] = ble2;
    }

    /**
     * @return mengembalikan nilai array indeks 2
     */
    public int getWlan3() {
        return rssiValue[2];
    }

    /**
     * @param ble3 nilai rssi ble ke-tiga
     */
    public void setWlan3(int ble3) {
        this.rssiValue[2] = ble3;
    }

    /**
     * @return mengembalikan nilai array indeks 3
     */
    public int getWlan4() {
        return rssiValue[3];
    }

    /**
     * @param ble4 dnilai rssi ble ke-empat
     */
    public void setWlan4(int ble4) {
        this.rssiValue[3] = ble4;
    }

    /**
     * @return mengembalikan nilai array indeks 4
     */
    public int getWlan5() {
        return rssiValue[4];
    }

    /**
     * @param ble5 nilai rssi ble ke-lima
     */
    public void setWlan5(int ble5) {
        this.rssiValue[4] = ble5;
    }

    /**
     * @return mengembalikan nilai array indeks 5
     */
    public int getWlan6() {
        return rssiValue[5];
    }

    /**
     * @param ble6 nilai rssi ble ke-enam
     */
    public void setWlan6(int ble6) {
        this.rssiValue[5] = ble6;
    }

    /**
     * @return mengembalikan nilai Mac Address 1
     */
    public int getWlan7() {
        return rssiValue[6];
    }

    /**
     * @param ble7 nilai rssi ble ke-tujuh
     */
    public void setWlan7(int ble7) {
        this.rssiValue[7] = ble7;
    }

    /**
     * @return mengembalikan nilai array indeks 7
     */
    public int getWlan8() {
        return rssiValue[7];
    }

    /**
     * @param ble8 nilai rssi ble ke-delapan
     */
    public void setWlan8(int ble8) {
        this.rssiValue[7] = ble8;
    }

    /**
     * @return mengembalikan nilai array indeks 8
     */
    public int getWlan9() {
        return rssiValue[8];
    }

    /**
     * @param ble9 nilai rssi ble ke-sembilan
     */
    public void setWlan9(int ble9) {
        this.rssiValue[8] = ble9;
    }

    public String getMac1() {
        return WIFI_MAC_1;
    }

    /**
     * @return mengembalikan nilai Mac Address 2
     */
    public String getMac2() {
        return WIFI_MAC_2;
    }

    /**
     * @return mengembalikan nilai Mac Address 3
     */
    public String getMac3() {
        return WIFI_MAC_3;
    }

    /**
     * @return mengembalikan nilai Mac Address 4
     */
    public String getMac4() {
        return WIFI_MAC_4;
    }

    /**
     * @return mengembalikan nilai Mac Address 5
     */
    public String getMac5() {
        return WIFI_MAC_5;
    }

    /**
     * @return mengembalikan nilai Mac Address 6
     */
    public String getMac6() {
        return WIFI_MAC_6;
    }

    /**
     * @return roomName mengembalikan nama ruang
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * @param roomName memodifikasi nama ruang.
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }


    /** @param address macAdress dari setiap BLE/Beacon
     *  @param rssi nilai rssi dari setiap BLE/Beacon
     */
    public void setWlan(String address, int rssi){
        switch (address){
            case WIFI_MAC_1:
                setWlan1(rssi);
                break;
            case WIFI_MAC_2:
                setWlan2(rssi);
                break;
            case WIFI_MAC_3:
                setWlan3(rssi);
                break;
            case WIFI_MAC_4:
                setWlan4(rssi);
                break;
            case WIFI_MAC_5:
                setWlan5(rssi);
                break;
            case WIFI_MAC_6:
                setWlan6(rssi);
                break;
            case WIFI_MAC_7:
                setWlan7(rssi);
                break;
        }

    }
}
