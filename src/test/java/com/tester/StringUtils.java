package com.tester;

public class StringUtils {

    public static int getIndexOfString(String innerText, String outerText){
        return outerText.indexOf(innerText);
    }

    public static String getInnerStringBetweenIndexes(String text, int startIndex, int endIndex){
        return text.substring(startIndex,endIndex);
    }

    public static String getInnerStringBetweenTwoText(String mainText, String firstPart, String secondPart){
        int startIndex = getIndexOfString(firstPart, mainText) + firstPart.length();
        int endIndex = getIndexOfString(secondPart, mainText);
        return getInnerStringBetweenIndexes(mainText, startIndex, endIndex).trim();
    }

    public static String reverseWord(String word){
        String[] strArray = word.split("");
        String newString = "";
        for (int i = strArray.length - 1; i >= 0 ; i--) {
            newString += strArray[i];
        }
        return newString;
    }
}
