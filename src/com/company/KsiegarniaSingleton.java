package com.company;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

import static com.googlecode.lanterna.gui2.dialogs.MessageDialogButton.OK;

public class KsiegarniaSingleton {
    private int miejsce_w_magazynie_i_na_polkach;
    private static KsiegarniaSingleton ksiegarniaSingleton = new KsiegarniaSingleton();
    private KsiegarniaSingleton(){
        this.miejsce_w_magazynie_i_na_polkach = 5000;
    }
    public static KsiegarniaSingleton getInstance(){
        return ksiegarniaSingleton;
    }

    public int ilosc_miejsc_na_poczotku(){
        return 5000;
    }

    public int ilosc_wolynch_miejsc(){
        return miejsce_w_magazynie_i_na_polkach;
    }

    public void wolne_miejsce(WindowBasedTextGUI textGUI){
        new MessageDialogBuilder()
                .setTitle("Potwierdzenie")
                .setText("Tyle jest jeszcze miejsca:\n" + miejsce_w_magazynie_i_na_polkach)
                .addButton(OK)
                .build()
                .showDialog(textGUI);
    }

    public void update_miejsca(int ilosc){
            miejsce_w_magazynie_i_na_polkach = miejsce_w_magazynie_i_na_polkach + ilosc;
    }
}
