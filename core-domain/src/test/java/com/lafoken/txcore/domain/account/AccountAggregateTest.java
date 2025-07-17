package com.lafoken.txcore.domain.account;

import com.lafoken.txcore.domain.account.event.FundsDepositedEvent;
import com.lafoken.txcore.domain.account.model.*;
import com.lafoken.txcore.domain.shared.model.UserId;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.lafoken.txcore.domain.account.event.AccountCreatedEvent;
import com.lafoken.txcore.domain.account.event.AccountEvent;

import static org.junit.jupiter.api.Assertions.*;

class AccountAggregateTest {
	@Test
	@DisplayName("should generate AccountCreatedEvent on creation")
	void shouldGenerateAccountCreatedEventOnCreation() {
		// Given
		var accountId = new AccountId("acc-123");
		var ownerId = new UserId("user-abc");
		var depositAmount = Amount.of("100.00");

		// When
		var account = new AccountAggregate(accountId, ownerId, depositAmount);

		// Then
		List<AccountEvent> events = account.getUncommittedEvents();
		assertEquals(1, events.size());

		if (events.get(0) instanceof AccountCreatedEvent event) {
			assertEquals(accountId, event.accountId());
			assertEquals(ownerId, event.ownerId());
			assertEquals(Amount.of("100.00"), event.depositAmount());
		} else {
			fail("Expected AccountCreatedEvent but got: " + events.get(0).getClass().getName());
		}
	}

	@Test
	@DisplayName("should generate FundsDepositedEvent on deposit")
	void shouldGenerateFundsDepositedEventOnDeposit() {
		// Given
		var accountId = new AccountId("acc-123");
		List<AccountEvent> initialEvents = List.of(
				new AccountCreatedEvent(
						accountId,
						new UserId("user-abc"),
						Amount.of("100.00")));
		var account = new AccountAggregate(accountId, initialEvents);
		account.clearUncommittedEvents();

		var depositAmount = Amount.of("50.00");

		// When
		account.deposit(depositAmount);

		// Then
		List<AccountEvent> newEvents = account.getUncommittedEvents();
		assertEquals(1, newEvents.size());

		if (newEvents.get(0) instanceof FundsDepositedEvent event) {
			assertEquals(accountId, event.accountId());
			assertEquals(Amount.of("50.00"), event.depositAmount());
			assertEquals(new Balance(Amount.of("150.00")), event.newBalance());
		} else {
			fail("Expected FundsDepositedEvent but got:" + newEvents.get(0).getClass().getName());
		}
	}
}
