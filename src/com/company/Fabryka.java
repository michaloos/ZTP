package com.company;

//nie wiem właśnie czy to można nazwać metodą fabrykującą
class Fabryka {

    public static Student dodajStudenta(String imie, String nazwisko,int nr,int rok, int ilosc){
        return new Student(imie,nazwisko,nr,rok,ilosc);
    }
    public static Book dodajksiazke(String tytul, String autor, int rok, int cena, int ilosc){
        return new Book(tytul,autor,rok,cena,ilosc);
    }
}

