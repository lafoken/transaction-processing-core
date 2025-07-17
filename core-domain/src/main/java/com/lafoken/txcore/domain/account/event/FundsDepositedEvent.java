package com.lafoken.txcore.domain.account.event;

import com.lafoken.txcore.domain.account.model.AccountId;
import com.lafoken.txcore.domain.account.model.Amount;
import com.lafoken.txcore.domain.account.model.Balance;

public record FundsDepositedEvent(
		AccountId accountId,
		Amount depositAmount,
		Balance newBalance) implements AccountEvent {
}
