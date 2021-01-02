package com.company;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.List;

import static com.googlecode.lanterna.gui2.dialogs.MessageDialogButton.OK;

public interface WypiszInfo {
    void wypisz(WindowBasedTextGUI textGUI, Table<String> tabela, int i, List<Student> lista_studentow,List<Book> lista_ksiazek);
}

class WypiszStudenta implements WypiszInfo {

    @Override
    public void wypisz(WindowBasedTextGUI textGUI, Table<String> tabela, int i, List<Student> dane_studentow ,List<Book> lista_ksiazek) {
        String imie = dane_studentow.get(i).getName();
        String nazwisko = dane_studentow.get(i).getNazwisko();
        String indeks = Integer.toString(dane_studentow.get(i).getNr_ideksu());
        String rok_studiow = Integer.toString(dane_studentow.get(i).getRok_studiow());
        String ilosc_wuporzyczen = Integer.toString(dane_studentow.get(i).getIlosc_wyporz_ksiazek());
        new MessageDialogBuilder()
                .setTitle("Dane studenta")
                .setText("Imię: " + imie + "" +
                        "\nNazwisko: " + nazwisko + "" +
                        "\nNumer Indeksu:  " + indeks + "" +
                        "\nRok studiów: " + rok_studiow + "" +
                        "\nIlość wyporzyczonych książek:  " + ilosc_wuporzyczen)
                .addButton(OK)
                .build()
                .showDialog(textGUI);

    }
}

class WypiszKsiazke implements WypiszInfo {

    @Override
    public void wypisz(WindowBasedTextGUI textGUI, Table<String> tabela, int j,List<Student> lista_studentow,List<Book> ksiazki) {
        String tytul = ksiazki.get(j).getTitle();
        String autor = ksiazki.get(j).getAutor();
        String rok = Integer.toString(ksiazki.get(j).getYear());
        String cena = Double.toString(ksiazki.get(j).getPrize());
        String count = Integer.toString(ksiazki.get(j).getCount());
        new MessageDialogBuilder()
                .setTitle("Dane książki")
                .setText("Tytul książki: " + tytul + "" +
                        "\nAutor: " + autor + "" +
                        "\nRok wydania:  " + rok + "" +
                        "\nCena: " + cena + "" +
                        "\nDostępna ilość:  " + count)
                .addButton(OK)
                .build()
                .showDialog(textGUI);
    }
}