package com.company;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

import static com.googlecode.lanterna.gui2.dialogs.MessageDialogButton.OK;

public class Ksiegarnia {
    private int miejsce_w_magazynie_i_na_polkach; // ile można jeszcze upchać książek do księgarni, nie to ile jest książek tylko
                                                  // ile jeszcze zostało wolnego miejsca
    private static Ksiegarnia ksiegarnia = new Ksiegarnia();
    private Ksiegarnia(){
        this.miejsce_w_magazynie_i_na_polkach = 5000;
    }
    public static Ksiegarnia getInstance(){
        return ksiegarnia;
    }
    public int ilosc_miejsc_na_poczotku(){
        return 5000;
    } //ustawanie ile księgarnia ma miejsca na początku
    public int ilosc_wolynch_miejsc(){
        return miejsce_w_magazynie_i_na_polkach;
    } //zwraca ilość jeszcze dostępnego miejsca w księgarni

    //wyświetla informacje na temat ile jest jeszcze wolnego miejsca na książki
    public void wolne_miejsce(WindowBasedTextGUI textGUI){
        new MessageDialogBuilder()
                .setTitle("Potwierdzenie")
                .setText("Tyle jest jeszcze miejsca:\n" + miejsce_w_magazynie_i_na_polkach)
                .addButton(OK)
                .build()
                .showDialog(textGUI);
    }

    //zmiana stanu, nie połączony z Labelem, to są dwa "Stany"
    private void zmianaStanu(){
        int wolne_miejsca = ksiegarnia.ilosc_wolynch_miejsc();
        int miejsca_poczotek = ksiegarnia.ilosc_miejsc_na_poczotku();
        if(wolne_miejsca == 0){
            ksiegarnia.stan2 = StanPelno.getInstance(); //System.out.println("stanpelno");
        }else if(wolne_miejsca < miejsca_poczotek * 0.15 && wolne_miejsca > 0){
            ksiegarnia.stan2 = StanPrawiePelno.getInstance(); //System.out.println("stanprawiepelno");
        }else if(wolne_miejsca >= miejsca_poczotek * 0.15 && wolne_miejsca <= miejsca_poczotek * 0.85){
            ksiegarnia.stan2 = StanZbalansowany.getInstance(); //System.out.println("stanzbalansowany");
        }else if(wolne_miejsca > miejsca_poczotek * 0.85 && wolne_miejsca < miejsca_poczotek){
            ksiegarnia.stan2 = StanPrawiePusto.getInstance(); //System.out.println("stanprawiepusto");
        }else if(wolne_miejsca == miejsca_poczotek){
            ksiegarnia.stan2 = StanPusto.getInstance(); //System.out.println("stanpusto");
        }
    }


    //nie ma miejsca na nowe książki, książek jest bardzo dużo
    private static class StanPelno extends Stan2{
        private static StanPelno instancja = new StanPelno();
        private StanPelno(){}
        public static Stan2 getInstance(){
            return instancja;
        }

        @Override
        public void zwieksz_ilosc_miejsca(int ile, Ksiegarnia ksiegarnia) {
            ksiegarnia.miejsce_w_magazynie_i_na_polkach = ksiegarnia.miejsce_w_magazynie_i_na_polkach + ile;
            ksiegarnia.zmianaStanu();
        }

        @Override
        public void zmiejsz_ilosc_miejsca(int ile, Ksiegarnia ksiegarnia) {
            ksiegarnia.zmianaStanu();
        }
    }
    //zaraz zabraknie miejsca na nowe książki
    private static class StanPrawiePelno extends Stan2{
        private static StanPrawiePelno instancja = new StanPrawiePelno();
        private StanPrawiePelno(){}
        public static Stan2 getInstance(){
            return instancja;
        }

        @Override
        public void zwieksz_ilosc_miejsca(int ile, Ksiegarnia ksiegarnia) {
            ksiegarnia.miejsce_w_magazynie_i_na_polkach = ksiegarnia.miejsce_w_magazynie_i_na_polkach + ile;
            ksiegarnia.zmianaStanu();
        }

        @Override
        public void zmiejsz_ilosc_miejsca(int ile, Ksiegarnia ksiegarnia) {
            ksiegarnia.miejsce_w_magazynie_i_na_polkach = ksiegarnia.miejsce_w_magazynie_i_na_polkach - ile;
            ksiegarnia.zmianaStanu();
        }
    }
    //w księgarni nie ma prawie książek, jest ich bardzo mało, bardzo dużo miejsca na nowe książki
    private static class StanPrawiePusto extends Stan2{
        private static StanPrawiePusto instancja = new StanPrawiePusto();
        private StanPrawiePusto(){}
        public static Stan2 getInstance(){
            return instancja;
        }

        @Override
        public void zwieksz_ilosc_miejsca(int ile, Ksiegarnia ksiegarnia) {
            ksiegarnia.miejsce_w_magazynie_i_na_polkach = ksiegarnia.miejsce_w_magazynie_i_na_polkach + ile;
            ksiegarnia.zmianaStanu();
        }

        @Override
        public void zmiejsz_ilosc_miejsca(int ile, Ksiegarnia ksiegarnia) {
            ksiegarnia.miejsce_w_magazynie_i_na_polkach = ksiegarnia.miejsce_w_magazynie_i_na_polkach - ile;
            ksiegarnia.zmianaStanu();
        }
    }
    //brak jakich kolwiek książek w księgarni
    private static class StanPusto extends Stan2{
        private static StanPusto instancja = new StanPusto();
        private StanPusto(){}
        public static Stan2 getInstance(){
            return instancja;
        }

        @Override
        public void zwieksz_ilosc_miejsca(int ile, Ksiegarnia ksiegarnia) {
            ksiegarnia.zmianaStanu();
        }

        @Override
        public void zmiejsz_ilosc_miejsca(int ile, Ksiegarnia ksiegarnia) {
            ksiegarnia.miejsce_w_magazynie_i_na_polkach = ksiegarnia.miejsce_w_magazynie_i_na_polkach - ile;
            ksiegarnia.zmianaStanu();
        }
    }
    //ilość książęk w księgarni na umiarkowanym poziomie, jest miejsce na nowe, ale także są w niej inne książki
    private static class StanZbalansowany extends Stan2{
        private static StanZbalansowany instancja = new StanZbalansowany();
        private StanZbalansowany(){}
        public static Stan2 getInstance(){
            return instancja;
        }

        @Override
        public void zwieksz_ilosc_miejsca(int ile, Ksiegarnia ksiegarnia) {
            ksiegarnia.miejsce_w_magazynie_i_na_polkach = ksiegarnia.miejsce_w_magazynie_i_na_polkach + ile;
            ksiegarnia.zmianaStanu();
        }

        @Override
        public void zmiejsz_ilosc_miejsca(int ile, Ksiegarnia ksiegarnia) {
            ksiegarnia.miejsce_w_magazynie_i_na_polkach = ksiegarnia.miejsce_w_magazynie_i_na_polkach - ile;
            ksiegarnia.zmianaStanu();
        }
    }

    private final Stan2 STANPUSTO = StanPusto.getInstance();
    private Stan2 stan2 = STANPUSTO;

    public void zwieksz_ilosc_wolnego_miejsca(int ile){stan2.zwieksz_ilosc_miejsca(ile,this);} //zwiekszam miejsce czyli "zabieram" książki
    public void zmiejsz_ilosc_wolnego_miejsca(int ile){stan2.zmiejsz_ilosc_miejsca(ile,this);} //zmiejszam miejsce czyli "dodaje" książki
}

abstract class Stan2{
    public Stan2(){}
    public abstract void zwieksz_ilosc_miejsca(int ile,Ksiegarnia ksiegarnia);
    public abstract void zmiejsz_ilosc_miejsca(int ile,Ksiegarnia ksiegarnia);
}