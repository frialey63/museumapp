package org.pjp.museum.ui.bean;

import java.util.HashMap;
import java.util.Map;

import org.pjp.museum.model.MobileType;
import org.pjp.museum.model.Period;

public class Statistic {

	private final Period period;
	
	private final Map<MobileType, Integer> count = new HashMap<>();

	public Statistic(Period period) {
		super();
		this.period = period;
	}
	
	public Period getPeriod() {
		return period;
	}

	public Map<MobileType, Integer> getCount() {
		return count;
	}

	public int getCount(MobileType mobileType) {
		return count.containsKey(mobileType) ? count.get(mobileType) : 0;
	}

	public int getTotalCount() {
		return count.values().stream().reduce(0, (a, b) -> a + b);
	}

	public void incCount(MobileType mobileType) {
		if (count.containsKey(mobileType)) {
			count.put(mobileType, count.get(mobileType) + 1);
		} else {
			count.put(mobileType, 1);
		}
	}
}
