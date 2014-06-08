package com.rand0m86.tukey53h.wrapper;

/**
 * Wrapper for {@link Double}
 * 
 * @author rand0m86
 *
 */
public class DoubleWrapper implements NumberWrapper<Double> {

	@Override
	public Double add(Double a, Double b) {
		return a + b;
	}

	@Override
	public Double substract(Double a, Double b) {
		return a - b;
	}

	@Override
	public Double multiply(Double a, Double b) {
		return a * b;
	}

	@Override
	public Double divide(Double a, Double b) {
		return a / b;
	}

	@Override
	public Double abs(Double a) {
		return a > 0 ? a : -a;
	}

	@Override
	public Double valueOf(Number n) {
		return n.doubleValue();
	}

}
