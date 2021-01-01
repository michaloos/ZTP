package com.company;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;

public class StanPrawiePelno extends Stan{

    StanPrawiePelno(Label label) {
        super(label);
    }

    @Override
    public void color() {
        labelstan.setBackgroundColor(new TextColor.RGB(255,0,0));
    }

    @Override
    public void tekst() {
        labelstan.setText("Zaraz zabraknie miejsca\nna nowe książki");
    }
}
