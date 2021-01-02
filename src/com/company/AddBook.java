package com.company;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.List;

public class AddBook implements Element{

    @Override
    public void dodaj(WindowBasedTextGUI textGUI, List<Student> listastudentow, List<Book> listaksiazki ,
                      Table<String> tablica, Label stanlabel,Ksiegarnia ksiegarnia) {
        new Polacz().dodajksiakze(textGUI, tablica, listaksiazki,stanlabel,ksiegarnia);
    }
}
