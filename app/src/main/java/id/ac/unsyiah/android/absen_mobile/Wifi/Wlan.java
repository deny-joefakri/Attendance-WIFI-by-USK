package id.ac.unsyiah.android.absen_mobile.Wifi;

public class Wlan {
        private int id;
        private int[] rssiValue = {-100,-100,-100,-100,-100,-100}; // Array yang menunjukkan nilai teburuk RSSI secara default.
        private String roomName;

        // variable konstan yang berinisialisasi MacAddress dari setiap WLAN.
        private static final String WIFI_MAC_1 = "00:fd:22:c3:0c:af";//DATABASE, JARINGAN | MIPA-Lt.3
        private static final String WIFI_MAC_2 = "00:fd:22:ce:53:cf";//DATABASE, JARINGAN | MIPA-Lt.3
        private static final String WIFI_MAC_3 = "00:fd:22:ce:58:40";//DATABASE, JARINGAN | MIPA-Lt.3
        private static final String WIFI_MAC_4 = "00:fd:22:c4:fc:8f";//DMIR | MIPA-Lt.2
        private static final String WIFI_MAC_5 = "00:fd:22:ce:4d:c0";//DMIR | MIPA-Lt.2
        private static final String WIFI_MAC_6 = "00:fd:22:a3:61:20";//DMIR | MIPA-Lt.2

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
         * @param wlan1 nilai rssi ble pertama
         */
        public void setWlan1(int wlan1) {
            this.rssiValue[0] = wlan1;
        }

        /**
         * @return mengembalikan nilai array indeks 1
         */
        public int getWlan2() {
            return rssiValue[1];
        }

        /**
         * @param wlan2 nilai rssi ble ke-dua
         */
        public void setWlan2(int wlan2) {
            this.rssiValue[1] = wlan2;
        }

        /**
         * @return mengembalikan nilai array indeks 2
         */
        public int getWlan3() {
            return rssiValue[2];
        }

        /**
         * @param wlan3 nilai rssi ble ke-tiga
         */
        public void setWlan3(int wlan3) {
            this.rssiValue[2] = wlan3;
        }

        /**
         * @return mengembalikan nilai array indeks 3
         */
        public int getWlan4() {
            return rssiValue[3];
        }

        /**
         * @param wlan4 dnilai rssi ble ke-empat
         */
        public void setWlan4(int wlan4) {
            this.rssiValue[3] = wlan4;
        }

        /**
         * @return mengembalikan nilai array indeks 4
         */
        public int getWlan5() {
            return rssiValue[4];
        }

        /**
         * @param wlan5 nilai rssi ble ke-lima
         */
        public void setWlan5(int wlan5) {
            this.rssiValue[4] = wlan5;
        }

        /**
         * @return mengembalikan nilai array indeks 5
         */
        public int getWlan6() {
            return rssiValue[5];
        }

        /**
         * @param wlan6 nilai rssi ble ke-enam
         */
        public void setWlan6(int wlan6) {
            this.rssiValue[5] = wlan6;
        }

        /**
         * @return mengembalikan nilai Mac Address 1
         */
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
        public void setBle(String address, int rssi){
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
            }

        }
    }


