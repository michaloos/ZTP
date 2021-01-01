package com.company;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;

public class StanZbalansowany extends Stan{

    StanZbalansowany(Label label) {
        super(label);
    }

    @Override
    public void color() {
        labelstan.setBackgroundColor(new TextColor.RGB(255,255,0));
    }

    @Override
    public void tekst() {
        labelstan.setText("Ilość książek umiarkowana");
    }
}
