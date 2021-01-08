package com.company;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.table.Table;

import javax.swing.*;
import java.util.List;

interface Element{
    void dodaj(WindowBasedTextGUI textGUI, List<Student> listastudentow,
               List<Book> listaksiazki, Table<String> tablica, Label stanlabel,Ksiegarnia ksiegarnia);
    void dodajGUI(DefaultListModel<String> lista, List<Student> dane_studentow, JFrame frame, List<Book> ksiazki,Ksiegarnia ksiegarnia, JLabel jLabelstan);
}
