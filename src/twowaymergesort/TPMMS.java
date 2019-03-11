package twowaymergesort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TPMMS {

	static BufferedReader mainReader;
	static BufferedReader phase2Reader;
	static List<RecordModel> records;
	static ArrayList<NewRecordModel> records2 = new ArrayList<NewRecordModel>();
	static int fileCounter = 0;
	static int ramSize;
	static int noOfRecords;
	static int inOneGo;
	static int recordCounter;
	static FileReader mergeFile;
	static FileReader mainFile;
	static long startTime;
	static int recordCounter2;
	static int noOfRecords2;
	static int fileCounter2 = 0;
	static int inOneGo2;

	static void parse1(String line) {
		RecordModel recordModel = new RecordModel();
		recordModel.paidAmount = line.substring(241, 250);
		recordModel.id = line.substring(18, 27);
		recordModel.cNumber = line.substring(0, 8);
		recordModel.date = line.substring(8, 18);
		recordModel.name = line.substring(27, 52);
		recordModel.address = line.substring(52, 202);
		recordModel.email = line.substring(202, 230);
		recordModel.insured_item = line.substring(230, 232);
		recordModel.damageAmount = line.substring(232, 241);
		records.add(recordModel);
	}

	static void writeToFile() {
		try {
			File newFile = new File(
					"D:\\Concordia\\COMP 6521 ADV DATABASE TECH AND APPL\\LabAssignment1Project\\AdvaceDBLabAssignment\\src\\"
							+ fileCounter + ".txt");
			fileCounter++;
			newFile.createNewFile();
			FileWriter fileWriter = new FileWriter(newFile.getAbsolutePath(), true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (RecordModel r : records) {
				bufferedWriter.write(r.toString() + "\n");
			}
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
	}

	private static void writeToFinal() {
		// TODO Auto-generated method stub
		try {
			File newFile = new File(
					"D:\\Concordia\\COMP 6521 ADV DATABASE TECH AND APPL\\LabAssignment1Project\\AdvaceDBLabAssignment\\src\\outputfin.txt");
			newFile.createNewFile();
			FileWriter fileWriter = new FileWriter(newFile.getAbsolutePath(), true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (RecordModel r : records) {
				// System.out.println(r.Amount_Paid);
				bufferedWriter.write(r.toString() + "\n");
			}
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void phase1Sort() {
		String line;
		try {
			while ((line = mainReader.readLine()) != null) {
				if (records.size() == inOneGo) {
					Collections.sort(records);

					// records.sort((c1, c2) -> c2.paidAmount.compareTo(c1.paidAmount));
					writeToFile();
					records.clear();
					System.gc();
				}
				parse1(line);
				recordCounter++;
			}
			mainReader.close();
			mainFile.close();
			if (records.size() > 0) {
				Collections.sort(records);
				// records.sort((c1, c2) ->
				// String.valueOf(c2.Amount_Paid).compareTo(String.valueOf(c1.Amount_Paid)));
				writeToFile();
				records.clear();
				System.gc();
			}

		} catch (OutOfMemoryError | IOException e) {
			e.printStackTrace();
		}

	}

	public static void phase2Sort() {
		recordCounter = 0;
		int lineNo[] = new int[fileCounter];
		try {
			for (int i = 0; i < fileCounter; i++)
				lineNo[i] = 0;

			for (int i = 0; i < fileCounter; i++) {
				if (recordCounter == noOfRecords) {
					break;
				}
				mergeFile = new FileReader(
						"D:\\Concordia\\COMP 6521 ADV DATABASE TECH AND APPL\\LabAssignment1Project\\AdvaceDBLabAssignment\\src\\"
								+ i + ".txt");
				phase2Reader = new BufferedReader(mergeFile);
				String line = null;
				int loopCounter = 0;
				while ((line = phase2Reader.readLine()) != null) {
					if (loopCounter == lineNo[i]) {
						lineNo[i] += 1;
						if (records.size() == inOneGo) {
							for (int j = 0; j < lineNo.length; j++) {
								System.out.println("File No " + j + ":" + lineNo[j]);
							}
							System.out.println("End");
							System.out.println(recordCounter);
							Collections.sort(records);

							// records.sort((c1, c2) -> String.valueOf(c2.Amount_Paid)
							// .compareTo(String.valueOf(c1.Amount_Paid)));
							writeToFinal();
							records.clear();
							// System.gc();
						}
						parse1(line);
						// records.add(u);
						recordCounter++;
						// System.out.println(recordCounter);
						break;
					}
					loopCounter++;
				}
				phase2Reader.close();
				mergeFile.close();
				if (i == fileCounter - 1 && recordCounter != noOfRecords) {
					i = -1;
				}
			}
			if (records.size() > 0) {
				Collections.sort(records);

				// records.sort((c1, c2) ->
				// String.valueOf(c2.Amount_Paid).compareTo(String.valueOf(c1.Amount_Paid)));
				writeToFinal();
				records.clear();
				System.gc();
			}
			for (int i = 0; i < fileCounter; i++) {
				File file = new File(
						"D:\\Concordia\\COMP 6521 ADV DATABASE TECH AND APPL\\LabAssignment1Project\\AdvaceDBLabAssignment\\src\\"
								+ i + ".txt");
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void writeToFile2() {
		try {
			File newFile = new File(
					"D:\\Concordia\\COMP 6521 ADV DATABASE TECH AND APPL\\LabAssignment1Project\\AdvaceDBLabAssignment\\src\\"
							+ fileCounter2 + ".txt");
			fileCounter2++;
			newFile.createNewFile();
			FileWriter fileWriter = new FileWriter(newFile.getAbsolutePath(), true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (NewRecordModel newRecordModel : records2) {
				bufferedWriter.write(newRecordModel.CID + "#" + newRecordModel.Amount_Paid + "\n");
			}
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
	}

	private static void phase12Sort() {
		String line;
		try {
			while ((line = mainReader.readLine()) != null) {
				if (records2.size() == inOneGo2) {
					records2.sort((c1, c2) -> new BigDecimal(c2.Amount_Paid).compareTo(new BigDecimal(c1.Amount_Paid)));
					writeToFile2();
					records2.clear();
					System.gc();
				}
				parse2(line);
			}
			mainReader.close();
			mainFile.close();
			if (records2.size() > 0) {
				records2.sort((c1, c2) -> new BigDecimal(c2.Amount_Paid).compareTo(new BigDecimal(c1.Amount_Paid)));
				writeToFile2();
				records2.clear();
				System.gc();
			}

		} catch (OutOfMemoryError | IOException e) {
			e.printStackTrace();
		}
	}

	static void parse2(String line) {
		int flag = 0;
		int i = Integer.parseInt(line.substring(18, 27));
		String s = line.substring(241, line.length());
		for (NewRecordModel r : records2) {
			if (i == r.CID) {
				r.Amount_Paid = (new BigDecimal(r.Amount_Paid).add(new BigDecimal(s))).toString();
				flag = 1;
			}
		}
		if (flag == 0) {
			NewRecordModel r = new NewRecordModel(i, s);
			records2.add(r);
			noOfRecords2++;
		}
	}

	public static void main(String args[]) {
		try {

			Scanner s = new Scanner(System.in);
			System.out.println("Enter Ram Size");
			System.out.println("Enter no of Records");
			ramSize = s.nextInt();
			noOfRecords = s.nextInt();
			inOneGo = ramSize * 700;
			startTime = System.currentTimeMillis();
			records = new ArrayList<>(inOneGo);
			mainFile = new FileReader(
					"D:\\Concordia\\COMP 6521 ADV DATABASE TECH AND APPL\\LabAssignment1Project\\AdvaceDBLabAssignment\\src\\input.txt");
			mainReader = new BufferedReader(mainFile);
			phase1Sort();
			System.out.println("Phase 1 Complete: " + (System.currentTimeMillis() - startTime));
			records.clear();
			System.out.println(recordCounter);
			System.gc();
			recordCounter = 0;
			phase2Sort();
			System.out.println("Phase 2 Complete: " + (System.currentTimeMillis() - startTime));

			System.out.println(recordCounter);
			mainFile = new FileReader(
					"D:\\Concordia\\COMP 6521 ADV DATABASE TECH AND APPL\\LabAssignment1Project\\AdvaceDBLabAssignment\\src\\outputfin.txt");
			mainReader = new BufferedReader(mainFile);
			phase12Sort();
			System.out.println("RecordCounter2:" + noOfRecords2);
			if (fileCounter2 > 1) {
				phase22Sort();
			}
			System.out.println("Final Time: " + (System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in main " + e);
		}
	}

	public static void phase22Sort() {
		recordCounter2 = 0;
		int lineNo[] = new int[fileCounter2];
		try {
			for (int i = 0; i < fileCounter2; i++)
				lineNo[i] = 0;

			for (int i = 0; i < fileCounter2; i++) {
				if (recordCounter2 == noOfRecords2) {
					break;
				}
				mergeFile = new FileReader(
						"D:\\Concordia\\COMP 6521 ADV DATABASE TECH AND APPL\\LabAssignment1Project\\AdvaceDBLabAssignment\\src\\"
								+ i + ".txt");
				phase2Reader = new BufferedReader(mergeFile);
				String line = null;
				int loopCounter = 0;
				while ((line = phase2Reader.readLine()) != null) {
					if (loopCounter == lineNo[i]) {
						lineNo[i] += 1;
						if (records2.size() == inOneGo2) {
							for (int j = 0; j < lineNo.length; j++) {
								System.out.println("File No " + j + ":" + lineNo[j]);
							}
							System.out.println("End");
							System.out.println(recordCounter);
							records2.sort((c1, c2) -> new BigDecimal(c2.Amount_Paid)
									.compareTo(new BigDecimal(c1.Amount_Paid)));
							writeToFinal2();
							records2.clear();
							System.gc();
						}
						int flag = 0;
						int ij = Integer.parseInt(line.substring(0, 9));
						String s = line.substring(11, line.length());
						for (NewRecordModel r : records2) {
							if (ij == r.CID) {
								r.Amount_Paid = (new BigDecimal(r.Amount_Paid).add(new BigDecimal(s))).toString();
								flag = 1;
							}
						}
						if (flag == 0) {
							NewRecordModel r = new NewRecordModel(i, s);
							records2.add(r);
							noOfRecords2++;
						}

						recordCounter2++;
						// System.out.println(recordCounter);
						break;
					}
					loopCounter++;
				}
				phase2Reader.close();
				mergeFile.close();
				if (i == fileCounter2 - 1 && recordCounter2 != noOfRecords2) {
					i = -1;
				}
			}
			if (records2.size() > 0) {
				records2.sort((c1, c2) -> new BigDecimal(c2.Amount_Paid).compareTo(new BigDecimal(c1.Amount_Paid)));
				writeToFinal2();
				records2.clear();
				System.gc();
			}
			for (int i = 0; i < fileCounter2; i++) {
				File file = new File("F:\\DataSet\\Output\\output2" + i + ".txt");
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void writeToFinal2() {
		// TODO Auto-generated method stub
		try {
			File newFile = new File(
					"D:\\Concordia\\COMP 6521 ADV DATABASE TECH AND APPL\\LabAssignment1Project\\AdvaceDBLabAssignment\\src\\outputfin2.txt");
			newFile.createNewFile();
			FileWriter fileWriter = new FileWriter(newFile.getAbsolutePath(), true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (NewRecordModel r : records2) {
				bufferedWriter.write(r.toString() + "\n");
				recordCounter2++;
			}
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
