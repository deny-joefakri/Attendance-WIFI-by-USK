package id.ac.unsyiah.android.absen_mobile.AttendanceStore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    // Nama tabel
    public static final String TABLE_NAME = "ATTENDANCE_TABLE";
    public static final String TABLE_NAMEE = "LECTURER_TABLE";

    // Nama kolom yang ada pada tabel absen
    public static final String COL_1_ID = "id";
    public static final String COL_2_ATTENDANCE_ID = "id_absen";
    public static final String COL_3_HOUR = "jam";
    public static final String COL_4_MINUTE = "menit";
    public static final String COL_5_isAttendance = "isAbsen";
    public static final String COL_6_classID = "idRuang";

    // Nama kolom yang ada pada tabel lecturer
    public static final String COL_1_ID_TABLE = "id";
    public static final String COL_2_KDMK = "kode_mk";
    public static final String COL_3_DATE = "tanggal";
    //Hapus tabel
    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String DATABASE_NAME = "lecture_attendance.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL_1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_2_ATTENDANCE_ID + " INTEGER, " +
                        COL_3_HOUR + " INTEGER, " +
                        COL_4_MINUTE + " INTEGER, " +
                        COL_5_isAttendance + " INTEGER DEFAULT 0, " +
                        COL_6_classID + " INTEGER)";

        final String SQL_CREATE_LECTURER_TABLE =
                "CREATE TABLE " + TABLE_NAMEE + " (" +
                        COL_1_ID_TABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_2_KDMK + " String, " +
                        COL_3_DATE + " String)";

        db.execSQL(SQL_CREATE_TABLE);
        db.execSQL(SQL_CREATE_LECTURER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }
}
