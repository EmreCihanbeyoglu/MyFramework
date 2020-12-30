package com.tester;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordGenerator {

    // form required groups to use them inside password
    private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
    private static final String DIGIT = "0123456789";
    private static final String SPECIAL_CHAR = "~$^+=<>!@#&()–[{}]:;',?/*;";


    private static final String PASSWORD_ALLOW =
            CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + SPECIAL_CHAR;

    private static SecureRandom random = new SecureRandom();

    /**
     *
     * @param passwordLength
     * @param lowerCaseLetterNumber
     * @param upperCaseLetterNumber
     * @param digitNumber
     * @param specialCharNumber
     * @return returns a string composed of specified restrictions. The main method that you should use is this
     *         The others will help this method to generate a demanded password.
     */
    public static String generateStrongPassword(int passwordLength, int lowerCaseLetterNumber, int upperCaseLetterNumber, int digitNumber, int specialCharNumber) {

        StringBuilder result = new StringBuilder(passwordLength);

        // at least 2 chars (lowercase)
        String strLowerCase = generateRandomString(CHAR_LOWERCASE, lowerCaseLetterNumber);
        System.out.format("%-20s: %s%n", "Chars (Lowercase)", strLowerCase);
        result.append(strLowerCase);

        // at least 2 chars (uppercase)
        String strUppercaseCase = generateRandomString(CHAR_UPPERCASE, upperCaseLetterNumber);
        System.out.format("%-20s: %s%n", "Chars (Uppercase)", strUppercaseCase);
        result.append(strUppercaseCase);

        // at least 2 digits
        String strDigit = generateRandomString(DIGIT, digitNumber);
        System.out.format("%-20s: %s%n", "Digits", strDigit);
        result.append(strDigit);

        // at least 2 special characters (punctuation + symbols)
        String strSpecialChar = generateRandomString(SPECIAL_CHAR, specialCharNumber);
        System.out.format("%-20s: %s%n", "Special chars", strSpecialChar);
        result.append(strSpecialChar);

        // remaining (non-specified number of chars are selected randomly), just random
        int specifiedRulesLength = lowerCaseLetterNumber + upperCaseLetterNumber + digitNumber + specialCharNumber;
        String strOther = generateRandomString(PASSWORD_ALLOW, passwordLength - specifiedRulesLength);
        System.out.format("%-20s: %s%n", "Others", strOther);
        result.append(strOther);

        String password = result.toString();
        // combine all
        System.out.format("%-20s: %s%n", "Password", password);
        // shuffle again
        System.out.format("%-20s: %s%n", "Final Password", shuffleString(password));
        System.out.format("%-20s: %s%n%n", "Password Length", password.length());

        return password;
    }

    private static String generateRandomString(String input, int size) {

        if (input == null || input.length() <= 0)
            throw new IllegalArgumentException("Invalid input.");
        if (size < 1) throw new IllegalArgumentException("Invalid size.");

        StringBuilder result = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            // produce a random order
            int index = random.nextInt(input.length());
            result.append(input.charAt(index));
        }
        return result.toString();
    }

    public static String shuffleString(String input) {
        List<String> result = Arrays.asList(input.split(""));
        Collections.shuffle(result);
        // java 8
        return result.stream().collect(Collectors.joining());
    }

    public static String compareAndGenerateAgain(String oldPassword, int passwordLength, int lowerCaseLetterNumber, int upperCaseLetterNumber, int digitNumber, int specialCharNumber ){
        String newGenerated;
        while (true){
            newGenerated = generateStrongPassword(passwordLength, lowerCaseLetterNumber, upperCaseLetterNumber, digitNumber, specialCharNumber);
            if (!newGenerated.equals(oldPassword)){
                break;
            }
        }
        return newGenerated;
    }
}
