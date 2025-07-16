package com.lafoken.txcore.domain.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lafoken.txcore.domain.account.event.AccountCreatedEvent;
import com.lafoken.txcore.domain.account.model.Amount;
import com.lafoken.txcore.domain.shared.event.DomainEvent;
import com.lafoken.txcore.domain.shared.model.UserId;
import com.lafoken.txcore.domain.account.model.AccountId;

public class AccountAggregate {
  private List<DomainEvent> uncommittedEvents = new ArrayList<>();

  public AccountAggregate(AccountId accountId, UserId ownerId, Amount initialDeposit) {
    this.uncommittedEvents.add(new AccountCreatedEvent(accountId, ownerId, initialDeposit));
  }

  public List<DomainEvent> getUncommittedEvents() {
    return Collections.unmodifiableList(uncommittedEvents);
  }
}
