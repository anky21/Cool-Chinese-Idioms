package me.anky.randomnumber;

import java.util.Random;

/**
 * Created by Anky An on 9/03/2017.
 * anky25@gmail.com
 */

public class CreateRandomNumber {
    public static int createRandomNumber(){
        int min = 1;
        // The number of idioms in the database
        int max = 20;
        int rnd = new Random().nextInt(max - min + 1) + min;
        return rnd;
    }
}
