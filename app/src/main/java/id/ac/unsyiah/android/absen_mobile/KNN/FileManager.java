package id.ac.unsyiah.android.absen_mobile.KNN;//FileManager
// * ReadFile: read training files and test files
// * OutputFile: output predicted labels into a file

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

public class FileManager {
	
	//read training files
	public static TrainRecord[] readTrainFile(InputStream inputStream) throws IOException{
		Log.d("Terserah", "membaca file train");
//		File file = new File(fileName);
//		Log.d("Terserah", "coba file : "+file.exists());
		Scanner scanner = new Scanner(inputStream).useLocale(Locale.US);

		//read file
		int NumOfSamples = scanner.nextInt();
		int NumOfAttributes = scanner.nextInt();
		int LabelOrNot = scanner.nextInt();
		Log.d("Terserah", NumOfSamples+""+NumOfAttributes+""+LabelOrNot);
		scanner.nextLine();
		
		assert LabelOrNot == 1 : "No classLabel";// ensure that C is present in this file

		//transform data from file into TrainRecord objects
		TrainRecord[] records = new TrainRecord[NumOfSamples];
		int index = 0;
		while(scanner.hasNext()){
			double[] attributes = new double[NumOfAttributes];
			int classLabel = -1;
			
			//Read a whole line for a TrainRecord
			for(int i = 0; i < NumOfAttributes; i ++){
				attributes[i] = scanner.nextDouble();
				Log.d("Terserah", attributes[i]+"");
			}

			//Read classLabel
			classLabel = (int) scanner.nextDouble();
			assert classLabel != -1 : "Reading class label is wrong!";
			
			records[index] = new TrainRecord(attributes, classLabel);
			index ++;
		}
		return records;
	}

	public static TestRecord readTestFile(double[] testData) {
		return new TestRecord(testData, -1);
	}


}
