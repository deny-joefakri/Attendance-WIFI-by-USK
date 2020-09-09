package id.ac.unsyiah.android.absen_mobile.Model;

/**
 * Kelas model untuk data absen sqlite
 */
public class DataAttendance {
    private int id, idAbsen;
    private int jamAbsen, menitAbsen;
    private int isAbsen;
    private int idRuang;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAbsen() {
        return idAbsen;
    }

    public void setIdAbsen(int idAbsen) {
        this.idAbsen = idAbsen;
    }

    public int getJamAbsen() {
        return jamAbsen;
    }

    public void setJamAbsen(int jamAbsen) {
        this.jamAbsen = jamAbsen;
    }

    public int getMenitAbsen() {
        return menitAbsen;
    }

    public void setMenitAbsen(int menitAbsen) {
        this.menitAbsen = menitAbsen;
    }

    public int isAbsen() {
        return isAbsen;
    }

    public void setAbsen(int absen) {
        isAbsen = absen;
    }

    public int getIdRuang() {
        return idRuang;
    }

    public void setIdRuang(int idRuang) {
        this.idRuang = idRuang;
    }
}
