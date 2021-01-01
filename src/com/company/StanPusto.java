package com.company;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;

public class StanPusto extends Stan{

    StanPusto(Label label) {
        super(label);
    }

    @Override
    public void color() {
        labelstan.setBackgroundColor(new TextColor.RGB(0,255,0));
    }

    @Override
    public void tekst() {
        labelstan.setText("Studentci nie mają\nco czytać!");
    }
}
