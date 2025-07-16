package com.lafoken.txcore.domain.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.lafoken.txcore.domain.shared.event.DomainEvent;
import com.lafoken.txcore.domain.account.event.AccountCreatedEvent;
import com.lafoken.txcore.domain.account.model.*;
import com.lafoken.txcore.domain.shared.model.UserId;

import java.math.BigDecimal;
import java.util.List;

class AccountAggregateTest {

  @Test
  @DisplayName("AccountCreatedEvent")
  void shouldGenerateAccountCreatedEventOnCreation() {
    // Given
    var accountId = new AccountId("acc-123");
    var ownerId = new UserId("user-abc");
    var initialDeposit = new Amount(new BigDecimal("100.00"));

    // When
    var account = new AccountAggregate(accountId, ownerId, initialDeposit);

    // Then
    List<DomainEvent> events = account.getUncommittedEvents();
    assertEquals(1, events.size());
    assertTrue(events.get(0) instanceof AccountCreatedEvent);

    var event = (AccountCreatedEvent) events.get(0);
    assertEquals(accountId, event.accountId());
  }
}
