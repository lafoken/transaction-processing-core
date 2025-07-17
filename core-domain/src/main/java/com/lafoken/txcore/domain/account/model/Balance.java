package com.lafoken.txcore.domain.account.model;

public record Balance(Amount value) {
  public Balance add(Amount amountToAdd) {
    return new Balance(new Amount(this.value.value().add(amountToAdd.value())));
  }
}
