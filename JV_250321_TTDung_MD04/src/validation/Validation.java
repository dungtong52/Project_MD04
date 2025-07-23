package validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Validation {
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isIntegerInRange(String input, int min, int max) {
        if (!isNotEmpty(input)) return false;
        try {
            int number = Integer.parseInt(input);
            return number >= min && number <= max;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidType(String input, String type) {
        if (!isNotEmpty(input)) return false;
        try {
            switch (type) {
                case "Integer":
                    return Integer.parseInt(input) > 0;
                case "Float":
                    return Float.parseFloat(input) > 0;
                case "Short":
                    return Short.parseShort(input) > 0;
                case "Long":
                    return Long.parseLong(input) > 0;
                case "Boolean":
                    if (!input.equalsIgnoreCase("true") && !input.equalsIgnoreCase("false"))
                        return false;
                    return input.equalsIgnoreCase("true");
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidLength(String input, int maxLength) {
        if (!isNotEmpty(input)) return false;
        try {
            return input.length() <= maxLength;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidDate(String input, String pattern) {
        if (!isNotEmpty(input)) return false;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate.parse(input, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidUserName(String input) {
        return isNotEmpty(input) && input.matches("^[a-zA-Z0-9_]{1,30}$");
    }

    public static boolean isValidPassword(String input) {
        return isNotEmpty(input) && input.matches("^[a-zA-Z0-9$%]{8,30}$");
    }

    public static boolean isValidEmail(String input) {
        return isNotEmpty(input) && input.matches("^[a-zA-Z0-9.]+@[a-zA-Z0-9]+\\.[a-z]{2,}$");
    }

    public static boolean isValidPhone(String input) {
        return isNotEmpty(input) && input.matches("^0[0-9]{9,10}$");
    }
}
