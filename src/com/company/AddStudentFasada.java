package com.company;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;

import java.util.regex.Pattern;

public class AddStudentFasada implements FasadaToAdd{

    //proszenie użytkownika o imię bo dodajemy studenta
    @Override
    public String dodaj_imie_tytul(WindowBasedTextGUI textGUI) {
        return new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Imie")
                .setValidationPattern(Pattern.compile("[a-zA-Z ]*"),"wpisz imie")
                .build()
                .showDialog(textGUI);
    }

    //proszenie użytownika o nazwisko studenta
    @Override
    public String dodaj_nazwisko_autor(WindowBasedTextGUI textGUI) {
        return new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Nazwisko")
                .setValidationPattern(Pattern.compile("[a-zA-Z ]*"),"wpisz nazwisko")
                .build()
                .showDialog(textGUI);
    }

    //proszenie użytownika o numer indeksu studenta
    @Override
    public int dodaj_indeks_rok(WindowBasedTextGUI textGUI) {
        String indeks = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Numer Indeksu")
                .setValidationPattern(Pattern.compile("[0-9]*"),"wpisz indeks")
                .build()
                .showDialog(textGUI);
        return Integer.parseInt(indeks);
    }

    //proszenie użytownika o rok studiów studenta
    @Override
    public int dodaj_rok_studiow_cena(WindowBasedTextGUI textGUI) {
        String rok = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Rok Studiow")
                .setValidationPattern(Pattern.compile("[0-9]*"),"wpisz rok studiow")
                .build()
                .showDialog(textGUI);
        return Integer.parseInt(rok);
    }

    //proszenie użytownika o ilość wyporzyczonych książek studenta
    @Override
    public int dodaj_iloscwyp_ilosc_nastanie(WindowBasedTextGUI textGUI) {
        String ilosc = new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Ilość wyporzyczonych książek")
                .setValidationPattern(Pattern.compile("[0-9]*"),"wpisz ilość wyporzyczonych książek")
                .build()
                .showDialog(textGUI);
        return Integer.parseInt(ilosc);
    }

}
