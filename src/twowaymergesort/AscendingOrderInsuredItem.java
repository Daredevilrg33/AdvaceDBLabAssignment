package twowaymergesort;

import java.util.Comparator;
import java.util.List;

class AscendingOrderInsuredItem implements Comparator<List<String>> {
	@Override
	public int compare(List<String> list1, List<String> list2) {
		int i1 = Integer.parseInt(list1.get(6));
		int i2 = Integer.parseInt(list2.get(6));
		return i1 - i2;
	}
}
