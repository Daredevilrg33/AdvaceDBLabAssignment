package twowaymergesort;

import java.util.Arrays;

public class RecordModel implements Comparable<RecordModel> {

	String cNumber;
	String date;
	String id;
	String name;
	String address;
	String email;
	String insured_item;
	String damageAmount;
	String paidAmount;

	@Override
	public int compareTo(RecordModel o) {
		// TODO Auto-generated method stub
		return (int) (Double.valueOf(o.paidAmount.toString()) - Double.valueOf(paidAmount.toString()));
	}

	public String toString() {
		return cNumber.toString() + date.toString() + id.toString() + name.toString() + address.toString()
				+ email.toString() + insured_item.toString() + damageAmount.toString() + paidAmount.toString();
	}

}
