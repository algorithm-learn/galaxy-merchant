package org.akj.algorithm.merchant.expression;

import java.util.List;

import org.akj.algorithm.merchant.entity.CurrencyAmount;
import org.akj.algorithm.merchant.utils.CurrencyAmountUtil;

public class GalaxyExpression {

	public static final int REPET_LIMIT = 3;

	List<CurrencyAmount> currencyAmountList = null;

	public GalaxyExpression(List<CurrencyAmount> transactionAmountInfo) {
		this.currencyAmountList = transactionAmountInfo;
	}

	public Long getValue() {
		Long sum = CurrencyAmountUtil.summ(currencyAmountList);

		return sum;
	}

	public List<CurrencyAmount> getCurrencyAmountList() {
		return currencyAmountList;
	}

	public void setCurrencyAmountList(List<CurrencyAmount> currencyAmountList) {
		this.currencyAmountList = currencyAmountList;
	}

}
