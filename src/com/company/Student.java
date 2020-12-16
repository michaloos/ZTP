package com.company;

import com.github.javafaker.Faker;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.*;

public class Student {
    private String name;
    private String nazwisko;
    private int nr_ideksu;
    private int rok_studiow;
    private int ilosc_wyporz_ksiazek;

    private Strategia strategia;

    public Student(String name, String nazwisko, int numer, int rok_studiow, int ilosc_wyporz_ksiazek){
        this.name = name;
        this.nazwisko = nazwisko;
        this.nr_ideksu = numer;
        this.rok_studiow = rok_studiow;
        this.ilosc_wyporz_ksiazek = ilosc_wyporz_ksiazek;
    }

    public Student() {
        name=null;
        nazwisko=null;
        nr_ideksu=0;
        rok_studiow=0;
        ilosc_wyporz_ksiazek=0;
    }

    public String getName(){
        return name;
    }

    public String getNazwisko(){
        return nazwisko;
    }

    public int getNr_ideksu(){
        return nr_ideksu;
    }

    public int getRok_studiow(){
        return rok_studiow;
    }

    public int getIlosc_wyporz_ksiazek() { return ilosc_wyporz_ksiazek; }

    public Student Studentfake(){
        Random random = new Random();
        Faker faker = new Faker();
        String name = faker.name().firstName();//losowe imię
        String surname = faker.name().lastName();//losowe nazwisko
        int indesk = random.nextInt(10000);
        int ilosc_wyporz_ksiazek = random.nextInt(10);
        int rok_studiow = random.nextInt(6);

        return new Student(name,surname,indesk,rok_studiow,ilosc_wyporz_ksiazek);
    }

    public static Comparator<Student>  IndeksCompare = new Comparator<Student>(){
        @Override
        public int compare(Student o1, Student o2) {
            int ind1 = o1.getNr_ideksu();
            int ind2 = o2.getNr_ideksu();
            return ind1 - ind2;
        }
    };

    public static Comparator<Student> ImieCompare = new Comparator<Student>() {
        @Override
        public int compare(Student o1, Student o2) {
            String imie1 = o1.getName().toUpperCase();
            String imie2 = o2.getName().toUpperCase();
            return imie1.compareTo(imie2);
        }
    };

    public List<Student> Sortuj(List<Student> lista, boolean i){
        SortowaniePoImieniu strategiaimie = new SortowaniePoImieniu();
        SortowaniePoIndeksie strategiaindeks = new SortowaniePoIndeksie();
        if(i){
            return new ArrayList<>(strategiaimie.sortowanie(lista));
        }else{
            return new ArrayList<>(strategiaindeks.sortowanie(lista));
        }
    }

    //narazie iterator nie działa, ale z tego co rozumiem w tym
    // przypadku jakim jak chciałem go użyć to by i tak niedziałał
    /*static class ZwyklyIterator implements Iterator<Student> {

        List<Student> list;
        private int indeks;
        private int i;
        public ZwyklyIterator(List<Student> lista,int numer){
            list = new ArrayList<>(lista);
            this.indeks = numer;
            this.i = 0;
        }

        @Override
        public boolean hasNext() {
            if(list.get(i) == null){
                System.out.println(0);
                return false;
            }else {
                System.out.println(1);
                return true;
            }
        }

        @Override
        public Student next() {
            if(list.get(i).getNr_ideksu() == indeks){
                System.out.println(2);
                System.out.println(list.get(i).getNr_ideksu());
                System.out.println(indeks);
                System.out.println(i);
                System.out.println(list.get(i));
                return list.get(i);
            }else{
                i++;
                System.out.println(33);
                return null;
            }
        }
    }
    public Iterator<Student> zwyklyIterator(List<Student> lista,int numer){return new ZwyklyIterator(lista, numer);}*/
}
