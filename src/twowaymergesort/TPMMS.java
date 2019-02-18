package twowaymergesort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TPMMS {
	public static List<List<String>> data = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Runtime.getRuntime().gc();

		File file = new File(
				"D:\\Concordia\\COMP 6521 ADV DATABASE TECH AND APPL\\LabAssignment1Project\\AdvaceDBLabAssignment\\src\\input.txt");

		BufferedReader bufferReader = new BufferedReader(new FileReader(file));
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the RAM size : ");
		int ram_size = scan.nextInt();
		scan.close();
		String line = bufferReader.readLine();
		// System.out.println(line);
		// System.out.println(line.length());
		int record_size = line.length();
		int count = 1;
		int file_count = 0;
		int lines_read = 1;
		int numberOfLinesToRead = ram_size * 1024 * 1024 / (16 * record_size);
		System.out.println("Record Size = " + record_size + " Lines to Read = " + numberOfLinesToRead);
		// List<List<String>> data = new ArrayList<>();
		int[] startIndices = { 0, 8, 18, 27, 52, 202, 230, 232, 241, 250 };
		while (line != null) {
			System.out.println(lines_read + " " + line + " " + line.length());
			List<String> record = new ArrayList<String>();
			for (int i = 0; i < startIndices.length - 1; i++)
				record.add(line.substring(startIndices[i], startIndices[i + 1]).trim());
			data.add(record);
			if (lines_read % numberOfLinesToRead == 0) {
				Collections.sort(data, new DesendingOrderAmountPaidComparator());
				Collections.sort(data, new AscendingOrderInsuredItem());
				writeIntoFile(file_count++);
				data.clear();
			}
			line = bufferReader.readLine();
			lines_read++;
		}
		if (data.size() > 0)
			writeIntoFile(file_count++);
		data.clear();
		int noOfRecordsPerFile = numberOfLinesToRead / file_count;
		int i = 0;
		while (i < numberOfLinesToRead) {
			for (int j = 0; j < file_count; j++) {
				int counter = 0;
				bufferReader = new BufferedReader(new FileReader(new File(
						"D:\\Concordia\\COMP 6521 ADV DATABASE TECH AND APPL\\LabAssignment1Project\\AdvaceDBLabAssignment\\PART-"
								+ j + ".txt")));

				while (counter < noOfRecordsPerFile && !bufferReader.readLine().trim().equalsIgnoreCase("")) {

					String[] val = bufferReader.readLine().split("#");
					i++;
					List<String> record = new ArrayList<String>();
					for (String value : val)
						record.add(value);
					data.add(record);
					counter++;
				}
			}
			Collections.sort(data, new DesendingOrderAmountPaidComparator());
			Collections.sort(data, new AscendingOrderInsuredItem());
			writeIntoFile(99);
			data.clear();
		}

	}

	public static void writeIntoFile(int file_no) throws Exception {
		File file = new File("PART-" + file_no + ".txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(file.getAbsolutePath());
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		for (int i = 0; i < data.size(); i++) {
			bufferedWriter.write(String.join("#", data.get(i)) + "\n");
			// System.out.println(String.join("#",data.get(i)));
		}
		bufferedWriter.close();
		fileWriter.close();
	}
}
