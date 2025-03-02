package systems.monitor.manager.utils;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for the {@link DateUtils} class.
// The tests examine just successful scenarios.
// @author Antonio Scardace
// @version 1.0

class DateUtilsTest {
    
    @Test
    void testDateTimeToString_Successful() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        String result = DateUtils.dateTimeToString(timestamp);
        assertEquals("2024-01-01 12:00:00", result);
    }

    @Test
    void testStringToDateTime_Successful() {
        String datetime = "2024-01-01 12:00:00";
        LocalDateTime result = DateUtils.stringToDateTime(datetime);
        LocalDateTime expected = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        assertEquals(expected, result);
    }
}