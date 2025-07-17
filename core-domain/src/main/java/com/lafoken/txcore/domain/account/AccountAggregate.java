package com.lafoken.txcore.domain.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lafoken.txcore.domain.account.event.AccountCreatedEvent;
import com.lafoken.txcore.domain.account.event.AccountEvent;
import com.lafoken.txcore.domain.account.event.FundsDepositedEvent;
import com.lafoken.txcore.domain.account.model.Amount;
import com.lafoken.txcore.domain.account.model.Balance;
import com.lafoken.txcore.domain.shared.model.UserId;
import com.lafoken.txcore.domain.account.model.AccountId;

public class AccountAggregate {

	private AccountId id;
	private Balance balance;
	private List<AccountEvent> uncommittedEvents = new ArrayList<>();

	// Constructor for creating a new aggregate
	public AccountAggregate(AccountId id, UserId ownerId, Amount depositAmount) {
		applyAndRecord(new AccountCreatedEvent(id, ownerId, depositAmount));
	}

	// Constructor for reconstituting the aggregate from its history
	public AccountAggregate(AccountId id, List<AccountEvent> eventHistory) {
		this.id = id;
		for (AccountEvent event : eventHistory) {
			apply(event);
		}
	}

	// Internal state mutation logic

	// Routes events to the correct state change method
	private void apply(AccountEvent event) {
		switch (event) {
			case AccountCreatedEvent e -> apply(e);
			case FundsDepositedEvent e -> apply(e);
		}
	}

	private void apply(AccountCreatedEvent event) {
		this.id = event.accountId();
		this.balance = new Balance(event.depositAmount());
	}

	private void apply(FundsDepositedEvent event) {
		this.balance = event.newBalance();
	}

	// Applies the state change and recorcs the event
	private void applyAndRecord(AccountEvent event) {
		apply(event);
		uncommittedEvents.add(event);
	}

	// Other

	public void deposit(Amount amount) {
		var newBalance = this.balance.add(amount);
		applyAndRecord(new FundsDepositedEvent(this.id, amount, newBalance));
	}

	public List<AccountEvent> getUncommittedEvents() {
		return Collections.unmodifiableList(uncommittedEvents);
	}

	public void clearUncommittedEvents() {
		uncommittedEvents.clear();
	}
}
