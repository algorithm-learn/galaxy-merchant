package org.akj.algorithm.merchant.expression;

import java.math.BigDecimal;
import java.util.List;

import org.akj.algorithm.merchant.entity.CurrencySymbol;
import org.akj.algorithm.merchant.entity.GalaxyCurrencyAmount;
import org.akj.algorithm.merchant.utils.CurrencyAmountUtil;

public class MetalExpression {
	public static final int REPET_LIMIT = 3;

	private List<GalaxyCurrencyAmount> galaxyCurrencyAmountList = null;

	private Integer credits;

	private CurrencySymbol symbol;

	public MetalExpression(CurrencySymbol symbol, List<GalaxyCurrencyAmount> galaxyCurrencyAmountList,
			Integer credits) {
		this.symbol = symbol;
		this.galaxyCurrencyAmountList = galaxyCurrencyAmountList;
		this.credits = credits;
	}

	public GalaxyCurrencyAmount getValue() {
		Long sum = CurrencyAmountUtil.sum(galaxyCurrencyAmountList);

		BigDecimal temp = BigDecimal.valueOf(credits).divide(BigDecimal.valueOf(sum));
		
		return new GalaxyCurrencyAmount(symbol, temp);
	}

}
