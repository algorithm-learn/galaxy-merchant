package org.akj.algorithm.merchant.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.akj.algorithm.merchant.TradeApplication;
import org.akj.algorithm.merchant.entity.CurrencyAmount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TradeServiceTest {

	private List<String> inputs = null;

	private TradeApplication app = null;
	private TradeService tradeService = null;

	@SuppressWarnings("static-access")
	@BeforeEach
	public void setup() throws IOException, URISyntaxException {
		app = new TradeApplication(tradeService);
		URI uri = this.getClass().getClassLoader().getSystemResource("input.txt").toURI();
		inputs = Files.readAllLines(Paths.get(uri));
		tradeService = new TradeService();
	}

	@Test
	public void testRegex() {
		Assertions.assertEquals(true, "xx is A".matches(".*is [A-Z]"));
	}

	@SuppressWarnings("unchecked")
	@Test
	void testExtractType1InputFromSource() throws Exception {
		Method method = TradeService.class.getDeclaredMethod("extractType1InputFromSource", List.class, List.class);
		method.setAccessible(true);
		List<String> results = (List<String>) method.invoke(tradeService, inputs, app.getBasicCurrencyAmountList());

		Assertions.assertEquals(4, results.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	void testPopulateCurrencyMapping() throws Exception {
		Method method = TradeService.class.getDeclaredMethod("extractType1InputFromSource", List.class, List.class);
		method.setAccessible(true);
		List<String> results = (List<String>) method.invoke(tradeService, inputs, app.getBasicCurrencyAmountList());

		Method method1 = TradeService.class.getDeclaredMethod("populateCurrencyMappingforType1", List.class,
				List.class);
		method1.setAccessible(true);
		List<CurrencyAmount> currencies = (List<CurrencyAmount>) method1.invoke(tradeService, results,
				app.getBasicCurrencyAmountList());

		long cnt = currencies.stream().filter(item -> item.getCurrency().getSymbol().equals("glob")).count();

		Assertions.assertEquals(1, cnt);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testExtractType2InputFromSource() throws Exception {
		Method method = TradeService.class.getDeclaredMethod("extractType1InputFromSource", List.class, List.class);
		method.setAccessible(true);
		List<String> results = (List<String>) method.invoke(tradeService, inputs, app.getBasicCurrencyAmountList());

		Method method1 = TradeService.class.getDeclaredMethod("extractType2InputFromSource", List.class);
		method1.setAccessible(true);
		inputs.removeAll(results);
		List<String> type2Inputs = (List<String>) method1.invoke(tradeService, inputs);

		Assertions.assertEquals(3, type2Inputs.size());

	}

	@Test
	@SuppressWarnings("unchecked")
	void testPopulateCurrencyMappingforType2() throws Exception {

		Method method1 = TradeService.class.getDeclaredMethod("populateCurrencyMappingforType1", List.class,
				List.class);
		method1.setAccessible(true);
		List<CurrencyAmount> currencies = (List<CurrencyAmount>) method1.invoke(tradeService, inputs.subList(0, 4),
				app.getBasicCurrencyAmountList());

		Method method2 = TradeService.class.getDeclaredMethod("populateCurrencyMappingforType2", List.class, List.class,
				List.class);
		method2.setAccessible(true);
		List<CurrencyAmount> metalCurrencies = (List<CurrencyAmount>) method2.invoke(tradeService, inputs.subList(4, 7),
				app.getBasicCurrencyAmountList(), currencies);

		Assertions.assertEquals(17, metalCurrencies.get(0).getAmount().intValue());
	}

	@Test
	@SuppressWarnings("unchecked")
	void testExtractType3InputFromSource() throws Exception {
		Method method1 = TradeService.class.getDeclaredMethod("extractType3InputFromSource", List.class);
		method1.setAccessible(true);
		List<String> result = (List<String>) method1.invoke(tradeService, inputs.subList(7, 12));

		Assertions.assertTrue(result.size() == 1);
	}

	@Test
	@SuppressWarnings("unchecked")
	void testHandleType3() throws Exception {
		Method method1 = TradeService.class.getDeclaredMethod("populateCurrencyMappingforType1", List.class,
				List.class);
		method1.setAccessible(true);
		List<CurrencyAmount> currencies = (List<CurrencyAmount>) method1.invoke(tradeService, inputs.subList(0, 4),
				app.getBasicCurrencyAmountList());

		Method method = TradeService.class.getDeclaredMethod("handleType3", List.class, List.class);
		method.setAccessible(true);
		List<String> result = (List<String>) method.invoke(tradeService, inputs.subList(7, 8), currencies);

		Assertions.assertEquals(result.size(), 1);

		Assertions.assertTrue(result.get(0).matches(".* is \\d+"));
	}

	@Test
	@SuppressWarnings("unchecked")
	void testExtractTypy4InputFromSource() throws Exception {
		Method method1 = TradeService.class.getDeclaredMethod("extractType4InputFromSource", List.class);
		method1.setAccessible(true);
		List<String> result = (List<String>) method1.invoke(tradeService, inputs.subList(8, 12));

		Assertions.assertTrue(result.size() == 3);
	}

	@Test
	@SuppressWarnings("unchecked")
	void testHandleType4() throws Exception {
		Method method1 = TradeService.class.getDeclaredMethod("populateCurrencyMappingforType1", List.class,
				List.class);
		method1.setAccessible(true);
		List<CurrencyAmount> currencies = (List<CurrencyAmount>) method1.invoke(tradeService, inputs.subList(0, 4),
				app.getBasicCurrencyAmountList());
		Assertions.assertEquals(4, currencies.size());

		Method method2 = TradeService.class.getDeclaredMethod("populateCurrencyMappingforType2", List.class, List.class,
				List.class);
		method2.setAccessible(true);
		List<CurrencyAmount> metalCurrencies = (List<CurrencyAmount>) method2.invoke(tradeService, inputs.subList(4, 7),
				app.getBasicCurrencyAmountList(), currencies);
		Assertions.assertEquals(17, metalCurrencies.get(0).getAmount().intValue());

		Method method = TradeService.class.getDeclaredMethod("handleType4", List.class, List.class, List.class);
		method.setAccessible(true);
		List<String> result = (List<String>) method.invoke(tradeService, inputs.subList(8, 11), currencies,
				metalCurrencies);

		Assertions.assertTrue(result.size() == 3);

		Assertions.assertTrue(result.get(0).matches(".* is \\d+"));
	}

}
