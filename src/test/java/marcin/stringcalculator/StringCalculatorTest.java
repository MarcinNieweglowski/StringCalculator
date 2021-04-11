package marcin.stringcalculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringCalculatorTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private StringCalculator calculator;

	@Before
	public void setUp() {
		this.calculator = new StringCalculator();
	}

	@Test(expected = Test.None.class)
	public void addShouldReturnZeroIfThePassedStringIsNull() throws Exception {
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 0, this.calculator.add(null));
	}

	@Test(expected = Test.None.class)
	public void addShouldReturnZeroIfThePassedStringIsEmpty() throws Exception {
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 0, this.calculator.add(""));
	}

	@Test
	public void addShouldAddASingleNumber() throws Exception {
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 1, this.calculator.add("1"));
	}

	@Test(expected = Test.None.class)
	public void addShouldNotFailAndReturnZeroWhenZeroIsPassedIn() throws Exception {
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 0, this.calculator.add("0"));
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 0, this.calculator.add("0,0,0"));
	}

	@Test
	public void addShouldAddMultipleNumbers() throws Exception {
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 5, this.calculator.add("1,4"));
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 5, this.calculator.add("1,0,4"));
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 15, this.calculator.add("1,5,9"));
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 20, this.calculator.add("1,4,5,10"));
	}

	@Test
	public void addShouldAddNumbersWithNewLines() throws Exception {
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 1, this.calculator.add("1,\n"));
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 3, this.calculator.add("\n1\n2"));
	}

	@Test
	public void addShouldAddNumbersSeparatedBySeparatorsWithDifferentLength() throws Exception {
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 10, this.calculator.add("//%%%;;;;;;**\n1%%%2**3;;;;;;4"));
	}

	@Test
	public void addShouldAddNumbersSeparatedByWords() throws Exception {
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 10, this.calculator.add("//word\n1word2word3word4"));
	}

	@Test(expected = Test.None.class)
	public void addShouldNotFailWhenWhitespaceIsTheSeparator() throws Exception {
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 60, this.calculator.add("//      \n10      20      30"));
	}

	@Test(expected = Test.None.class)
	public void addShouldNotFailWhenNewLineOrTabIsTheSpecifiedSeparator() throws Exception {
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 60, this.calculator.add("//\n\n10\n20\n30"));
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 60, this.calculator.add("//\t\n10\t20\t30"));
	}

	@Test
	public void addShouldAddNumbersWithDifferentSeparators() throws Exception {
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 6, this.calculator.add("//##;;\n1##2;;3"));
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 6, this.calculator.add("//;*\n1;2*3"));
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 41, this.calculator.add("//;* \n15;20*5 1"));
	}

	@Test
	public void addShouldAddNumbersWithSameSeparatorButDifferentLengthUsed() throws Exception {
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 10,
				this.calculator.add("//%%%;;;;;;%%%%%%%%\n1%%%2%%%%%%%%3;;;;;;4"));
	}

	@Test
	public void addShouldThrowIllegalArgumentExceptionIfAtLeastOneNegativeValueWasFound() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("negatives are not allowed: -1");
		this.calculator.add("//\n,\n-1\n2,3");
	}

	@Test
	public void addShouldThrowIllegalArgumentExceptionForMultipleNegativesAndPrintThemAll() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("negatives are not allowed: -1,-2,-3");
		this.calculator.add("//\n,\n-1\n2,-2\n-3,3,55");
	}

	@Test
	public void addShouldFilterNotAddValuesGreaterThan1000() throws Exception {
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 0, this.calculator.add("1001,1002,1003"));
		Assert.assertEquals(EXPECTED_THE_SUM_TO_BE_EQUAL, 1001, this.calculator.add("1,1001,1000"));
	}

	private static final String EXPECTED_THE_SUM_TO_BE_EQUAL = "Expected the sum to be equal";
}
