/*
 * This file is part of muCommander, http://www.mucommander.com
 *
 * muCommander is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * muCommander is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mucommander.plugin.manager.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import javax.swing.JToggleButton;

/**
 * Addons manager cart button.
 */
@NullMarked
public class CartButton extends JToggleButton {

    protected int changesCount = 0;
    protected final Color changesCountFg = new Color(255, 255, 201);
    protected final Color noChangesFg = Color.BLACK;
    protected final Color changesCountBg = new Color(16, 163, 16);
    protected final Color noChangesBg = Color.LIGHT_GRAY;

    public CartButton() {
        super();
    }

    @Override
    public void setText(String text) {
        super.setText(text + "      ");
    }

    @Override
    protected void paintComponent(@Nullable Graphics graphics) {
        if (ui == null || graphics == null) {
            return;
        }

        Graphics g = graphics.create();
        try {
            ui.update(g, this);
            int x = getWidth() - 34;
            int y = getHeight() / 2 - 10;
            g.setColor(changesCount == 0 ? noChangesBg : changesCountBg);
            g.fillOval(x, y, 25, 20);
            g.setColor(changesCount == 0 ? noChangesFg : changesCountFg);
            Font font = getFont().deriveFont(Font.BOLD);
            g.setFont(font);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            String text = changesCount > 99 ? "+" : String.valueOf(changesCount);
            char[] changesCharArray = text.toCharArray();
            int textWidth = fontMetrics.charsWidth(changesCharArray, 0, changesCharArray.length);
            g.drawString(text, x + 13 - textWidth / 2, y + 15);
        } finally {
            g.dispose();
        }
    }

    public void setChangesCount(int changesCount) {
        this.changesCount = changesCount;
        repaint();
    }
}
