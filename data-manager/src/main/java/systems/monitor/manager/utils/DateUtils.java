package systems.monitor.manager.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// Utility class that provides various helper methods for working with dates.
// @author Antonio Scardace
// @version 1.0

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    // Converts a LocalDateTime object to its String representation.
    // The outcome string follows the pattern
    
    public static String dateTimeToString(LocalDateTime timestamp) {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Converts a string representing a timestamp to a LocalDateTime object.
    // The "datetime" parameter must follow the pattern "yyyy-MM-dd HH:mm:ss".

    public static LocalDateTime stringToDateTime(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(datetime, formatter);
    }
}