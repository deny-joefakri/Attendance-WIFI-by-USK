package id.ac.unsyiah.android.absen_mobile.KNN;//basic metric interface

public interface Metric {
	double getDistance(Record s, Record e);
}
