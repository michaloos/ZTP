package com.company;

import com.googlecode.lanterna.gui2.Label;

abstract class Stan {

    Label labelstan;
    Stan(Label label){
        this.labelstan = label;
    }

    public Stan() {

    }

    public abstract void color();
    public abstract void tekst();
}

