package com.company;

import java.util.ArrayList;
import java.util.List;

public class SortowaniePoIndeksie implements Strategia{

    //zwracana jest posortowana lista względem indeksu studenta i wrzucana do listy w interfejsie
    // czy to w trybie tekstowym czy graficznym
    @Override
    public List<Student> sortowanie(List<Student> lista) {
        List<Student> listasort = new ArrayList<>(lista);
        listasort.sort(Student.IndeksCompare);
        return listasort;
    }
}
