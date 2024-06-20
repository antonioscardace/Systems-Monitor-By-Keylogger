package systems.monitor.manager.strategy;

import systems.monitor.manager.requests.RecordRequest;

// Strategy interface for inserting a record into a database.
// @author Antonio Scardace
// @version 1.0

public interface RecordInsertionStrategy {
    void insertRecord(RecordRequest req) throws IllegalArgumentException;
}