package org.akj.algorithm.merchant.entity;

import java.math.BigDecimal;
import java.util.List;

//roman currency amount
public class RomanCurrencyAmount extends CurrencyAmount {

	// additional attr for amount format validation
	private Boolean isRepeatable;

	// additional attr for amount format validation
	private Boolean isSubtractable;

	// additional attr for amount format validation
	private List<RomanCurrencyAmount> subtractableFrom = null;

	// for never repeated and non-substractable currencty
	public RomanCurrencyAmount(CurrencySymbol currency, BigDecimal amount) {
		super(currency, amount);
		this.isRepeatable = false;
		this.isSubtractable = false;
		this.subtractableFrom = null;
	}

	public RomanCurrencyAmount(CurrencySymbol currency, BigDecimal amount, Boolean isRepeatable, Boolean isSubtractable,
			List<RomanCurrencyAmount> subtractableFrom) {
		super(currency, amount);
		this.isRepeatable = isRepeatable;
		this.isSubtractable = isSubtractable;
		this.subtractableFrom = subtractableFrom;
	}

	public Boolean getIsRepeatable() {
		return isRepeatable;
	}

	public void setIsRepeatable(Boolean isRepeatable) {
		this.isRepeatable = isRepeatable;
	}

	public Boolean getIsSubtractable() {
		return isSubtractable;
	}

	public void setIsSubtractable(Boolean isSubtractable) {
		this.isSubtractable = isSubtractable;
	}

	public List<RomanCurrencyAmount> getSubtractableFrom() {
		return subtractableFrom;
	}

	public void setSubtractableFrom(List<RomanCurrencyAmount> subtractableFrom) {
		this.subtractableFrom = subtractableFrom;
	}
}
