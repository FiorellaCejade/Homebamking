package com.mindhub.homebaking.utils;

public final class CardUtils {

    private CardUtils(){}

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String randomNumCard() {
        return getRandomNumber(1000, 9999)+"-"+ getRandomNumber(1000, 9999)+"-"+getRandomNumber(1000, 9999)+"-"+getRandomNumber(1000, 9999);
    }

    public static int getCvv(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static int createCvv() {
        return getCvv(999,100);
    }






}
