package twowaymergesort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TPMMS {
	public static List<List<String>> data = new ArrayList<>();
    public static int lines_read = 1;
    public static int file_count = 0;
    public static String line;
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Runtime.getRuntime().gc();
		File file = new File("C:\\Users\\SATISH\\Desktop\\AdvanceDBProject\\AdvaceDBLabAssignment\\bin\\input.txt");
		BufferedReader bufferReader = new BufferedReader(new FileReader(file));
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the RAM size : ");
		int ram_size = scan.nextInt();
		scan.close();
		//String line = bufferReader.readLine();
		line = bufferReader.readLine();
		// System.out.println(line);
		// System.out.println(line.length());
		int record_size = line.length();
		int count = 1;
		//int file_count = 0;
		//int lines_read = 1;
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
				writeIntoFile(file_count++,false);
				data.clear();
			}
			line = bufferReader.readLine();
			lines_read++;
		}
		if (data.size() > 0)
			writeIntoFile(file_count++,false);
		data.clear();
		System.out.println("File Count : "+file_count);
		System.out.println("Number of line to read : "+numberOfLinesToRead);
		int noOfRecordsPerFile = numberOfLinesToRead / file_count;
		System.out.println("Number of records from a file : "+noOfRecordsPerFile);
		mergeFiles(noOfRecordsPerFile);
		System.out.println("Merging done!");
	}
	public static void mergeFiles(int noOfRecordsPerFile) throws Exception
    {
		int count = 0;
		List<Integer> files_list = new ArrayList<Integer>();
		for(int i=0;i<file_count;i++)
			files_list.add(i);
		BufferedReader[] bufferedReaders = new BufferedReader[file_count];
		File merge_file = new File("PART-99.txt");
		if(merge_file.delete())
		{
			System.out.println("Merge file deleted!");
		}
		else
		{
			System.out.println("file does not exist!");
		}
		for(int i=0;i<file_count;i++)
			bufferedReaders[i] = new BufferedReader(new FileReader("PART-"+i+".txt"));
		while(files_list.size()!=0) 
		{
			System.out.println(files_list.size());
			for(int j = 0; j < files_list.size(); j++) {
				line = bufferedReaders[files_list.get(j)].readLine();
				while (count < noOfRecordsPerFile && line!=null ) {
					   List<String> record = new ArrayList<String>();   
					   for(String attribute : line.trim().split("#"))
					       record.add(attribute);
					   data.add(record);
					   line = bufferedReaders[files_list.get(j)].readLine();
					   if(line==null)
					   {
						   files_list.remove(files_list.get(j));
						   break;
					   }
					   //count++;
					   //counter++;	   
				}
			}
			Collections.sort(data, new DesendingOrderAmountPaidComparator());
			Collections.sort(data, new AscendingOrderInsuredItem());
			writeIntoFile(99,true);
			data.clear();
		}
        //System.out.println(lines_read);
        //System.out.println(count);
    }
	public static void writeIntoFile(int file_no,boolean append) throws Exception {
		File file = new File("PART-" + file_no + ".txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(file.getAbsolutePath(),append);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		for (int i = 0; i < data.size(); i++) {
			bufferedWriter.write(String.join("#", data.get(i)) + "\n");
			// System.out.println(String.join("#",data.get(i)));
		}
		bufferedWriter.close();
		fileWriter.close();
	}
}
