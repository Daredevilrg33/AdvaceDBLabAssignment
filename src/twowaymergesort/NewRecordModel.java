package twowaymergesort;

public class NewRecordModel {
	int CID;
	String Amount_Paid;

	NewRecordModel(int CID, String Amount_Paid) {
		this.CID = CID;
		this.Amount_Paid = Amount_Paid;
	}

	public String toString() {
		return CID + Amount_Paid;
	}
}
