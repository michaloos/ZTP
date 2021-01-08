package com.company;
import javax.swing.*;
import java.awt.*;
//Stan odnoszący się do labela informującego o stanie księgarni, nie połączony z stanami w klasie księgarnia w trybie graficznym
abstract class StanGUI {

    JLabel jLabelstan;
    StanGUI(JLabel jLabel){
        this.jLabelstan = jLabel;
    }

    //stan zmienia color labela oraz tekst informujący o tym jaki jest stan ilości miejsca w księgarni
    public abstract void color();
    public abstract void tekst();
}

class StanPelnoGUI extends StanGUI{

    StanPelnoGUI(JLabel jLabel){
        super(jLabel);
    }

    @Override
    public void color() {
        jLabelstan.setBackground(Color.RED);
    }

    @Override
    public void tekst() {
        jLabelstan.setText("W tym momencie nie ma\nmiejsca na nowe książki!");
    }
}

class StanPrawiePelnoGUI extends StanGUI{

    StanPrawiePelnoGUI(JLabel jLabel){
        super(jLabel);
    }

    @Override
    public void color() {
        jLabelstan.setBackground(Color.RED);
    }

    @Override
    public void tekst() {
        jLabelstan.setText("Zaraz zabraknie miejsca\nna nowe książki");
    }
}

class StanPrawiePustoGUI extends StanGUI{

    StanPrawiePustoGUI(JLabel jLabelstan){
        super(jLabelstan);
    }

    @Override
    public void color() {
        jLabelstan.setBackground(Color.GREEN);
    }

    @Override
    public void tekst() {
        jLabelstan.setText("Studentom zaraz zabraknie\nksiążek do czytania!");
    }
}

class StanPustoGUI extends StanGUI{

    StanPustoGUI(JLabel jLabelstan){
        super(jLabelstan);
    }

    @Override
    public void color() {
        jLabelstan.setBackground(Color.GREEN);
    }

    @Override
    public void tekst() {
        jLabelstan.setText("Studentci nie mają\nco czytać!");
    }
}

class StanZbalansowanyGUI extends StanGUI{

    StanZbalansowanyGUI(JLabel jLabel){
        super(jLabel);
    }

    @Override
    public void color() {
        jLabelstan.setBackground(Color.YELLOW);
    }

    @Override
    public void tekst() {
        jLabelstan.setText("Ilość książek umiarkowana");
    }
}