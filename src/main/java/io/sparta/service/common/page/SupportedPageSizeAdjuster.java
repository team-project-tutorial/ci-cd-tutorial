package io.sparta.service.common.page;

public class SupportedPageSizeAdjuster {
	private static final int MAX_PAGE_SIZE = 1000;
	private static final String OVER_THAN_MAX_PAGE_ERROR_MESSAGE = """
		요청하신 페이지 크기(%d)가 허용 범위를 초과했습니다.
		최대 %d건 이하로 다시 요청해 주세요.
		""";

	public static int adjustPageSize(int pageSize) {
		if (isNotSupportedPageSize(pageSize)) {
			throw new IllegalArgumentException(OVER_THAN_MAX_PAGE_ERROR_MESSAGE.formatted(pageSize, MAX_PAGE_SIZE));
		}
		return pageSize;
	}

	private static boolean isNotSupportedPageSize(int pageSize) {
		return pageSize > MAX_PAGE_SIZE;
	}
}
