package com.rand0m86.tukey53h;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.rand0m86.tukey53h.wrapper.NumberWrapper;

/**
 * Implementation of algorithm named Tukey53h. It's used for filtration of data but it causes some data loss.
 * This data loss is 8 elements long. First 4 elements are lost in the first iteration over data, next 2
 * elements are lost in the second iteration and 2 elements are lost while using Hanning filter.
 *   
 * @author rand0m86
 *
 * @param <T> any {@link Number} that implements {@link Comparable} interface.
 */
public final class Tukey53h <T extends Number & Comparable<T>> {

	private static final Integer TWO = 2;
	private static final Integer FOUR = 4;
	private static final Integer MIN_DATA_LENGTH = 8;
	private final List<T> rawData;
	private final NumberWrapper<T> wrapper;
	private final Double k;
	
	private List<T> firstIteration;
	private List<T> secondIteration;
	private List<T> hanningFiltered;
	
	/**
	 * Hidden constructor.
	 * @param data - list of any numbers
	 * @param wrapper - helper class that can manipulate user numbers
	 * @param k - filtering coefficient
	 */
	private Tukey53h(List<T> data, NumberWrapper<T> wrapper, Double k) {
		this.rawData = data;
		this.wrapper = wrapper;
		this.k = k;
	}
	
	/**
	 * Filters given data with Tukey53h method.
	 * @param data - list of any numbers
	 * @param wrapper - helper class that can manipulate user numbers
	 * @param k - filtering coefficient
	 * @return filtered data
	 */
	public static <T extends Number & Comparable<T>> List<T> filter (List<T> data, NumberWrapper<T> wrapper, Double k) {
		if (null == data || data.size() < MIN_DATA_LENGTH) {
			return data;
		}
		return new Tukey53h<T>(data, wrapper, k).filterInternal(); 
	}
	
	/**
	 * Aggregates all logic of data filtering.
	 * @return filtered data
	 */
	private List<T> filterInternal() {
		firstIteration = siftData(rawData, 5);
		secondIteration = siftData(firstIteration, 3);
		hanningFiltered = filterHanning();
		return filterTukey();
	}
	
	/**
	 * Sifts data with specified window size. Causes data loss that is equal to
	 * <pre>windowSize - 1</pre>
	 * 
	 * @param data - data to be sifted
	 * @param windowSize - size of sifting window
	 * @return sifted data
	 */
	private List<T> siftData(List<T> data, Integer windowSize) {
		List<T> result = new ArrayList<T>(data.size());
		LinkedList<T> window = new LinkedList<T>();
		Integer middleWindowIndex = (windowSize - 1) / 2;
		Iterator<T> dataIterator = data.iterator();
		
		while (dataIterator.hasNext()) {
			if (window.size() == windowSize) {
				ArrayList<T> windowCopy = new ArrayList<T>(window);
				Collections.sort(windowCopy);
				result.add(windowCopy.get(middleWindowIndex));
			}
			window.add(dataIterator.next());
			
			if (window.size() > windowSize) {
				window.removeFirst();
			}
			// last element
			if (!dataIterator.hasNext()) {
				Collections.sort(window);
				result.add(window.get(middleWindowIndex));
			}
		}
		return result;
	}
	
	/**
	 * Implementation of Hanning filter:
	 * <pre>
	 * 	element[i] = 0.25 * rawData[i - 1] + 0.5 * rawData[i] + 0.25 * rawData[i + 1]
	 * </pre>
	 * @return data filtered with Hanning filter
	 */
	private List<T> filterHanning() {
		List<T> result = new ArrayList<T>(secondIteration.size());
		ListIterator<T> listIterator = secondIteration.listIterator();
		
		while (listIterator.hasNext()) {
			T current;
			T firstSummand;
			T secondSummand;
			T thirdSummand;
			
			if (!listIterator.hasPrevious()) {
				listIterator.next();
				continue;
			}
			firstSummand = wrapper.divide(listIterator.previous(), wrapper.valueOf(FOUR));
			listIterator.next(); // move cursor back

			current = listIterator.next();
			secondSummand = wrapper.divide(current, wrapper.valueOf(TWO));
			
			if (!listIterator.hasNext()) {
				break;
			}
			thirdSummand = wrapper.divide(listIterator.next(), wrapper.valueOf(FOUR));
			listIterator.previous(); // move cursor back
			
			T sum = wrapper.add(firstSummand, secondSummand);
			sum = wrapper.add(sum, thirdSummand);
			result.add(sum);
		}
		return result;
	}
	
	/**
	 * Last iteration in Tukey53h algorithm. 
	 * @return filtered data.
	 */
	private List<T> filterTukey() {
		Iterator<T> henningIterator = hanningFiltered.iterator();		
		List<T> rawDataTrimmed = rawData.subList(MIN_DATA_LENGTH, rawData.size());
		
		Iterator<T> rawDataTrimmedIterator = rawDataTrimmed.iterator();
		List<T> result = new ArrayList<T>(hanningFiltered.size());
		T kWrapped = wrapper.valueOf(k);
		
		for (; henningIterator.hasNext() && rawDataTrimmedIterator.hasNext();) {
			T henningValue = henningIterator.next();
			T originalValue = rawDataTrimmedIterator.next();
			
			if (wrapper.abs(wrapper.substract(originalValue, henningValue))
					.compareTo(kWrapped) > 0) {
				result.add(henningValue);				
			} else {
				result.add(originalValue);
			}
		}
		return result;
	}
}
