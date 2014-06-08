package com.rand0m86.tukey53h.wrapper;

/**
 * Wrapper for {@link Integer}
 * 
 * @author rand0m86
 *
 */
public class IntegerWrapper implements NumberWrapper<Integer> {

	@Override
	public Integer multiply(Integer a, Integer b) {
		return a * b;
	}

	@Override
	public Integer divide(Integer a, Integer b) {
		return a / b;
	}

	@Override
	public Integer valueOf(Number n) {
		return n.intValue();
	}

	@Override
	public Integer substract(Integer a, Integer b) {
		return a - b;
	}

	@Override
	public Integer abs(Integer a) {
		return a > 0 ? a : -a;
	}

	@Override
	public Integer add(Integer a, Integer b) {
		return a + b;
	}

}
