package com.lafoken.txcore.domain.account.event;

import com.lafoken.txcore.domain.account.model.Amount;
import com.lafoken.txcore.domain.shared.model.UserId;
import com.lafoken.txcore.domain.account.model.AccountId;

public record AccountCreatedEvent(
		AccountId accountId,
		UserId ownerId,
		Amount depositAmount) implements AccountEvent {
}
