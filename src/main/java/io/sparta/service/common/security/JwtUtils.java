package io.sparta.service.common.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
	@Value("${jwt.secret-key}")
	private String secretKey;
}
