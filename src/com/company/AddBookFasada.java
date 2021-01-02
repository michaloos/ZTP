package com.company;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;

import java.util.regex.Pattern;

public class AddBookFasada implements FasadaToAdd{

    //proszenie użytownika o tytuł książki
    @Override
    public String dodaj_imie_tytul(WindowBasedTextGUI textGUI) {
        return new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Tytuł")
                .setValidationPattern(Pattern.compile("[a-zA-Z0-9 ]*"),"wpisz tytuł")
                .build()
                .showDialog(textGUI);
    }

    //proszenie użytownika o autora książki
    @Override
    public String dodaj_nazwisko_autor(WindowBasedTextGUI textGUI) {
        return new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Autora")
                .setValidationPattern(Pattern.compile("[a-zA-Z ]*"),"wpisz autora")
                .build()
                .showDialog(textGUI);
    }

    //proszenie użytownika o rok wydania książki
    @Override
    public int dodaj_indeks_rok(WindowBasedTextGUI textGUI) {
        String rok = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Rok wydania")
                .setValidationPattern(Pattern.compile("[0-9]*"),"wpisz rok wydania")
                .build()
                .showDialog(textGUI);
        return Integer.parseInt(rok);
    }

    //proszenie użytownika o cenę książki
    @Override
    public int dodaj_rok_studiow_cena(WindowBasedTextGUI textGUI) {
        String cena = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Cenę")
                .setValidationPattern(Pattern.compile("[0-9]*"),"wpisz cenę")
                .build()
                .showDialog(textGUI);
        return Integer.parseInt(cena);
    }

    //proszenie użytownika o ilość w księgarni danej książki
    @Override
    public int dodaj_iloscwyp_ilosc_nastanie(WindowBasedTextGUI textGUI) {
        String dostepna_ilosc = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Ilość na magazynie")
                .setValidationPattern(Pattern.compile("[0-9]*"),"wpisz ilość na stanie")
                .build()
                .showDialog(textGUI);
        return Integer.parseInt(dostepna_ilosc);
    }
}


