package org.akj.algorithm.merchant.entity;

import java.math.BigDecimal;

public abstract class CurrencyAmount {
	CurrencySymbol currency;

	BigDecimal amount;

	public CurrencyAmount(CurrencySymbol currency, BigDecimal amount) {
		this.currency = currency;

		this.amount = amount;
	}

	public CurrencySymbol getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencySymbol currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
