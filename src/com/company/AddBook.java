package com.company;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.table.Table;

import javax.swing.*;
import java.util.List;

//połączenie fasady z metodą fabrykującą do dodawania książki
public class AddBook implements Element{

    //tryb tekstowy
    @Override
    public void dodaj(WindowBasedTextGUI textGUI, List<Student> listastudentow, List<Book> listaksiazki ,
                      Table<String> tablica, Label stanlabel,Ksiegarnia ksiegarnia) {
        new Polacz().dodajksiakze(textGUI, tablica, listaksiazki,stanlabel,ksiegarnia);
    }

    //tryb graficzny
    @Override
    public void dodajGUI(DefaultListModel<String> lista, List<Student> dane_studentow, JFrame frame, List<Book> ksiazki, Ksiegarnia ksiegarnia,
                         JLabel jLabelstan) {
        new Polacz().dodajKsiazkeGUI(frame,ksiazki,lista,jLabelstan,ksiegarnia);
    }
}
