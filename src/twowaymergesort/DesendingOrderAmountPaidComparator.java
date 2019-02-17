package twowaymergesort;

import java.util.Comparator;
import java.util.List;

class DesendingOrderAmountPaidComparator implements Comparator<List<String>>
{
	@Override
	public int compare(List<String> list1,List<String> list2)
	{
		double a1 = Double.parseDouble(list1.get(8));
		double a2 = Double.parseDouble(list2.get(8));
		return (int)(a2-a1);
	}
}