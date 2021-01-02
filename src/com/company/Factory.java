package com.company;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.List;

//tworzenie nowego obiektu, sprawdzamy czy użytkownik wybrał utworznie nowego studenta
//czy też dodanie nowej książki, ma od tego dwa przyciski, każdy odpowiada za jeden obiekt
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
