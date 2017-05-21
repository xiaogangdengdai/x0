package com.ssx.spa.javabean;

import java.io.Serializable;
import java.util.List;

public class AllLive implements Serializable {
    private List<DoubleLives> lives;
    private List<SingelLives> singelLives;

    public void setLives(List<DoubleLives> lives) {
        this.lives = lives;
    }

    public List<DoubleLives> getLives() {
        return this.lives;
    }

    public void setSingelLives(List<SingelLives> singelLives) {
        this.singelLives = singelLives;
    }

    public List<SingelLives> getSingelLives() {
        return this.singelLives;
    }
}
