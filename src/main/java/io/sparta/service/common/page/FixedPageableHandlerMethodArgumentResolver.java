package io.sparta.service.common.page;

import static io.sparta.service.common.page.SupportedPageNumberAdjuster.*;
import static io.sparta.service.common.page.SupportedPageSizeAdjuster.*;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public class FixedPageableHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolver {

	public FixedPageableHandlerMethodArgumentResolver() {
		super(new SortHandlerMethodArgumentResolver());
	}

	@Override
	public Pageable resolveArgument(MethodParameter methodParameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {
		Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

		int reAdjustedStartPageNumber = adjustStartPageNumber(pageable.getPageNumber());
		int pageSize = adjustPageSize(pageable.getPageSize());

		return PageRequest.of(reAdjustedStartPageNumber, pageSize, pageable.getSort());
	}

}
