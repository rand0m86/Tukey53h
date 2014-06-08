package com.rand0m86.tukey53h.wrapper;

/**
 * Wrapper interface that helps to manipulate custom data.
 * @author rand0m86
 *
 * @param <T> - any {@link Number} that implements {@link Comparable}
 */
public interface NumberWrapper <T extends Number & Comparable<T>> {
	
	/**
	 * Adds two numbers. 
	 * @param a - augend
	 * @param b - addend
	 * @return sum
	 */
	T add(T a, T b);
	
	/**
	 * Substracts two numbers.
	 * @param a - minuend
	 * @param b - subtrahend
	 * @return difference
	 */
	T substract(T a, T b);
	
	/**
	 * Multiplies two numbers.
	 * @param a - multiplicand
	 * @param b - multiplier
	 * @return product
	 */
	T multiply(T a, T b);
	
	/**
	 * Divides two numbers.
	 * @param a - dividend
	 * @param b - divisor
	 * @return quotient
	 */
	T divide(T a, T b);
	
	/**
	 * Returns absolute value of the given number.
	 * @param a - number to be checked.
	 * @return absolute value
	 */
	T abs(T a);
	
	/**
	 * Creates wrapped value by given number.
	 * @param n - number to be wrapped
	 * @return wrapped value
	 */
	T valueOf(Number n);
}
