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

package com.yunxin.utils.swingdpi.plaf;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class NimbusTweaker extends BasicTweaker {


    private static final String[] PREFIX_TO_NOT_SCALE_ICONS = {
            "InternalFrame."//, "CheckBox", "Menu.", "MenuItem.", "RadioButton"
    };

    /**
     * UIDefaults starting with these strings are 'prescaled' and therefore require being scaled by
     * {@link #alternateScaleFactor}.
     */
    private static final String[] PRESCALED_INTEGER_PREFIXES = {
            "ScrollBar.", "InternalFrame.", "Menu.",  "MenuBar.", "MenuItem.",
            "CheckBoxMenuItem.", "RadioButtonMenuItem.", "ToolBar.","SplitPane.","CheckBox.","Table.","TitledBorder."
    };

    public NimbusTweaker(float scaleFactor) {
        super(scaleFactor);
    }

    @Override
    public void initialTweaks() {

        Font font = uiDefaults.getFont("defaultFont");

        if (font != null) {
            uiDefaults.put("defaultFont", new FontUIResource(
                    font.getName(), font.getStyle(), Math.round(font.getSize() * scaleFactor)));
        }
    }

    /**
     * Don't attempt to scale fonts - in Nimbus, setting "defaultFont" is sufficient as this will
     * be inherited by all others.
     */
    @Override
    public Font modifyFont(Object key, Font original) {
        return original;
    }

    /**
     * Scaling Radio or CheckBox button icons leads to really weird artifacts in Nimbus. Their
     * selection state is generally not rendered. We therefore disable all scaling.
     */
    @Override
    public Icon modifyIcon(Object key, Icon original) {

        // TODO: disable more selectively?
        // Scaling seems problematic - scaled icons are not reset in the UIDefaults?
        // (Perhaps something to do with UI multiplexing & not overriding the correct data table??)
        return original;
    }
}
