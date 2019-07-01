package org.akj.algorithm.merchant.entity;

import java.math.BigDecimal;

/**
 * Currency amount for galaxy, goods
 * 
 * @author jamie
 *
 */
public class GalaxyCurrencyAmount extends CurrencyAmount {

	public GalaxyCurrencyAmount(CurrencySymbol currency, BigDecimal amount) {
		super(currency, amount);
	}
}
