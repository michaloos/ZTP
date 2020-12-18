package com.company;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;

public class StanNieMaMiejscaNaNoweKsizki extends Stan{

    StanNieMaMiejscaNaNoweKsizki(Label label) {
        super(label);
    }

    @Override
    public void color() {
        labelstan.setBackgroundColor(new TextColor.RGB(255,0,0));
    }

    @Override
    public void tekst() {
        labelstan.setText("W tym momencie nie ma\nmiejsca na nowe książki!");
    }
}
