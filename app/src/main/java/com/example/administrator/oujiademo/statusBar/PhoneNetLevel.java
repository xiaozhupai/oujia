package com.example.administrator.oujiademo.statusBar;

public interface PhoneNetLevel {
    /**
     * 信号满格
     */
    int BEST = 5;

    /**
     * 4 格信号
     */
    int BETTER = 4;

    int NORMAL = 3;

    int BAD = 2;

    int WORSE = 1;

    /**
     * 无信号
     */
    int WORST = 0;
}
