package org.akj.algorithm.merchant.expression;

import java.math.BigDecimal;
import java.util.List;

import org.akj.algorithm.merchant.entity.CurrencyAmount;
import org.akj.algorithm.merchant.utils.CurrencyAmountUtil;

public class GalaxyExpression  extends StandardExpression {

	public static final int REPET_LIMIT = 3;

	List<CurrencyAmount> currencyAmountList = null;

	public GalaxyExpression(List<CurrencyAmount> transactionAmountInfo) {
		this.currencyAmountList = transactionAmountInfo;
	}

	public BigDecimal getValue() {
		BigDecimal sum = CurrencyAmountUtil.summ(currencyAmountList);

		return sum;
	}

	public List<CurrencyAmount> getCurrencyAmountList() {
		return currencyAmountList;
	}

	public void setCurrencyAmountList(List<CurrencyAmount> currencyAmountList) {
		this.currencyAmountList = currencyAmountList;
	}

}
