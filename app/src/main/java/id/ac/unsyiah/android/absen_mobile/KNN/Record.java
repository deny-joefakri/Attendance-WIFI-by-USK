package id.ac.unsyiah.android.absen_mobile.KNN;

//Basic Record class
public class Record {
	double[] attributes;
	int classLabel;
	
	Record(double[] attributes, int classLabel){
		this.attributes = attributes;
		this.classLabel = classLabel;
	}
}
