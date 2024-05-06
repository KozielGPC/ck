package com.github.mauricioaniche.ck;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.github.mauricioaniche.ck.metric.RFC;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RFCTest extends BaseTest {

	@BeforeAll
	public void setUp() {
		report = run(fixturesDir() + "/rfc");
	}

	@Test
	public void countMethodInvocations() {
		CKClassResult a = report.get("rfc.GO");
		Assertions.assertEquals(3, a.getRfc());
	}

	@Test
	public void countSuperInvocations() {
		CKClassResult a = report.get("rfc.GO3");
		Assertions.assertEquals(2, a.getRfc());
	}

	@Test
	public void notPossibleToDifferentiateTypesWithStaticAnalysis() {
		CKClassResult a = report.get("rfc.RFC3");
		Assertions.assertEquals(2, a.getRfc());
	}

	@Test
	public void doesNotCountConstructorInvocations() {
		CKClassResult a = report.get("rfc.RFC2");
		Assertions.assertEquals(0, a.getRfc());
	}

	@Test
	public void methodLevel() {
		CKClassResult a = report.get("rfc.GO");
		Assertions.assertEquals(2, a.getMethod("m1/0").get().getRfc());
		Assertions.assertEquals(0, a.getMethod("m2/1[int]").get().getRfc());
		Assertions.assertEquals(1, a.getMethod("m3/0").get().getRfc());

	}

	@Test
	public void noMethodInvocation() {
		CKClassResult a = report.get("rfc.RFC4");
		Assertions.assertEquals(0, a.getRfc());
	}

	@Test
	public void functionalInterface() {
		CKClassResult a = report.get("rfc.RFC5");
		Assertions.assertEquals(3, a.getRfc());

	}

	@Test
	public void test() {
		CKClassResult b = report.get("rfc.RFC3");
		Optional<CKMethodResult> methodResult = b.getMethod("setResultTest/null");
		Assertions.assertFalse(methodResult.isPresent(), "Method result should be empty");
	}

	@Test
	public void test2() {
		CKClassResult a = report.get("rfc.RFC3");
		Optional<CKMethodResult> methodResult = a.getMethod("setResultTest/null");
		Assertions.assertFalse(methodResult.isPresent(), "Method result should be empty");
	}
}
