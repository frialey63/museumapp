package org.pjp.museum.ui.bean;

import java.util.HashMap;
import java.util.Map;

import org.pjp.museum.model.Period;

public class Statistic<T> {

	private final Period period;
	
	private final Map<T, Integer> count = new HashMap<>();

	public Statistic(Period period) {
		super();
		this.period = period;
	}
	
	public Period getPeriod() {
		return period;
	}

	public Map<T, Integer> getCount() {
		return count;
	}

	public int getCount(T type) {
		return count.containsKey(type) ? count.get(type) : 0;
	}

	public int getTotalCount(T excludeKey) {
		Map<T, Integer> tempCount = new HashMap<>(count);
		
		if (excludeKey != null) {
			tempCount.remove(excludeKey);
		}
		
		return tempCount.values().stream().reduce(0, (a, b) -> a + b);
	}

	public void incCount(T type) {
		if (count.containsKey(type)) {
			count.put(type, count.get(type) + 1);
		} else {
			count.put(type, 1);
		}
	}
}
