package com.company;

import java.util.ArrayList;
import java.util.List;

public class BuilderStudenci implements  Builder{

    List<Student> listastudentow = new ArrayList<>();
    @Override
    public void createElement() {
        Student student = new Student().Studentfake();
        listastudentow.add(student);
    }

    //budowniczy zwraca listę utworzonych studentów na początku do Directora
    public List<Student> pobierzliste(){
        return listastudentow;
    }
}
