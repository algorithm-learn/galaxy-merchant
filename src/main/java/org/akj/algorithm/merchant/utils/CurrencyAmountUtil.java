package org.akj.algorithm.merchant.utils;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.akj.algorithm.merchant.entity.CurrencyAmount;
import org.akj.algorithm.merchant.entity.GalaxyCurrencyAmount;

public final class CurrencyAmountUtil {

	public static BigDecimal sum(List<GalaxyCurrencyAmount> currencyAmountList) {
		final List<BigDecimal> currencyAmounts = currencyAmountList.stream().map(curr -> curr.getAmount())
				.collect(Collectors.toList());
		final List<BigDecimal> temp = new ArrayList<BigDecimal>();

		for (int currentIndex = 0; currentIndex < currencyAmounts.size();) {
			BigDecimal currentValue = currencyAmounts.get(currentIndex);
			BigDecimal nextValue = currentIndex + 1 >= currencyAmounts.size() ? BigDecimal.ZERO : currencyAmounts.get(currentIndex + 1);

			if (currentValue.compareTo(nextValue) >= 0) {
				temp.add(currentValue);
				currentIndex++;
			} else {
				temp.add(nextValue.subtract(currentValue));
				currentIndex = currentIndex + 2;
			}
		}

		double sum = temp.stream().mapToDouble(BigDecimal::doubleValue).sum();

		return new BigDecimal(sum);
	}

	public static BigDecimal summ(List<CurrencyAmount> currencyAmountList) {
		final List<BigDecimal> currencyAmounts = currencyAmountList.stream().map(curr -> curr.getAmount())
				.collect(Collectors.toList());
		final List<BigDecimal> temp = new ArrayList<BigDecimal>();

		for (int currentIndex = 0; currentIndex < currencyAmounts.size();) {
			BigDecimal currentValue = currencyAmounts.get(currentIndex);
			BigDecimal nextValue = currentIndex + 1 >= currencyAmounts.size() ? BigDecimal.ZERO : currencyAmounts.get(currentIndex + 1);

			if (currentValue.compareTo(nextValue) >= 0) {
				temp.add(currentValue);
				currentIndex++;
			} else {
				temp.add(nextValue.subtract(currentValue));
				currentIndex = currentIndex + 2;
			}
		}

		double sum = temp.stream().mapToDouble(BigDecimal::doubleValue).sum();

		return new BigDecimal(sum);
	}

	public static BigDecimal calculate(String symbol, List<CurrencyAmount> currencyAmountList,
			List<CurrencyAmount> metalCurrencies) {
		BigDecimal sum = summ(currencyAmountList);

		Optional<CurrencyAmount> temp = metalCurrencies.stream()
				.filter(currency -> currency.getCurrency().getSymbol().equals(symbol)).findFirst();

		if (temp.isEmpty())
			throw new InvalidParameterException("no such metal " + symbol);

		return temp.get().getAmount().multiply(sum);
	}

}