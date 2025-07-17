package com.lafoken.txcore.domain.account.model;

import java.math.BigDecimal;

public record Amount(BigDecimal value) {
	public static Amount of(String value) {
		return new Amount(new BigDecimal(value));
	}
}
