package com.company;

import java.util.List;

//Strategia służąca do sortowania  listy studentów
public interface Strategia {
    List<Student> sortowanie(List<Student> lista);
}

