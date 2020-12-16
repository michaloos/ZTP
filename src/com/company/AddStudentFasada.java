package com.company;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;

import java.util.regex.Pattern;

public class AddStudentFasada implements FasadaToAdd{

    @Override
    public String dodaj_imie_tytul(WindowBasedTextGUI textGUI) {
        String imie = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Imie")
                .setValidationPattern(Pattern.compile("[a-zA-Z]*"),"wpisz imie")
                .build()
                .showDialog(textGUI);
        return imie;
    }

    @Override
    public String dodaj_nazwisko_autor(WindowBasedTextGUI textGUI) {
        String nazwisko = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Nazwisko")
                .setValidationPattern(Pattern.compile("[a-zA-Z]*"),"wpisz nazwisko")
                .build()
                .showDialog(textGUI);
        return nazwisko;
    }

    @Override
    public int dodaj_indeks_rok(WindowBasedTextGUI textGUI) {
        String indeks = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Numer Indeksu")
                .setValidationPattern(Pattern.compile("[0-9]*"),"wpisz indeks")
                .build()
                .showDialog(textGUI);
        int indeksint = Integer.parseInt(indeks);
        return indeksint;
    }

    @Override
    public int dodaj_rok_studiow_cena(WindowBasedTextGUI textGUI) {
        String rok = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Rok Studiow")
                .setValidationPattern(Pattern.compile("[0-9]*"),"wpisz rok studiow")
                .build()
                .showDialog(textGUI);
        int rokSt = Integer.parseInt(rok);
        return rokSt;
    }

    @Override
    public int dodaj_iloscwyp_ilosc_nastanie(WindowBasedTextGUI textGUI) {
        String ilosc = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Ilość wyporzyczonych książek")
                .setValidationPattern(Pattern.compile("[0-9]*"),"wpisz ilość wyporzyczonych książek")
                .build()
                .showDialog(textGUI);
        int iloscwyporz = Integer.parseInt(ilosc);
        return iloscwyporz;
    }

}
