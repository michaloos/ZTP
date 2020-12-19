package com.company;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.List;

interface Element{
    public void dodaj(WindowBasedTextGUI textGUI, KsiegarniaSingleton ksiegarniaSingleton,
                      List<Student> listastudentow, List<Book> listaksiazki, Table<String> tablica);
}
