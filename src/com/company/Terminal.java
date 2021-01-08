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

import static com.company.Swing.SwingGUI;

class Terminala {

    public static List<Student> dane_studentow;
    public static List<Book> ksiazki;

    public static void Terminal() throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();

        Screen screen = new TerminalScreen(terminal);
        screen.doResizeIfNecessary();
        screen.startScreen();

        final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        // tworzenie panelu do trzymania innych komponentów
        Panel panel = new Panel();;
        panel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        //okno dzieli się na dwa panele, w jednym są tabele z listą studentów oraz listą książek
        //a w drugim panule przyciski oraz inne komponetu do obsługi aplikacji
        Panel lewy = new Panel();
        panel.addComponent(lewy.withBorder(Borders.singleLine("Dostępne opcje")));
        lewy.setLayoutManager(new GridLayout(2));

        Panel prawy = new Panel();
        prawy.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(prawy.withBorder(Borders.singleLine("Lista studentów i książek")));

        Ksiegarnia ksiegarnia = Ksiegarnia.getInstance();

        //labele w którcyh nic nie ma aby troche uporządkować wygląd
        final Label spacja = new Label(" ");
        final Label spacja2 = new Label(" ");
        final Label informacja = new Label("Naciśnij enter na wybranej pozycji\naby zobaczeć dokładne informacje");
        final Label spacja3 = new Label(" ");

        //inicjalizacja tabel do wyświetlania informacji
        Table<String> table_student = new Table<String>("Imię", "Nazwisko", "Nr indeksu");
        Table<String> table2_ksiazka = new Table<String>("Tytuł","Autor","Cena");

        //dodawanie labeli do komponentu
        lewy.addComponent(informacja);
        lewy.addComponent(spacja3);
        prawy.addComponent(table_student);
        prawy.addComponent(spacja);
        prawy.addComponent(table2_ksiazka);

        Label stanlabel = new Label("Stan magazynu");

        //ustawianie dla dwóch tabel ile ma być wyświetlanych wierszy
        table_student.setVisibleRows(7);
        table2_ksiazka.setVisibleRows(7);

        BuilderKsiazki builderKsiazki = new BuilderKsiazki();
        BuilderStudenci builderStudenci = new BuilderStudenci();

        //budowanie dwóch list ze studentami i książkami
        for(int i=0;i<50;i++){
            builderKsiazki.createElement();
            builderStudenci.createElement();
        }

        dane_studentow = new ArrayList<Student>(builderStudenci.pobierzliste());
        ksiazki = new ArrayList<Book>(builderKsiazki.pobierzliste());
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
            ksiazka.inicjalizacja_labela(ksiegarnia,stanlabel);
        }

        //pełne informacje na temant studenta
        table_student.setSelectAction(new Runnable() {
            @Override
            public void run() {
                int i = table_student.getSelectedRow();
                new WypiszStudenta().wypisz(textGUI,table_student,i,dane_studentow,ksiazki);
            }
        });

        //pełne informacje na temat książki
        table2_ksiazka.setSelectAction(new Runnable() {
            @Override
            public void run() {
                int j = table2_ksiazka.getSelectedRow();
                new WypiszKsiazke().wypisz(textGUI,table_student,j,dane_studentow,ksiazki);
            }
        });

        //do metody fabrykującej
        Factory elementfactory = new Factory();

        //dodawanie studenta
        new Button("Dodaj studenta", new Runnable() {
            @Override
            public void run() {
                Element element = elementfactory.getElement("Student");
                element.dodaj(textGUI,dane_studentow,ksiazki,table_student,stanlabel,ksiegarnia);
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
            @Override
            public void run() {
                Element element = elementfactory.getElement("Book");
                element.dodaj(textGUI,dane_studentow,ksiazki,table2_ksiazka,stanlabel,ksiegarnia);
            }
        }).addTo(lewy);

        //usuwanie książki (to jest wszystkie egzemplarze) za pomocą tytułu i autora
        new Button("Usun ksiązkę", new Runnable() {
            @Override
            public void run() {
                new Book().UsunKsiazkeIWszystkieJejEgzemplarze(textGUI,ksiazki,table2_ksiazka,stanlabel,ksiegarnia);
            }
        }).addTo(lewy);

        //wyporzyczenie / usuwanie jednego z dostępnych egzemplarzy
        new Button("Wyporzycz książkę", new Runnable() {
            @Override
            public void run() {
                new Book().WyporzyczKsiazke(textGUI,ksiazki,stanlabel,ksiegarnia);
            }
        }).addTo(lewy);

        //zwracanie książki
        new Button("Zwróć książkę", new Runnable() {
            @Override
            public void run() {
                new Book().ZwrotKsiazki(textGUI,ksiazki,ksiegarnia,stanlabel);
            }
        }).addTo(lewy);

        //kupno książki
        new Button("Kup książkę", new Runnable() {
            @Override
            public void run() {
                new Book().KupnoKsiazki(textGUI,ksiazki,ksiegarnia,stanlabel);
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

        //sortowanie listy studentów po imieniu
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

        //sortowanie listy studentów po indeksie
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

        //kończymy szukanie resetujemy "filtry"
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
                ksiegarnia.wolne_miejsce(textGUI);
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

        //zamiana trybu tekstowego na graficzny
        new Button("Zamiana w tryb graficzny", new Runnable() {
            @Override
            public void run() {
                try {
                    terminal.close();
                    SwingGUI();
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
