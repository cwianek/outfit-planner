package com.outfit.planner.system.outbox;

public interface OutboxScheduler {
    void processOutboxMessage();
}
