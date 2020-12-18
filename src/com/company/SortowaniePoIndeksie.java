package com.company;

import java.util.ArrayList;
import java.util.List;

public class SortowaniePoIndeksie implements Strategia{

    @Override
    public List<Student> sortowanie(List<Student> lista) {
        List<Student> listasort = new ArrayList<>(lista);
        listasort.sort(Student.IndeksCompare);
        return listasort;
    }
}
