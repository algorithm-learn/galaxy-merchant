package org.akj.algorithm.merchant.expression;

import java.math.BigDecimal;
import java.util.List;

import org.akj.algorithm.merchant.entity.CurrencyAmount;
import org.akj.algorithm.merchant.utils.CurrencyAmountUtil;

public abstract class StandardExpression {
	public abstract BigDecimal getValue();
}