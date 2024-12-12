package com.example.api_gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.api_gateway.filter.util.FilterUtils;

import reactor.core.publisher.Mono;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

	private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
		
		if(isRequestIdPresent(requestHeaders)) {
			logger.info("x-request-id found: {}", FilterUtils.getRequestId(requestHeaders));
		} else {
			String requestId = generateRequestId();
			exchange = FilterUtils.setRequestId(exchange, requestId);
			logger.info("Generated x-request-id: {}", requestId);
		}
		
		return chain.filter(exchange);
	}
	
    private boolean isRequestIdPresent(HttpHeaders requestHeaders) {
        if (FilterUtils.getRequestId(requestHeaders) != null) {
            return true;
        } else {
            return false;
        }
    }

    private String generateRequestId() {
    	return java.util.UUID.randomUUID().toString();
    }
}
