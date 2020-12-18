package com.company;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.io.IOException;

class Terminala {
    public static void Terminal() throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();

        Screen screen = new TerminalScreen(terminal);
        screen.doResizeIfNecessary();
        screen.startScreen();

        final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        // Create panel to hold components
        Panel panel = new Panel();;
        panel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        Panel lewy = new Panel();
        panel.addComponent(lewy.withBorder(Borders.singleLine("Dostępne opcje")));
        lewy.setLayoutManager(new GridLayout(2));

        Panel prawy = new Panel();
        prawy.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(prawy.withBorder(Borders.singleLine("Lista studentów i książek")));

        //użycie singletona jedne jedyne użycie get.instance
        KsiegarniaSingleton ksiegarniaSingleton = KsiegarniaSingleton.getInstance();

        //labele w którcyh nic nie ma aby troche uporządkować wygląd
        final Label spacja = new Label(" ");
        final Label spacja2 = new Label(" ");
        final Label informacja = new Label("Naciśnij enter na wybranej pozycji\naby zobaczeć dokładne informacje");
        final Label spacja3 = new Label(" ");

        //inicjalizacja tabel do wyświetlania informacji
        Table<String> table_student = new Table<String>("Imię", "Nazwisko", "Nr indeksu");
        Table<String> table2_ksiazka = new Table<String>("Tytuł","Autor","Cena");
        lewy.addComponent(informacja);
        lewy.addComponent(spacja3);
        prawy.addComponent(table_student);
        prawy.addComponent(spacja);
        prawy.addComponent(table2_ksiazka);

        Label stanlabel = new Label("Stan magazynu");

        table_student.setVisibleRows(7);
        table2_ksiazka.setVisibleRows(7);

        BuilderKsiazki builderKsiazki = new BuilderKsiazki();
        BuilderStudenci builderStudenci = new BuilderStudenci();

        //budowanie dwóch list ze studentami i książkami
        for(int i=0;i<50;i++){
            builderKsiazki.createElement();
            builderStudenci.createElement();
        }
        ksiegarniaSingleton.update_miejsca(-50);//no bo tyle jest początkowych książek
        List<Student> dane_studentow = new ArrayList<Student>(builderStudenci.pobierzliste());
        List<Book> ksiazki = new ArrayList<Book>(builderKsiazki.pobierzliste());
        //dodawanie wstępnych studentów
        for (Student student : dane_studentow) {
            String nr = Integer.toString(student.getNr_ideksu());
            table_student.getTableModel().addRow(student.getName(), student.getNazwisko(), nr);
        }
        //dodawanie wstępnych książek
        for (Book ksiazka : ksiazki){
            String prize = Double.toString(ksiazka.getPrize());
            table2_ksiazka.getTableModel().addRow(ksiazka.getTitle(), ksiazka.getAutor(), prize);
            //początkowe inicjowanie koloru wskaźnika
            if(ksiegarniaSingleton.ilosc_wolynch_miejsc() > ksiegarniaSingleton.ilosc_miejsc_na_poczotku()*0.70){
                stanlabel.setBackgroundColor(new TextColor.RGB(0,255,0));
            }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() > ksiegarniaSingleton.ilosc_miejsc_na_poczotku()*0.30){
                stanlabel.setBackgroundColor(new TextColor.RGB(255,255,0));
            }else{
                stanlabel.setBackgroundColor(new TextColor.RGB(255,0,0));
            }
        }

        //pełne informacje na temant studenta
        table_student.setSelectAction(new Runnable() {
            @Override
            public void run() {
                int i = table_student.getSelectedRow();
                String imie = dane_studentow.get(i).getName();
                String nazwisko = dane_studentow.get(i).getNazwisko();
                String indeks = Integer.toString(dane_studentow.get(i).getNr_ideksu());
                String rok_studiow = Integer.toString(dane_studentow.get(i).getRok_studiow());
                String ilosc_wuporzyczen = Integer.toString(dane_studentow.get(i).getIlosc_wyporz_ksiazek());
                new WypiszStudenta().wypisz(textGUI,imie,nazwisko,indeks,rok_studiow,ilosc_wuporzyczen);
            }
        });

        //pełne informacje na temat książki
        table2_ksiazka.setSelectAction(new Runnable() {
            @Override
            public void run() {
                int j = table2_ksiazka.getSelectedRow();
                String tytul = ksiazki.get(j).getTitle();
                String autor = ksiazki.get(j).getAutor();
                String rok = Integer.toString(ksiazki.get(j).getYear());
                String cena = Double.toString(ksiazki.get(j).getPrize());
                String count = Integer.toString(ksiazki.get(j).getCount());
                new WypiszKsiazke().wypisz(textGUI,tytul,autor,rok,cena,count);
            }
        });

        //dodawanie studenta
        new Button("Dodaj studenta", new Runnable() {
            @Override
            public void run() {
                Polacz polacz = new Polacz();
                Student student;
                student = polacz.dodajstudenta(textGUI);
                String indeks = Integer.toString(student.getNr_ideksu());
                dane_studentow.add(student);
                table_student.getTableModel().addRow(student.getName(),student.getNazwisko(),indeks);
            }
        }).addTo(lewy);

        //usuwanie studenta
        new Button("Usun studenta", new Runnable() {
            @Override
            public void run() {
                new Student().UsunStudenta(textGUI,dane_studentow,table_student);
            }
        }).addTo(lewy);

        //dodawanie książki
        new Button("Dodaj książkę", new Runnable() {
            //inicjalizacja stanu
            Stan stan;
            @Override
            public void run() {
                Polacz polacz = new Polacz();
                Book book;
                book = polacz.dodajksiakze(textGUI,ksiegarniaSingleton);
                if(book != null){
                    ksiazki.add(book);
                    String cena = Integer.toString(book.getPrize());
                    table2_ksiazka.getTableModel().addRow(book.getTitle(),book.getAutor(),cena);
                    if(ksiegarniaSingleton.ilosc_wolynch_miejsc() > ksiegarniaSingleton.ilosc_miejsc_na_poczotku()*0.70){
                        stan = new StanPrawiePelnoMiejsca(stanlabel);
                    }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() > ksiegarniaSingleton.ilosc_miejsc_na_poczotku()*0.30){
                        stan = new StanUwazajNailosc(stanlabel);
                    }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() == 0){
                        stan = new StanNieMaMiejscaNaNoweKsizki(stanlabel);
                    }else if(ksiegarniaSingleton.ilosc_wolynch_miejsc() == ksiegarniaSingleton.ilosc_miejsc_na_poczotku()) {
                        stan = new StanBrakKsiazek(stanlabel);
                    }else{
                        stan = new StanBrakMiejsca(stanlabel);
                    }
                    stan.color();
                    stan.tekst();
                }
            }
        }).addTo(lewy);

        //usuwanie książki (to jest wszystkie egzemplarze) za pomocą tytułu i autora
        new Button("Usun ksiązkę", new Runnable() {
            @Override
            public void run() {
                new Book().UsunKsiazkeIWszystkieJejEgzemplarze(textGUI,ksiazki,table2_ksiazka,ksiegarniaSingleton);
            }
        }).addTo(lewy);

        //wyporzyczenie / usuwanie jednego z dostępnych egzemplarzy
        new Button("Wyporzycz książkę", new Runnable() {
            @Override
            public void run() {
                new Book().WyporzyczKsiazke(textGUI,ksiazki,ksiegarniaSingleton);
            }
        }).addTo(lewy);

        //zwracanie książki
        new Button("Zwróć książkę", new Runnable() {
            @Override
            public void run() {
                new Book().ZwrotKsiazki(textGUI,ksiazki,ksiegarniaSingleton);
            }
        }).addTo(lewy);

        //kupno książki
        new Button("Kup książkę", new Runnable() {
            @Override
            public void run() {
                new Book().KupnoKsiazki(textGUI,ksiazki,ksiegarniaSingleton);
            }
        }).addTo(lewy);
        lewy.addComponent(spacja2);

        //szukanie STUDENTÓW
        Label indeks_szukaj = new Label("Wyszukaj studenta po \nnumerze indeksu");
        lewy.addComponent(indeks_szukaj);
        TextBox szukaj_indeks = new TextBox().setValidationPattern(Pattern.compile("[0-9]*")).addTo(lewy);

        new Button("Wyszukaj", new Runnable() {
            @Override
            public void run() {
                new Student().SzukajStudenta(textGUI,szukaj_indeks,table_student,dane_studentow);
            }
        }).addTo(lewy);

        //sortowanie listy studentów
        new Button("Sort studentów po imieniu", new Runnable() {
            @Override
            public void run() {
                assert false;
                List<Student> listasortimie = new ArrayList<>(new Student().Sortuj(dane_studentow,true));
                table_student.getTableModel().clear();
                for (Student student : listasortimie) {
                    String nr = Integer.toString(student.getNr_ideksu());
                    table_student.getTableModel().addRow(student.getName(), student.getNazwisko(), nr);
                }
            }
        }).addTo(lewy);

        new Button("Sort studentów po indeksie", new Runnable() {
            @Override
            public void run() {
                assert false;
                List<Student> listasortindeks = new ArrayList<>(new Student().Sortuj(dane_studentow,false));
                table_student.getTableModel().clear();
                for (Student student : listasortindeks) {
                    String nr = Integer.toString(student.getNr_ideksu());
                    table_student.getTableModel().addRow(student.getName(), student.getNazwisko(), nr);
                }
            }
        }).addTo(lewy);

        new Button("Zakończ szukanie", new Runnable() {
            @Override
            public void run() {
                szukaj_indeks.setText("");
                table_student.getTableModel().clear();

                //przywracanie studentów do tabeli
                for (Student student : dane_studentow) {
                    String nr = Integer.toString(student.getNr_ideksu());
                    table_student.getTableModel().addRow(student.getName(), student.getNazwisko(), nr);
                }
            }
        }).addTo(lewy);

        //wyszukiwanie KSIĄŻEK
        Label szukaj_tytul = new Label("Wyszukaj książkę za \npomocą tytułu");
        lewy.addComponent(szukaj_tytul);
        TextBox tytul_szukaj = new TextBox().setValidationPattern(Pattern.compile("[a-zA-Z ]*")).addTo(lewy);


        new Button("Wyszukaj", new Runnable() {
            @Override
            public void run() {
                new Book().SzukajKsiazki(textGUI,table2_ksiazka,ksiazki,tytul_szukaj);
            }
        }).addTo(lewy);

        //usuwanie filtrów książek powrót do normalnej tabeli
        new Button("Zakończ szukanie", new Runnable() {
            @Override
            public void run() {
                tytul_szukaj.setText("");
                table2_ksiazka.getTableModel().clear();

                //przywracanie studentów do tabeli
                for (Book ksiazka : ksiazki){
                    String prize = Double.toString(ksiazka.getPrize());
                    table2_ksiazka.getTableModel().addRow(ksiazka.getTitle(), ksiazka.getAutor(), prize);
                }
            }
        }).addTo(lewy);

        // Create window to hold the panel
        BasicWindow window = new BasicWindow();
        window.setComponent(panel.withBorder(Borders.singleLine("Panel Główny")));

        new Button("Ilość wolnych miejsc", new Runnable() {
            @Override
            public void run() {
                ksiegarniaSingleton.wolne_miejsce(textGUI);
            }
        }).addTo(lewy);

        //dodanie labelu stanu do ilosci w magazynie
        stanlabel.addTo(lewy);


        //przycisk do zamknięcia terminala
        new Button("Wyjście", new Runnable() {
            @Override
            public void run() {
                try {
                    terminal.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addTo(lewy);

        // Create gui and start gui
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(),
                                 new EmptySpace(TextColor.ANSI.BLUE));
        gui.addWindowAndWait(window);
    }
}
