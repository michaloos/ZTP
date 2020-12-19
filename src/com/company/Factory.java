package com.company;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.List;

interface Element{
    public void dodaj(WindowBasedTextGUI textGUI,KsiegarniaSingleton ksiegarniaSingleton,
                      List<Student> listastudentow,List<Book> listaksiazki , Table<String> tablica);
}

class AddBook implements Element{

    @Override
    public void dodaj(WindowBasedTextGUI textGUI,KsiegarniaSingleton ksiegarniaSingleton,
                      List<Student> listastudentow,List<Book> listaksiazki , Table<String> tablica) {
        new Polacz().dodajksiakze(textGUI, ksiegarniaSingleton, tablica, listaksiazki);
    }
}

class AddStudent implements Element{

    @Override
    public void dodaj(WindowBasedTextGUI textGUI,KsiegarniaSingleton ksiegarniaSingleton,
                      List<Student> listastudentow,List<Book> listaksiazki , Table<String> tablica) {
         new Polacz().dodajstudenta(textGUI,tablica,listastudentow);
    }
}

class Factory{
    public Element getElement(String elementType){
        if(elementType == null){
            return null;
        }
        if(elementType.equalsIgnoreCase("Student")){
            return new AddStudent();
        }else if(elementType.equalsIgnoreCase("Book")){
            return new AddBook();
        }
        else{
            return null;
        }
    }
}
