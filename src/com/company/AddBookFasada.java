package com.company;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;

import java.util.regex.Pattern;

public class AddBookFasada implements FasadaToAdd{

    @Override
    public String dodaj_imie_tytul(WindowBasedTextGUI textGUI) {
        String tytul = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Tytuł")
                .setValidationPattern(Pattern.compile("[a-zA-Z]*"),"wpisz tytuł")
                .build()
                .showDialog(textGUI);
        return tytul;
    }

    @Override
    public String dodaj_nazwisko_autor(WindowBasedTextGUI textGUI) {
        String autor = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Autora")
                .setValidationPattern(Pattern.compile("[a-zA-Z]*"),"wpisz autora")
                .build()
                .showDialog(textGUI);
        return autor;
    }

    @Override
    public int dodaj_indeks_rok(WindowBasedTextGUI textGUI) {
        String rok = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Rok wydania")
                .setValidationPattern(Pattern.compile("[0-9]*"),"wpisz rok wydania")
                .build()
                .showDialog(textGUI);
        int year = Integer.parseInt(rok);
        return year;
    }

    @Override
    public int dodaj_rok_studiow_cena(WindowBasedTextGUI textGUI) {
        String cena = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Cenę")
                .setValidationPattern(Pattern.compile("[0-9]*"),"wpisz cenę")
                .build()
                .showDialog(textGUI);
        int prize = Integer.parseInt(cena);
        return prize;
    }

    @Override
    public int dodaj_iloscwyp_ilosc_nastanie(WindowBasedTextGUI textGUI) {
        String dostepna_ilosc = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Ilość na magazynie")
                .setValidationPattern(Pattern.compile("[0-9]*"),"wpisz ilość na stanie")
                .build()
                .showDialog(textGUI);
        int amount = Integer.parseInt(dostepna_ilosc);
        return amount;
    }
}


