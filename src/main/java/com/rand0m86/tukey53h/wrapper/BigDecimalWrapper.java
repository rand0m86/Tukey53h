package com.rand0m86.tukey53h.wrapper;

import java.math.BigDecimal;

/**
 * Wrapper for {@link BigDecimal}
 * 
 * @author rand0m86
 *
 */
public class BigDecimalWrapper implements NumberWrapper<BigDecimal> {

	@Override
	public BigDecimal multiply(BigDecimal a, BigDecimal b) {
		return a.multiply(b);
	}

	@Override
	public BigDecimal divide(BigDecimal a, BigDecimal b) {
		return a.divide(b);
	}

	@Override
	public BigDecimal valueOf(Number n) {
		return BigDecimal.valueOf(n.doubleValue());
	}

	@Override
	public BigDecimal substract(BigDecimal a, BigDecimal b) {
		return a.subtract(b);
	}

	@Override
	public BigDecimal abs(BigDecimal a) {
		return a.abs();
	}

	@Override
	public BigDecimal add(BigDecimal a, BigDecimal b) {
		return a.add(b);
	}

}
