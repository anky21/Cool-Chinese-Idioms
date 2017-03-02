package me.anky.coolchineseidioms;

import java.util.Random;

public class IdiomProvider {
    public static String getIdiom() {

        // Randomly create an integer according to the size of the idiom collection database
        int min = 1;
        int max = 20;
        int rnd = new Random().nextInt(max - min + 1) + min;

        return String.valueOf(rnd);
    }
}
