package org.akj.algorithm.merchant;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.akj.algorithm.merchant.entity.CurrencyAmount;
import org.akj.algorithm.merchant.entity.RomanCurrencyAmount;
import org.akj.algorithm.merchant.entity.RomanCurrencyEnum;
import org.akj.algorithm.merchant.service.TradeService;

/**
 * Galaxy Merchant
 *
 */
public class TradeApplication {

	List<CurrencyAmount> basicCurrencyAmountList = null;

	private TradeService tradeService;

	public TradeApplication(TradeService tradeService) {
		this.tradeService = tradeService;

		this.init();
	}

	private void init() {
		basicCurrencyAmountList = new ArrayList<CurrencyAmount>();

		RomanCurrencyAmount d = new RomanCurrencyAmount(RomanCurrencyEnum.D.getSymbol(), RomanCurrencyEnum.D.value());
		RomanCurrencyAmount l = new RomanCurrencyAmount(RomanCurrencyEnum.L.getSymbol(), RomanCurrencyEnum.L.value());
		RomanCurrencyAmount v = new RomanCurrencyAmount(RomanCurrencyEnum.V.getSymbol(), RomanCurrencyEnum.V.value());
		RomanCurrencyAmount m = new RomanCurrencyAmount(RomanCurrencyEnum.M.getSymbol(), RomanCurrencyEnum.M.value(),
				false, false, null);

		// C" can be subtracted from "D" and "M
		RomanCurrencyAmount c = new RomanCurrencyAmount(RomanCurrencyEnum.C.getSymbol(), RomanCurrencyEnum.C.value(),
				false, false, Arrays.asList(new RomanCurrencyAmount[] { d, m }));

		// "X" can be subtracted from "L" and "C"
		RomanCurrencyAmount x = new RomanCurrencyAmount(RomanCurrencyEnum.X.getSymbol(), RomanCurrencyEnum.X.value(),
				false, false, Arrays.asList(new RomanCurrencyAmount[] { l, c }));

		// "I" can be subtracted from "V" and "X"
		RomanCurrencyAmount i = new RomanCurrencyAmount(RomanCurrencyEnum.I.getSymbol(), RomanCurrencyEnum.I.value(),
				false, false, Arrays.asList(new RomanCurrencyAmount[] { l, c }));

		basicCurrencyAmountList.add(d);
		basicCurrencyAmountList.add(l);
		basicCurrencyAmountList.add(v);
		basicCurrencyAmountList.add(m);
		basicCurrencyAmountList.add(c);
		basicCurrencyAmountList.add(x);
		basicCurrencyAmountList.add(i);
	}

	private List<String> loadInputArgsFromFile(URI uri) throws IOException {
		return Files.readAllLines(Paths.get(uri));
	}

	public void run(URI uri) throws IOException {
		List<String> requests = loadInputArgsFromFile(uri);
		tradeService.startTransaction(requests, getBasicCurrencyAmountList());
	}

	public List<CurrencyAmount> getBasicCurrencyAmountList() {
		return basicCurrencyAmountList;
	}
}
