package id.ac.unsyiah.android.absen_mobile.AttendanceStore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import id.ac.unsyiah.android.absen_mobile.Model.DataAttendance;
import id.ac.unsyiah.android.absen_mobile.Model.LecturerAttendance;

public class DatabaseAccess {
    private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;


    /**
     * Private konstruktor untuk menghindari pembuatan objek dari luar kelas.
     *
     * @param context context.
     */
    public DatabaseAccess(Context context){
        this.context = context;
        this.databaseOpenHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Membuka koneksi sqLiteDatabase.
     */
    public void open() {
        this.sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
    }

    /**
     * Menutup koneksi sqLiteDatabase.
     */
    public void close() {
        if (sqLiteDatabase != null) {
            this.sqLiteDatabase.close();
        }
    }

    public void addDataAttendance(DataAttendance dataAttendance){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.COL_2_ATTENDANCE_ID, dataAttendance.getIdAbsen());
        contentValues.put(DatabaseOpenHelper.COL_3_HOUR, dataAttendance.getJamAbsen());
        contentValues.put(DatabaseOpenHelper.COL_4_MINUTE, dataAttendance.getMenitAbsen());
        contentValues.put(DatabaseOpenHelper.COL_5_isAttendance, 0);
        contentValues.put(DatabaseOpenHelper.COL_6_classID, dataAttendance.getIdRuang());

    sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_NAME, null, contentValues);
    }

    public void addLecturerAttendance(LecturerAttendance studentAttendance){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.COL_2_KDMK, studentAttendance.getKdmkSqlite());
        contentValues.put(DatabaseOpenHelper.COL_3_DATE, studentAttendance.getTanggalSqlite());

        sqLiteDatabase.insert(DatabaseOpenHelper.TABLE_NAMEE, null, contentValues);
    }

    public List<DataAttendance> selectIsNotAbsen(int idAbsen){
        List<DataAttendance> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ATTENDANCE_TABLE WHERE isAbsen = 0 and id_absen="+idAbsen, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DataAttendance attendance = new DataAttendance();
            attendance.setId(cursor.getInt(0));
            attendance.setIdAbsen(cursor.getInt(1));
            attendance.setJamAbsen(cursor.getInt(2));
            attendance.setMenitAbsen(cursor.getInt(3));
            attendance.setAbsen(cursor.getInt(4));
            attendance.setIdRuang(cursor.getInt(5));
            list.add(attendance);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean cekIsAbsen(int id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ATTENDANCE_TABLE WHERE isAbsen = 0 and id="+id, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean checkCourseId(String kdmk, String tanggal){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LECTURER_TABLE WHERE kode_mk='"+kdmk+"' AND tanggal='"+tanggal+"'", null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public int countAttendance(int id, int idAbsen){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ATTENDANCE_TABLE WHERE id_absen='"+idAbsen+"' AND id >'"+id+"'", null);
        int total = cursor.getCount();
        cursor.close();
        return total;
    }


    public void updateIsAbsen(int id){
        sqLiteDatabase.execSQL("UPDATE ATTENDANCE_TABLE SET isAbsen = 1 WHERE id="+id);
    }

    public void deleteLecturerAttendance(String tanggal, String kdmk){
        sqLiteDatabase.delete(DatabaseOpenHelper.TABLE_NAMEE,  DatabaseOpenHelper.COL_3_DATE + "='"+tanggal+"' AND "+DatabaseOpenHelper.COL_2_KDMK +"='"+kdmk+"'", null);
    }

    public void deleteDataAttendance(int idAbsen){
        sqLiteDatabase.execSQL("DELETE FROM ATTENDANCE_TABLE WHERE id_absen="+idAbsen);
    }
}
