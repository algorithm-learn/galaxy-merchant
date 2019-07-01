package org.akj.algorithm.merchant.entity;

import java.math.BigDecimal;

//roman currency enum
public enum RomanCurrencyEnum {

	I("I", new BigDecimal(1)), V("V", new BigDecimal(5)), X("X", new BigDecimal(10)), L("L", new BigDecimal(50)),
	C("C", new BigDecimal(100)), D("D", new BigDecimal(500)), M("M", new BigDecimal(1000));

	private String symbol;
	private BigDecimal value;

	RomanCurrencyEnum(String symbol, BigDecimal value) {
		this.symbol = symbol;
		this.value = value;
	}

	public CurrencySymbol getSymbol() {
		return new CurrencySymbol(this.symbol);
	}

	public BigDecimal value() {
		return this.value;
	}
}
