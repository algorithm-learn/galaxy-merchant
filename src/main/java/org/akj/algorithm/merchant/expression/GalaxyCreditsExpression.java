package org.akj.algorithm.merchant.expression;

import java.math.BigDecimal;
import java.util.List;

import org.akj.algorithm.merchant.entity.CurrencyAmount;
import org.akj.algorithm.merchant.utils.CurrencyAmountUtil;

public class GalaxyCreditsExpression extends StandardExpression{

	public static final int REPET_LIMIT = 3;

	List<CurrencyAmount> currencyAmountList = null;

	String symbol = null;

	List<CurrencyAmount> metalCurrencies = null;

	/**
	 * exprecess for galaxy credit
	 * 
	 * @param symbol
	 * @param transactionAmountInfo
	 * @param metalCurrencies
	 */
	public GalaxyCreditsExpression(String symbol, List<CurrencyAmount> transactionAmountInfo,
			List<CurrencyAmount> metalCurrencies) {
		this.currencyAmountList = transactionAmountInfo;
		this.symbol = symbol;
		this.metalCurrencies = metalCurrencies;
	}

	public BigDecimal getValue() {
		return CurrencyAmountUtil.calculate(symbol, currencyAmountList, metalCurrencies);
	}

	public List<CurrencyAmount> getCurrencyAmountList() {
		return currencyAmountList;
	}

	public void setCurrencyAmountList(List<CurrencyAmount> currencyAmountList) {
		this.currencyAmountList = currencyAmountList;
	}

}
