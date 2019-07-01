package org.akj.algorithm.merchant;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.akj.algorithm.merchant.service.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for TradeAppplication.
 */
public class TradeAppplicationTest {
	private TradeApplication app = null;

	@BeforeEach
	public void setup() {
		app = new TradeApplication(new TradeService());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testRun() throws URISyntaxException, IOException {
		URI uri = this.getClass().getClassLoader().getSystemResource("input.txt").toURI();

		app.run(uri);
	}
}
