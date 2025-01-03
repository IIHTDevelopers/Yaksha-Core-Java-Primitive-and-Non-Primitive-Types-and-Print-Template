package com.iiht.evaluation.yaksha.piglatin;

import static com.iiht.evaluation.yaksha.testutils.TestUtils.boundaryTestFile;
import static com.iiht.evaluation.yaksha.testutils.TestUtils.businessTestFile;
import static com.iiht.evaluation.yaksha.testutils.TestUtils.currentTest;
import static com.iiht.evaluation.yaksha.testutils.TestUtils.exceptionTestFile;
import static com.iiht.evaluation.yaksha.testutils.TestUtils.testReport;
import static com.iiht.evaluation.yaksha.testutils.TestUtils.yakshaAssert;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.iiht.evaluation.yaksha.piglatin.MyApp;
public class ConvertToPiglatinTest {
	@AfterAll
	public static void afterAll() {
		testReport();
	}
	
	@Test
	public void testGetPiglatin() throws Exception {
		MyApp obj = new MyApp();
		String str = "This is a string";
		String piglatin = obj.getPiglatin(str);
		yakshaAssert(currentTest(), piglatin.contentEquals("hisTa sia aa tringsa")? true:false, businessTestFile);
	}
	
	@Test
	public void testGetPiglatinForMinValue() throws Exception {
		MyApp obj = new MyApp();
		String str = "a";
		String piglatin = obj.getPiglatin(str);
		yakshaAssert(currentTest(), piglatin.contentEquals("aa")? true:false, businessTestFile);
	}
	
	@Test
	public void testGetPiglatinForMaxValue() throws Exception {
		MyApp obj = new MyApp();
		String str = "THIS";
		String piglatin = obj.getPiglatin(str);
		yakshaAssert(currentTest(), piglatin.contentEquals("HISTa")? true:false, businessTestFile);
	}
	
	@Test
	public void testGetPiglatinForNoValue() throws Exception {
		MyApp obj = new MyApp();
		String str = "";
		String piglatin = obj.getPiglatin(str);
		yakshaAssert(currentTest(), piglatin.contentEquals("")? true:false, boundaryTestFile);
	}
	
	@Test
	public void testGetPiglatinForExceptionalValue() throws Exception {
		MyApp obj = new MyApp();
		String str = "  This is  a string ";
		String piglatin = obj.getPiglatin(str);
		yakshaAssert(currentTest(), piglatin.contentEquals("  hisTa sia  aa tringsa ")? true:false, exceptionTestFile);
	}
	
	
}
