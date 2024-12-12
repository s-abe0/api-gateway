package com.example.api_gateway.filter.util;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

public class FilterUtils {
	private static final String X_REQUEST_ID = "X-REQUEST-ID";
	
	public static String getRequestId(HttpHeaders headers) {
		if(headers.get(X_REQUEST_ID) != null) {
			return headers.get(X_REQUEST_ID).stream().findFirst().get();
		} else {
			return null;
		}
	}
	
	public static ServerWebExchange setRequestId(ServerWebExchange exchange, String value) {
		return exchange.mutate().request(exchange.getRequest().mutate().header(X_REQUEST_ID, value).build()).build();
	}
}
