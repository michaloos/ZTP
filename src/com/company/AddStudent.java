package com.company;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.List;

public class AddStudent implements Element{

    @Override
    public void dodaj(WindowBasedTextGUI textGUI, KsiegarniaSingleton ksiegarniaSingleton,
                      List<Student> listastudentow, List<Book> listaksiazki , Table<String> tablica, Label stanlabel,Ksiegarnia ksiegarnia) {
         new Polacz().dodajstudenta(textGUI,tablica,listastudentow);
    }
}
