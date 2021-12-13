package com.example.mangmacs;

import java.util.Random;

public class SendCode {
    private int code;

    public int getRandomCode() {
        Random random = new Random();
        code = random.nextInt(999999);
        return code;
    }
}
