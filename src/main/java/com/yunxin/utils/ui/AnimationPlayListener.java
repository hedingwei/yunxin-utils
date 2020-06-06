package com.yunxin.utils.ui;

import java.awt.*;

public interface AnimationPlayListener {
    public void onStart();
    public void onPlay(int index, Image image);
    public void onStop();
}
