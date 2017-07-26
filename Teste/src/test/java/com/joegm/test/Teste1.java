package com.joegm.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.joegm.CharStream;
import com.joegm.RetornaVogal;
import com.joegm.exception.CharNotFoundException;

public class Teste1 {

	

	@Before
	public void beforeEachTest() {
		System.out.println("##################### Iniciando Teste");
	}

	@After
	public void afterEachTest() {
		System.out.println("##################### Finalizando Teste\r\n");
	}

	@Test
	public void teste01() {

		try {

			char c = RetornaVogal.getInstance().firstChar(new CharStream("aAbBABacafesddeffduausodí"));

			Assert.assertEquals(c, 'o');
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void teste02() {
		System.out.println("teste02 com exception");
		try {
			char c = RetornaVogal.getInstance().firstChar(new CharStream("aAbBABacafesddeffduausooodíiii"));
			Assert.fail("deveria dar exception");
		} catch (Exception e) {
			Assert.assertTrue(true);
		}

	}

	@Test
	public void tste03() {
		long l = System.currentTimeMillis();
		System.out.println("teste02 com concorrencia");
		int numeroMaximoDeThreads = 100;
		int numeroDeLoops = 2000;
		ExecutorService executorService = new ThreadPoolExecutor(numeroMaximoDeThreads, numeroMaximoDeThreads, 1,
				TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		for (int i = 0; i < numeroDeLoops; i++) {
			executorService.execute(new Runnable() {

				@Override
				public void run() {
					try {
						RetornaVogal.getInstance().firstChar(new CharStream("aAbBABacafesddeffduausodí"));
					} catch (IllegalArgumentException | CharNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		}
		long l2 = System.currentTimeMillis() - l;
		executorService.shutdown();
		while (!executorService.isTerminated()) {

		}
		System.out.println(
				"teste02 com concorrencia de " + 100 + " Threads em loop de " + numeroDeLoops + " tempo: " + l2 + "ms");

	}
}
