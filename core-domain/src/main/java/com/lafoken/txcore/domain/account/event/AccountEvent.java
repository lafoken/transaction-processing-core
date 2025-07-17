package com.lafoken.txcore.domain.account.event;

import com.lafoken.txcore.domain.shared.event.DomainEvent;

/**
 * A sealed interface representing all domain events related to the
 * AccountAggregate.
 * It extends the global DomainEvent marker interface.
 */
public sealed interface AccountEvent extends DomainEvent
		permits AccountCreatedEvent, FundsDepositedEvent {
}
