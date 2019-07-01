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

	public static Long sum(List<GalaxyCurrencyAmount> currencyAmountList) {
		final List<Long> currencyAmounts = currencyAmountList.stream().mapToLong(curr -> curr.getAmount().longValue())
				.boxed().collect(Collectors.toList());
		final List<Long> temp = new ArrayList<Long>();

		for (int currentIndex = 0; currentIndex < currencyAmounts.size();) {
			Long currentValue = currencyAmounts.get(currentIndex);
			Long nextValue = currentIndex + 1 >= currencyAmounts.size() ? 0 : currencyAmounts.get(currentIndex + 1);

			if (currentValue >= nextValue) {
				temp.add(currentValue);
				currentIndex++;
			} else {
				temp.add(nextValue - currentValue);
				currentIndex = currentIndex + 2;
			}
		}

		Long sum = temp.stream().mapToLong(value -> value.longValue()).sum();

		return sum;
	}

	public static Long summ(List<CurrencyAmount> currencyAmountList) {
		final List<Long> currencyAmounts = currencyAmountList.stream().mapToLong(curr -> curr.getAmount().longValue())
				.boxed().collect(Collectors.toList());
		final List<Long> temp = new ArrayList<Long>();

		for (int currentIndex = 0; currentIndex < currencyAmounts.size();) {
			Long currentValue = currencyAmounts.get(currentIndex);
			Long nextValue = currentIndex + 1 >= currencyAmounts.size() ? 0 : currencyAmounts.get(currentIndex + 1);

			if (currentValue >= nextValue) {
				temp.add(currentValue);
				currentIndex++;
			} else {
				temp.add(nextValue - currentValue);
				currentIndex = currentIndex + 2;
			}
		}

		Long sum = temp.stream().mapToLong(value -> value.longValue()).sum();

		return sum;
	}

	public static Long calculate(String symbol, List<CurrencyAmount> currencyAmountList,
			List<CurrencyAmount> metalCurrencies) {
		Long result = 0l;
		Long sum = summ(currencyAmountList);

		Optional<CurrencyAmount> temp = metalCurrencies.stream()
				.filter(currency -> currency.getCurrency().getSymbol().equals(symbol)).findFirst();

		if (temp.isEmpty())
			throw new InvalidParameterException("no such metal " + symbol);

		return temp.get().getAmount().multiply(BigDecimal.valueOf(sum)).longValue();
	}

}