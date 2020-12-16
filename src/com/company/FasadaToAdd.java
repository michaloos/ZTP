package com.company;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;

import java.util.regex.Pattern;

public interface FasadaToAdd {
    String dodaj_imie_tytul(WindowBasedTextGUI textGUI);
    String dodaj_nazwisko_autor(WindowBasedTextGUI textGUI);
    int dodaj_indeks_rok(WindowBasedTextGUI textGUI);
    int dodaj_rok_studiow_cena(WindowBasedTextGUI textGUI);
    int dodaj_iloscwyp_ilosc_nastanie(WindowBasedTextGUI textGUI);
}

class Polacz{
    private AddStudentFasada studentFasada = new AddStudentFasada();
    private AddBookFasada bookFasada = new AddBookFasada();

    public Student dodajstudenta(WindowBasedTextGUI textGUI){
        String imie = studentFasada.dodaj_imie_tytul(textGUI);
        String nazwisko = studentFasada.dodaj_nazwisko_autor(textGUI);
        int indeks = studentFasada.dodaj_indeks_rok(textGUI);
        int rok_studiow = studentFasada.dodaj_rok_studiow_cena(textGUI);
        int ilosc_wyporz = studentFasada.dodaj_iloscwyp_ilosc_nastanie(textGUI);
        Student student = Fabryka.dodajStudenta(imie,nazwisko,indeks,rok_studiow,ilosc_wyporz);

        return student;
    }

    public Book dodajksiakze(WindowBasedTextGUI textGUI, KsiegarniaSingleton ksiegarniaSingleton){
        String tytul = bookFasada.dodaj_imie_tytul(textGUI);
        String autor = bookFasada.dodaj_nazwisko_autor(textGUI);
        int rok = bookFasada.dodaj_indeks_rok(textGUI);
        int cena = bookFasada.dodaj_rok_studiow_cena(textGUI);
        int ilosc_na_stanie = bookFasada.dodaj_iloscwyp_ilosc_nastanie(textGUI);
        Book book = Fabryka.dodajksiazke(tytul,autor,rok,cena,ilosc_na_stanie);
        ksiegarniaSingleton.update_miejsca(-ilosc_na_stanie);
        return book;
    }
}