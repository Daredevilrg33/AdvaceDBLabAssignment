package twowaymergesort;

import static java.util.stream.Collectors.toMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TPMMS {
	public static List<List<String>> data;
	public static int lines_read = 1;
	public static int file_count = 0;
	public static String line;
	private static long startTime;
	static int[] startIndices = { 0, 8, 18, 27, 52, 202, 230, 232, 241, 250 };

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Runtime.getRuntime().gc();
		startTime = System.currentTimeMillis();
		System.out.println("Start Time: " + startTime);
		File file = new File(
				"D:\\Concordia\\COMP 6521 ADV DATABASE TECH AND APPL\\LabAssignment1Project\\AdvaceDBLabAssignment\\src\\input.txt");
		BufferedReader bufferReader = new BufferedReader(new FileReader(file));
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the RAM size : ");
		int ram_size = scan.nextInt();
		ram_size = ram_size - 1;
		scan.close();
		scan = null;
		line = bufferReader.readLine();
		int record_size = line.length();
		int numberOfLinesToRead = ram_size * 1024 * 1024 / (2 * record_size);
//		numberOfLinesToRead = 3000;
		System.out.println("Record Size = " + record_size + " Lines to Read = " + numberOfLinesToRead);
		data = new ArrayList<>(numberOfLinesToRead);

		while (line != null) {
			List<String> record = new ArrayList<String>(startIndices.length);
			for (int i = 0; i < startIndices.length - 1; i++)
				record.add(line.substring(startIndices[i], startIndices[i + 1]).trim());
			data.add(record);

//			System.out.println("size of data : " + data.size());
			if (lines_read % numberOfLinesToRead == 0) {
				Collections.sort(data, new DesendingOrderAmountPaidComparator());
//				Collections.sort(data, new AscendingOrderInsuredItem());
				writeIntoFile(file_count++, false);
				data.clear();
			}
			line = bufferReader.readLine();
			lines_read++;
		}
		if (data.size() > 0) {
			Collections.sort(data, new DesendingOrderAmountPaidComparator());
//			Collections.sort(data, new AscendingOrderInsuredItem());
			writeIntoFile(file_count++, false);
		}
		data.clear();
//		data = null;
		System.out.println("File Count : " + file_count);
		System.out.println("Number of line to read : " + numberOfLinesToRead);
		int noOfRecordsPerFile = numberOfLinesToRead / file_count;
		System.out.println("Number of records from a file : " + noOfRecordsPerFile);
		Runtime.getRuntime().gc();
		mergeFiles(noOfRecordsPerFile);
		System.out.println("Merging done!");
	}

	public static void mergeFiles(int noOfRecordsPerFile) throws Exception {
//		noOfRecordsPerFile = noOfRecordsPerFile - 2000;
//		noOfRecordsPerFile = 5;
		System.out.println("Number of records from a file : " + noOfRecordsPerFile);
		List<Integer> files_list = new ArrayList<Integer>();
		for (int i = 0; i < file_count; i++) {
			files_list.add(i);
		}
//		data = new ArrayList<>(noOfRecordsPerFile * 3);
		BufferedReader[] bufferedReaders = new BufferedReader[file_count];
		File merge_file = new File("PART-" + Integer.MAX_VALUE + ".txt");
		if (merge_file.delete()) {
			System.out.println("Merge file deleted!");
		} else {
			System.out.println("file does not exist!");
		}
		for (int i = 0; i < file_count; i++) {
			bufferedReaders[i] = new BufferedReader(new FileReader("PART-" + i + ".txt"));
		}
		// String frontRecord;

		while (files_list.size() != 0) {
			for (int j = 0; j < files_list.size(); j++) {
				line = bufferedReaders[files_list.get(j)].readLine();
				while (line != null) {
					List<String> record = new ArrayList<String>(startIndices.length);
//					for (int i = 0; i < startIndices.length - 1; i++)
//						record.add(line.substring(startIndices[i], startIndices[i + 1]).trim());
					for (String attribute : line.trim().split("#"))
						record.add(attribute);
					data.add(record);

					line = bufferedReaders[files_list.get(j)].readLine();
					if (line == null) {
						files_list.remove(files_list.get(j));
						break;
					}
				}
			}
			Collections.sort(data, new DesendingOrderAmountPaidComparator());
//			Collections.sort(data, new AscendingOrderInsuredItem());
			writeIntoFile(Integer.MAX_VALUE, true);
			data.clear();

		}

		File file = new File("PART-" + Integer.MAX_VALUE + ".txt");
		BufferedReader bufferReader = new BufferedReader(new FileReader(file));
		line = bufferReader.readLine();
		Runtime.getRuntime().gc();
		// System.out.println(line);
		// System.out.println(line.length());
		String[] str = line.split("#");
		System.out.println("cid : " + str[2]);
		System.out.println("Amount Paid : " + str[8]);

		String value = str[2].concat(str[8]);
		System.out.println(value + " : Length: " + value.length());
		int record_size = value.length() + 2;
		int numberOfLinesToRead = 5 * 1024 * 1024 / (2 * record_size);
		HashMap<String, BigDecimal> hashMap = new HashMap<>(numberOfLinesToRead);
		while (line != null) {
			// System.out.println(lines_read + " " + line + " " + line.length());
			String[] str1 = line.split("#");
			if (hashMap.containsKey(str1[2])) {

				BigDecimal a1 = hashMap.get(str1[2]).add(new BigDecimal(str1[8]));
				hashMap.put(str1[2], a1);

			} else {
				hashMap.put(str1[2], new BigDecimal(str1[8]));

			}
			line = bufferReader.readLine();
			lines_read++;
		}
		Map<String, BigDecimal> sorted = hashMap.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		// System.out.println(lines_read);
		// System.out.println(count);
		writeIntoFile("Final Output", sorted);
		System.out.println("FINAL OUTPUT");
		System.out.println("Time Taken : " + (System.currentTimeMillis() - startTime));
	}

	public static void writeIntoFile(int file_no, boolean append) throws Exception {
		File file = new File("PART-" + file_no + ".txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(file.getAbsolutePath(), append);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		for (int i = 0; i < data.size(); i++) {
			bufferedWriter.write(String.join("#", data.get(i)) + "\n");
//			System.out.println(String.join("#", data.get(i)));
		}
		bufferedWriter.close();
		fileWriter.close();
	}

	public static void writeIntoFile(String fileName, Map<String, BigDecimal> map) throws Exception {
		File file = new File(fileName + ".txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(file.getAbsolutePath(), true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		for (String key : map.keySet()) {
			bufferedWriter.write(key + "#" + map.get(key) + "\n");
		}

		bufferedWriter.close();
		fileWriter.close();
	}
}
