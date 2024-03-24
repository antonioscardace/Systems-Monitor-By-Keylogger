package systems.monitor.server.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the {@link Utils} class.
// The tests examine both successful and unsuccessful scenarios.
// @author Antonio Scardace
// @version 1.0

@SpringBootTest
class UtilsTest {
    
    @Test
    void testDatetimeToString() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        String result = Utils.datetimeToString(timestamp);
        assertEquals("2024-01-01 12:00:00", result);
    }

    @Test
    void testStringToTimestamp() {
        String datetime = "2024-01-01 12:00:00";
        LocalDateTime result = Utils.stringToTimestamp(datetime);
        LocalDateTime expected = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        assertEquals(expected, result);
    }

    @Test
    void testStringToTimestamp_InvalidFormat() {
        String datetime = "01-2024-01 12:00:00";
        assertThrows(DateTimeParseException.class, () -> {
            Utils.stringToTimestamp(datetime);
        });
    }
}