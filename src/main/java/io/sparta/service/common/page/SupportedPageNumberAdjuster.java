package io.sparta.service.common.page;

import static org.hibernate.type.descriptor.java.IntegerJavaType.*;

public class SupportedPageNumberAdjuster {
	public static int adjustStartPageNumber(int startPageNumber) {
		if (isStartPageOverThanZero(startPageNumber)) {
			return startPageNumber - 1;
		}
		return ZERO;
	}

	private static boolean isStartPageOverThanZero(int startPageNumber) {
		return startPageNumber > ZERO;
	}
}
