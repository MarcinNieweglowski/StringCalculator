package marcin.stringcalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author marcin nieweglowski
 */
public class StringCalculator {

	/**
	 * The main method, which initiates the add method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		StringCalculator calculator = new StringCalculator();

		String text = "//%%%;;;;;;**\n1%%%2**3;;;;;;4";
		int result = calculator.add(text);
		LOGGER.info("Result of add method: {}", result);
	}

	/**
	 * Performs the add method on the given text. Parses the String retrieving the
	 * stored values, validates them and eventually sums all the integers.
	 *
	 * @param numbers {@code String} the numbers as text.
	 * @return {@code int} the sum of validated numbers stored within the given
	 *         text.
	 */
	public int add(String numbers) {
		if (isEmpty(numbers)) {
			LOGGER.debug("The passed string is empty, returning default value: 0");
			return 0;
		}

		List<Integer> list = getDigitsFromText(numbers);
		validateForNegativeValues(list);
		return calcuateSumOfValidNumbers(list);
	}

	private int calcuateSumOfValidNumbers(List<Integer> list) {
		LOGGER.debug("Calculating the sum, values above 1000 are considered invalid");
		return IntStream.range(0, list.size()).map(index -> list.get(index)).filter(value -> value < MAX_ALLOWED_VALUE)
				.sum();
	}

	private void validateForNegativeValues(List<Integer> list) {
		List<Integer> negatives = list.stream().filter(value -> value < 0).collect(Collectors.toList());

		if (negatives.isEmpty()) {
			LOGGER.debug("Validated the list of integers - no negative values found");
			return;
		}

		String message = negatives.stream().map(String::valueOf).collect(Collectors.joining(DEFAULT_SEPARATOR));
		LOGGER.error("Invalid input text - negative values found: '{}'", message);
		throw new IllegalArgumentException(String.format("negatives are not allowed: %s", message));
	}

	private List<Integer> getDigitsFromText(String numbers) {
		Matcher matcher = FIND_NUMBERS_PATTERN.matcher(numbers);
		List<Integer> values = new ArrayList<>();

		while (matcher.find()) {
			values.add(Integer.parseInt(matcher.group()));
		}

		LOGGER.debug("Found {} values from the text", values.size());
		return values;
	}

	private boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	private static final int MAX_ALLOWED_VALUE = 1001;

	private static final String DEFAULT_SEPARATOR = ",";

	private static final String FIND_NUMBERS_REGEX = "(-?[\\d]+)";

	private static final Pattern FIND_NUMBERS_PATTERN = Pattern.compile(FIND_NUMBERS_REGEX);

	private static final Logger LOGGER = LogManager.getLogger(StringCalculator.class);
}
