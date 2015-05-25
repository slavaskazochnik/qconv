package by.parfen.disptaxi.webapp.etc;

import java.math.BigDecimal;

public final class RatingClass {

	private static final int MIN_RATING = 1;
	private static final int MAX_RATING = 5;

	public static int getMinRating() {
		return MIN_RATING;
	}

	public static Long getMinRatingAsLong() {
		return Long.valueOf(MIN_RATING);
	}

	public static int getMaxRating() {
		return MAX_RATING;
	}

	public static Long getMaxRatingAsLong() {
		return Long.valueOf(MAX_RATING);
	}

	public static int getRatingPercent(BigDecimal rating) {
		int result = 0;
		BigDecimal bdResult = rating == null ? new BigDecimal(0) : rating.divide(new BigDecimal(MAX_RATING)).multiply(
				new BigDecimal(100));
		result = bdResult.intValueExact();
		return result;
	}

	public static int getRatingPercent(Long rating) {
		if (rating != null) {
			return getRatingPercent(new BigDecimal(rating));
		} else {
			return 0;
		}

	}
}
