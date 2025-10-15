package com.account.record;

import java.util.UUID;

public record TransferBalance(UUID fromAccountId,UUID toAccountId,Double balance) {
}
