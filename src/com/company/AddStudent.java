package com.company;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.table.Table;

import javax.swing.*;
import java.util.List;

//połączenie fasady z metodą fabrykującą do dodawania studenta
public class AddStudent implements Element{

    //tryb tekstowy
    @Override
    public void dodaj(WindowBasedTextGUI textGUI, List<Student> listastudentow, List<Book> listaksiazki ,
                      Table<String> tablica, Label stanlabel,Ksiegarnia ksiegarnia) {
         new Polacz().dodajstudenta(textGUI,tablica,listastudentow);
    }

    //tryb graficzny
    @Override
    public void dodajGUI(DefaultListModel<String> lista, List<Student> dane_studentow, JFrame frame,
                         List<Book> ksiazki, Ksiegarnia ksiegarnia, JLabel jLabelstan) {
        new Polacz().dodajStudentaGUI(frame,dane_studentow,lista);
    }
}
