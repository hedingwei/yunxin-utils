/*
 * Copyright 2016 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * This project is hosted at: https://github.com/lukeu/swing-dpi
 * Comments & collaboration are both welcome.
 */

package com.yunxin.utils.swingdpi;

import sun.awt.OSInfo;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class storing an observable static/global UI scaling factor, and some utility methods to scale
 * various types and metrics by that value.
 */
public class UiScaling {
    private UiScaling() {}

    /**
     * Scaling in integer percentage points; so 100 results in the 'standard' setting of 96 DPI.
     */
    private static final AtomicInteger s_scalingPercentage = 
            new AtomicInteger(DpiUtils.getClosestStandardScaling());

    /**
     * Threading: all access must synchronize on this final member
     */
    private static final List<ChangeListener> s_changeListeners = new ArrayList<ChangeListener>();

    /**
     * @return the currently-set scaling as a factor (or is that multiplicand?) Anyway 2.5f == 250%
     */
    public static float getScalingFactor() {
        return getScaling() / 100f;
    }

    /**
     * @return the currently-set scaling in percentage points.
     */
    public static int getScaling() {
        if(OSInfo.getOSType()== OSInfo.OSType.WINDOWS) {
            return s_scalingPercentage.get();
        }else{
            return (int) (s_scalingPercentage.get()*1.2);
        }

//        return s_scalingPercentage.get();
    }

    public static void setScaling(int scalingInPercent) {
        assert SwingUtilities.isEventDispatchThread();

        int old = s_scalingPercentage.getAndSet(scalingInPercent);
        if (old != scalingInPercent) {
            notifyListeners();
        }
    }

    /**
     * Public to allow users to notify listeners of similar events (like L&F changes) without
     * needing a second listener & event to register on. (For better or worse.)
     */
    private static void notifyListeners() {
        List<ChangeListener> listeners;
        synchronized (s_changeListeners) {
            listeners = new ArrayList<ChangeListener>(s_changeListeners);
        }

        for (ChangeListener changeListener : listeners) {
            changeListener.stateChanged(new ChangeEvent(UiScaling.class));
        }
    }

    /**
     * Be notified when L&amp;F or scaling-level has changed.
     * <p>
     * As always when listening on static objects: take care to avoid memory leaks by removing
     * listeners again, as these may continue to hold references to otherwise shorter-lived objects.
     */
    public static void addChangeListener(ChangeListener listener) {
        synchronized (s_changeListeners) {
            s_changeListeners.add(listener);
        }
    }

    /**
     * Threading note: it is possible that listeners will still be notified of events for a short
     * period after this method has exited, unless this method is called from the EDT.
     */
    public static void removeChangeListener(ChangeListener listener) {
        synchronized (s_changeListeners) {
            s_changeListeners.remove(listener);
        }
    }

    public static Dimension newDimension(int width, int height) {
        return scale(new Dimension(width, height));
    }

    public static int scale(int i) {

            return Math.round((i * getScaling()) / 100f);


    }

    public static float scale(float f) {
        return f * getScalingFactor();
    }

    public static Dimension scale(Dimension dim) {
        return (getScaling() == 100) ? dim : new Dimension(scale(dim.width), scale(dim.height));
    }

    public static Font scale(Font font) {
        return font.deriveFont(font.getSize2D() * getScalingFactor());
    }

    public static Border createEmptyBorder(int top, int left, int bottom, int right) {
        return BorderFactory.createEmptyBorder(
                scale(top), scale(left), scale(bottom), scale(right));
    }

    public static Border createLineBorder(Color color, int thickness)  {
        return BorderFactory.createLineBorder(color, scale(thickness));
    }

    public static Border createMatteBorder(int top, int left, int bottom, int right, Color color) {
        return BorderFactory.createMatteBorder(
                scale(top), scale(left), scale(bottom), scale(right), color);
    }
}
