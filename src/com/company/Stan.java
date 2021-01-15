package com.company;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Label;

import javax.swing.*;
import java.awt.*;

//Stan dla trybu tekstowego
//Stan odnoszący się do labela informującego o stanie księgarni, nie połączony z stanami w klasie księgarnia
abstract class Stan {

    Label labelstan;
    Stan(Label label){
        this.labelstan = label;
    }

    //stan zmienia color labela oraz tekst informujący o tym jaki jest stan ilości miejsca w księgarni
    public abstract void color();
    public abstract void tekst();
}

//stan w którym nie ma miejsca na nowe książki, księgarnia jest zapchana
class StanPelno extends Stan{

    StanPelno(Label label) {
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

//stan w którym prawie nie ma miejsca na nowe książki, jest bardzo mało miejsca na nowe książki
class StanPrawiePelno extends Stan{

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

//stan w którym nie prawie nie ma książek do czytania
class StanPrawiePusto extends Stan{

    StanPrawiePusto(Label label) {
        super(label);
    }

    @Override
    public void color() {
        labelstan.setBackgroundColor(new TextColor.RGB(0,255,0));
    }

    @Override
    public void tekst() {
        labelstan.setText("Studentom zaraz zabraknie\nksiążek do czytania!");
    }
}

//stan w którym nie ma żadnych książek do czytania, studenci nie mogą nic wyporzyczyć
class StanPusto extends Stan{

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

//stan w którym książek nie jest ani za dużo ani za mało
class StanZbalansowany extends Stan{

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