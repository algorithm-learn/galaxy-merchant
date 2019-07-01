package org.akj.algorithm.merchant.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.akj.algorithm.merchant.constant.Constant;
import org.akj.algorithm.merchant.entity.CurrencyAmount;
import org.akj.algorithm.merchant.entity.CurrencySymbol;
import org.akj.algorithm.merchant.entity.GalaxyCurrencyAmount;
import org.akj.algorithm.merchant.expression.GalaxyCreditsExpression;
import org.akj.algorithm.merchant.expression.GalaxyExpression;
import org.akj.algorithm.merchant.expression.MetalExpression;
import org.akj.algorithm.merchant.utils.ConsoleUtil;

public class TradeService {

	public void startTransaction(List<String> requests, List<CurrencyAmount> currencyList) {

		// type 1: xx is [A-Z]
		List<String> type1Inputs = extractType1InputFromSource(requests, currencyList);
		List<CurrencyAmount> galaxyCurrencies = populateCurrencyMappingforType1(type1Inputs, currencyList);

		// type2: xx Credits
		Iterator<String> iterator = requests.iterator();
		List<String> requestsWithoutType1 = new ArrayList<String>();
		while (iterator.hasNext()) {
			requestsWithoutType1.add(iterator.next());
		}
		requestsWithoutType1.removeAll(type1Inputs);

		List<String> type2Inputs = extractType2InputFromSource(requestsWithoutType1);
		List<CurrencyAmount> metalCurrencies = populateCurrencyMappingforType2(type2Inputs, currencyList,
				galaxyCurrencies);

		// type3: how much xxx?
		requestsWithoutType1.removeAll(type2Inputs);
		List<String> type3Inputs = extractType3InputFromSource(requestsWithoutType1);
		List<String> type3Response = handleType3(type3Inputs, galaxyCurrencies);
		type3Response.stream().forEach(message -> ConsoleUtil.print(message));

		// type4: how many Credits is xxx?
		requestsWithoutType1.removeAll(type3Inputs);
		List<String> type4Inputs = extractTypy4InputFromSource(requestsWithoutType1);
		List<String> type4Response = handleType4(type4Inputs, galaxyCurrencies, metalCurrencies);
		type4Response.stream().forEach(message -> ConsoleUtil.print(message));

		// type5: unrecognized input
		requestsWithoutType1.removeAll(type4Inputs);
		if(!requestsWithoutType1.isEmpty()) {
			requestsWithoutType1.stream().forEach(message -> ConsoleUtil.print(Constant.UNRECOGNIZED_INPUT_MESSAGE));
		}
	}

	private List<String> handleType4(List<String> type4Inputs, List<CurrencyAmount> galaxyCurrencies,
			List<CurrencyAmount> metalCurrencies) {
		List<String> response = new ArrayList<String>();

		for (String txn : type4Inputs) {
			String[] txnDetails = txn.split(" ");
			final Stream<String> stream = Arrays.asList(txnDetails).stream().filter(item -> {
				return galaxyCurrencies.stream().anyMatch(currency -> currency.getCurrency().getSymbol().equals(item));
			});

			List<CurrencyAmount> transactionAmountInfo = stream.map(item -> {
				return galaxyCurrencies.stream().filter(currency -> currency.getCurrency().getSymbol().equals(item))
						.findFirst().get();
			}).collect(Collectors.toList());

			String metalSymbol = txnDetails[txnDetails.length - 2];

			GalaxyCreditsExpression expression = new GalaxyCreditsExpression(metalSymbol, transactionAmountInfo,
					metalCurrencies);
			Long result = expression.getValue();

			List<String> collect = Arrays.asList(txnDetails).stream().filter(item -> {
				return galaxyCurrencies.stream().anyMatch(currency -> currency.getCurrency().getSymbol().equals(item));
			}).collect(Collectors.toList());

			collect.add(metalSymbol);
			collect.add("is");
			collect.add(result.toString());

			String message = constructResponseMessage(collect);
			response.add(message);
		}
		return response;
	}

	private List<String> extractTypy4InputFromSource(List<String> requestsWithoutType1) {
		return requestsWithoutType1.stream().filter(item -> item.startsWith("how many")).collect(Collectors.toList());
	}

	private List<String> handleType3(List<String> type3Inputs, List<CurrencyAmount> galaxyCurrencies) {
		List<String> response = new ArrayList<String>();
		for (String txn : type3Inputs) {
			String[] txnDetails = txn.split(" ");
			final Stream<String> tempStream = Arrays.asList(txnDetails).stream().filter(item -> {
				return galaxyCurrencies.stream().anyMatch(currency -> currency.getCurrency().getSymbol().equals(item));
			});

			final List<CurrencyAmount> transactionAmountInfo = tempStream.map(item -> {
				return galaxyCurrencies.stream().filter(currency -> currency.getCurrency().getSymbol().equals(item))
						.findFirst().get();
			}).collect(Collectors.toList());

			Long result = new GalaxyExpression(transactionAmountInfo).getValue();

			// prepare output string
			List<String> collect = Arrays.asList(txnDetails).stream().filter(item -> {
				return galaxyCurrencies.stream().anyMatch(currency -> currency.getCurrency().getSymbol().equals(item));
			}).collect(Collectors.toList());
			collect.add("is");
			collect.add(result.toString());

			String message = constructResponseMessage(collect);

			response.add(message.toString().trim());
		}

		return response;
	}

	private String constructResponseMessage(List<String> collect) {
		StringBuffer message = new StringBuffer();
		collect.forEach(item -> message.append(item).append(" "));
		return message.toString().trim();
	}

	private List<String> extractType3InputFromSource(List<String> requests) {
		return requests.stream().filter(s -> s.startsWith("how much is")).collect(Collectors.toList());
	}

	// extract metal and calculate it's value
	@SuppressWarnings("unchecked")
	private List<CurrencyAmount> populateCurrencyMappingforType2(List<String> type2Inputs,
			List<CurrencyAmount> basicRomanCurrencyList, List<CurrencyAmount> galaxyCurrencyList) {

		List<CurrencyAmount> metalCurrencies = new ArrayList<CurrencyAmount>();
		for (String txn : type2Inputs) {
			String[] txnDetails = txn.split(" ");
			List<String> galaxyGoods = Arrays.asList(txnDetails).stream().filter(item -> {
				Optional<CurrencyAmount> matchedCurrency = galaxyCurrencyList.stream()
						.filter(currency -> currency.getCurrency().getSymbol().equals(item.trim())).findAny();
				return matchedCurrency.isPresent();
			}).collect(Collectors.toList());

			// map good to galaxyCurrency which got in type1
			List<GalaxyCurrencyAmount> galaxyCurrency = galaxyGoods.stream().map(good -> {
				Optional<CurrencyAmount> matchedCurrency = galaxyCurrencyList.stream()
						.filter(currency -> currency.getCurrency().getSymbol().equals(good.trim())).findFirst();

				return new GalaxyCurrencyAmount(new CurrencySymbol(good), matchedCurrency.get().getAmount());
			}).collect(Collectors.toList());

			// get credits
			Integer credits = Integer.valueOf(txnDetails[txnDetails.length - 2]);

			// get symbol
			String symbol = txnDetails[2];

			MetalExpression metalExpression = new MetalExpression(new CurrencySymbol(symbol), galaxyCurrency, credits);

			metalCurrencies.add(metalExpression.getValue());
		}

		return metalCurrencies;
	}

	private List<CurrencyAmount> populateCurrencyMappingforType1(List<String> type1Inputs,
			List<CurrencyAmount> currencyList) {
		List<CurrencyAmount> results = type1Inputs.stream().map(itemStr -> {
			final String[] temp = itemStr.split(" ");
			final String galaxySymbol = temp[0];
			final String romanCurrencySynbol = temp[2];
			final Optional<CurrencyAmount> matchedCurrency = currencyList.stream()
					.filter(currency -> currency.getCurrency().getSymbol().equalsIgnoreCase(romanCurrencySynbol))
					.findAny();

			if (matchedCurrency.isPresent()) {
				return new GalaxyCurrencyAmount(new CurrencySymbol(galaxySymbol), matchedCurrency.get().getAmount());
			} else {
				throw new InvalidParameterException("invalid input");
			}
		}).collect(Collectors.toList());

		return results;
	}

	private List<String> extractType1InputFromSource(List<String> requests, List<CurrencyAmount> currencyList) {
		List<String> type1Inputs = requests.stream().filter(itemStr -> {
			if (itemStr.trim().matches(".*is [A-Z]")) {
				final String symbol = itemStr.split(" ")[2];
				final Optional<CurrencyAmount> matchedCurrency = currencyList.stream()
						.filter(currency -> currency.getCurrency().getSymbol().equalsIgnoreCase(symbol)).findAny();
				return matchedCurrency.isPresent();
			}

			return false;
		}).collect(Collectors.toList());

		return type1Inputs;
	}

	private List<String> extractType2InputFromSource(List<String> requests) {
		List<String> type1Inputs = requests.stream().filter(itemStr -> {
			return itemStr.trim().endsWith("Credits") ? true : false;
		}).collect(Collectors.toList());

		return type1Inputs;
	}

}
