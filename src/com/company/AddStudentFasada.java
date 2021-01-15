package com.company;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;

import javax.swing.*;
import java.util.regex.Pattern;

public class AddStudentFasada implements FasadaToAdd{

    //proszenie użytkownika o imię bo dodajemy studenta w trybie tekstowym
    @Override
    public String dodaj_imie_tytul(WindowBasedTextGUI textGUI) {
        return new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Imie")
                .setValidationPattern(Pattern.compile("[a-zA-Z ]*"),"wpisz imie")
                .build()
                .showDialog(textGUI);
    }

    //proszenie użytownika o nazwisko studenta w trybie tekstowym
    @Override
    public String dodaj_nazwisko_autor(WindowBasedTextGUI textGUI) {
        return new TextInputDialogBuilder()
                .setTitle("Dodaj")
                .setDescription("Nazwisko")
                .setValidationPattern(Pattern.compile("[a-zA-Z ]*"),"wpisz nazwisko")
                .build()
                .showDialog(textGUI);
    }

    //proszenie użytownika o numer indeksu studenta w trybie tekstowym
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

    //proszenie użytownika o rok studiów studenta w trybie tekstowym
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

    //proszenie użytownika o ilość wyporzyczonych książek studenta w trybie tekstowym
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

    //proszenie użytkownika o imię bo dodajemy studenta w trybie graficznym
    @Override
    public String dodaj_imie_tytulGUI(JFrame frame) {
        return JOptionPane.showInputDialog(frame, "Podaj imię studenta do dodania:");
    }

    //proszenie użytownika o nazwisko studenta w trybie graficznym
    @Override
    public String dodaj_nazwisko_autorGUI(JFrame frame) {
        return JOptionPane.showInputDialog(frame, "Podaj nazwisko studenta do dodania:");
    }

    //proszenie użytownika o numer indeksu studenta w trybie graficznym
    @Override
    public int dodaj_indeks_rokGUI(JFrame frame) {
        String indeks_string = JOptionPane.showInputDialog(frame, "Podaj numer indeksu studenta do dodania:");
        return Integer.parseInt(indeks_string);
    }

    //proszenie użytownika o rok studiów studenta w trybie graficznym
    @Override
    public int dodaj_rok_studiow_cenaGUI(JFrame frame) {
        String rok_studiow = JOptionPane.showInputDialog(frame, "Podaj rok studiów studenta do dodania:");
        return Integer.parseInt(rok_studiow);
    }

    //proszenie użytownika o ilość wyporzyczonych książek studenta w trybie graficznym
    @Override
    public int dodaj_iloscwyp_ilosc_nastanieGUI(JFrame frame) {
        String ilosc_wyporz = JOptionPane.showInputDialog(frame, "Podaj ilość wyporzyczonych książek\nprzez studenta do dodania:");
        return Integer.parseInt(ilosc_wyporz);
    }

}
