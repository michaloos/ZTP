package com.company;

import com.googlecode.lanterna.gui2.Label;

//Stan odnoszący się do labela informującego o stanie księgarni, nie połączony z stanami w klasie księgarnia
abstract class Stan {

    Label labelstan;
    Stan(Label label){
        this.labelstan = label;
    }

    public Stan() {

    }

    //stan zmienia color labela oraz tekst informujący o tym jaki jest stan ilości miejsca w księgarni
    public abstract void color();
    public abstract void tekst();
}

