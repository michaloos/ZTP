package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortowaniePoImieniu implements Strategia{

    List<Student> listastudentow = new ArrayList<>();
    @Override
    public List<Student> sortowanie(List<Student> lista) {
        List<Student> listasort = new ArrayList<>(lista);
        listasort.sort(Student.ImieCompare);
        return listasort;
    }
    public List<Student> pobierzliste(){return listastudentow;}
}
