package com.proj.learningIT;

import android.media.MediaPlayer;

public class SettingConfiguration {
    public boolean musicOn;
    public boolean soundOn;
    public int questionQuantity;
    public boolean timerOn;

    public boolean isSoundReferencesInit = false;

    MediaPlayer quizSong = null;
    MediaPlayer trueSound = null;
    MediaPlayer falseSound = null;

    public SettingConfiguration(boolean musicOn, boolean soundOn, int questionQuantity, boolean timerOn) {
        this.musicOn = musicOn;
        this.soundOn = soundOn;
        this.questionQuantity = questionQuantity;
        this.timerOn = timerOn;
    }
}
