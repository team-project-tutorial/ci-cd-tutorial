package io.sparta.service.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.sparta.service.common.page.FixedPageableHandlerMethodArgumentResolver;

@Configuration
public class ArgumentResolverConfig implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new FixedPageableHandlerMethodArgumentResolver());
	}
}
