import java.awt.*;
import java.util.*;

public class Lotnisko {


    Random random = new Random();
    String alphabet = "abcdefghijklmnopqrstuvwxyz";

    private ArrayList<Samolot> samoloty;

    public void odlot(){
        for(Samolot s: samoloty){
            s.lec(10);
        }
    }

    public void wypisz(){
        for(Samolot s: samoloty){
            System.out.println(s);
        }
    }
    public Lotnisko(int iloscSamolotow){
        samoloty = new ArrayList<>();
        for(int i = 0; i < iloscSamolotow; i++){
            int r = random.nextInt(1,4);
            if(r==1){
              samoloty.add(new SamolotPasazerski(nazwaSamolotu.generuj(), random.nextInt(500, 1001), random.nextInt(100, 300), 0));
            }else if(r==2){
                samoloty.add(new SamolotTowarowy(nazwaSamolotu.generuj(), random.nextInt(300,701), random.nextInt(10, 101), 0));
            } else {
                samoloty.add((new Mysliwiec(nazwaSamolotu.generuj(), random.nextInt(900, 3001), 0)));
            }
        }

    }

    public void sortowanieSamolotow(){
        Collections.sort(samoloty, (o1,o2) -> o1.getMaxPredkosc()-o2.getMaxPredkosc());
        wypisz();
        System.out.println();
        //samoloty.sort((o1,o2) -> o2.getNazwa().length() - o1.getNazwa().length());
        //samoloty.sort((o1,o2) -> o1.getNazwa().length() > 5 && o2.getNazwa().length() > 5 ?o1.getNazwa().compareTo(o2.getNazwa()):0);
        samoloty.sort((s1,s2) -> s1.getNazwa().length()> 5 && s2.getNazwa().length() > 5  ?s1.getNazwa().compareTo(s2.getNazwa()):(s2.getNazwa().length()> 5 ? 1:-1));
    }

    public void sortowanieLosowe(){
        Losowe<Comparator<Samolot>> comparator = () -> {

            if( random.nextInt(2) % 2 == 1) {
                System.out.println("Sortowanie po prędkości");
                return (o1,o2) -> o1.getMaxPredkosc()-o2.getMaxPredkosc();
            } else {
                System.out.println("Sortowanie po nazwie");
                return (o1,o2) -> o1.getNazwa().length()> 5 && o2.getNazwa().length() > 5  ?o1.getNazwa().compareTo(o2.getNazwa()):(o2.getNazwa().length()> 5 ? 1:-1);
            }

        };
        samoloty.sort(comparator.losuj());
    }

    @FunctionalInterface
    interface Losowe<T>{
        T losuj();
    }




    //String nazwa = nazwaSamolotu.generuj()
    Nazwa nazwaSamolotu = () -> {
        int length = random.nextInt(1,21);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <length; i++){
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    };


    public void dzialaniaLotniskowe() throws WyjatekLotniczy{

        samoloty.forEach(s -> System.out.println(s));
        samoloty.forEach(s -> s.laduj());
        samoloty.forEach(s -> System.out.println(s));
        samoloty.forEach(s -> {
            try {
                s.odprawa(random.nextInt(400));
            } catch (WyjatekLotniczy e) {
                System.out.println(e.getMessage());
            }
        });
        samoloty.forEach(s -> s.lec(10));
        samoloty.forEach(s -> System.out.println(s));
        samoloty.forEach(s -> {
            if (s.getClass().getName() == "Lotnisko$Mysliwiec"){
                Mysliwiec a = (Mysliwiec) s;
                a.atak();
            }
        });

    }

    public void odprawaSamolotu() throws WyjatekLotniczy{
        for (Samolot s : samoloty) {
            try {

                    if (s.getClass().getName() == "Lotnisko$SamolotPasazerski") {
                        s.odprawa(random.nextInt(400));
                    } else if (s.getClass().getName() == "Lotnisko$SamolotTowarowy") {
                        s.odprawa(random.nextInt(200));
                    } else {
                        s.odprawa(10);
                    }

            } catch (WyjatekLotniczy e) {
                System.out.println(e.getMessage());
                continue;
            }
        }

    }




    @FunctionalInterface
    interface Nazwa {
        String generuj();
    }


    public abstract class Samolot {

        private String nazwa;
        private int maxPredkosc;
        private int iloscGodzin;
        private boolean odprawa = false;
        private boolean wPowietrzu;

        public Samolot(String nazwa, int maxPredkosc) {
            this.nazwa = nazwa;
            this.maxPredkosc = maxPredkosc;
        }

        public void lec(int godziny){
            if(odprawa){
                if(wPowietrzu){
                    System.out.println("Lecimy");
                } else {
                    System.out.println("Startujemy");
                    iloscGodzin += godziny;
                    wPowietrzu = true;
                }
            } else {
                System.out.println("Nie możemy wystartować");
            }
        }

        public void laduj() {
            if(wPowietrzu){
                System.out.println("Lądujemy");
                wPowietrzu = false;
            } else {
                System.out.println("I tak jesteśmy na ziemi");
            }
        }

        public void odprawa(int iloscZaladunku) throws WyjatekLotniczy{

        }

        public boolean isOdprawa() {
            return odprawa;
        }

        public void setOdprawa(boolean odprawa) {
            this.odprawa = odprawa;
        }

        public boolean iswPowietrzu() {
            return wPowietrzu;
        }

        public void setwPowietrzu(boolean wPowietrzu) {
            this.wPowietrzu = wPowietrzu;
        }

        public int getMaxPredkosc() {
            return maxPredkosc;
        }

        public String getNazwa() {
            return nazwa;
        }

        @Override
        public String toString() {
            return "Samolot{" +
                    "nazwa='" + nazwa + '\'' +
                    ", maxPredkosc=" + maxPredkosc +
                    ", iloscGodzin=" + iloscGodzin +
                    ", odprawa=" + odprawa +
                    ", wPowietrzu=" + wPowietrzu +
                    " " + getClass().getName() + '}' ;
        }
    } //End of Samolot

    public class SamolotPasazerski extends Samolot{

        private int maxIloscPasazerow;
        private int aktualnaIloscPasazerow;

        public SamolotPasazerski(String nazwa, int maxPredkosc, int maxIloscPasazerow, int aktualnaIloscPasazerow) {
            super(nazwa, maxPredkosc);
            this.maxIloscPasazerow = maxIloscPasazerow;
            this.aktualnaIloscPasazerow = aktualnaIloscPasazerow;
        }

        @Override
        public void odprawa(int aktualnaIloscPasazerow) throws WyjatekLotniczy{
            if(aktualnaIloscPasazerow < maxIloscPasazerow/2){
                throw new WyjatekEkonomiczny("Za mało pasażerów, nie opłaca się lecieć");
            } else if(aktualnaIloscPasazerow > maxIloscPasazerow) {
                int p = aktualnaIloscPasazerow-maxIloscPasazerow;
                setOdprawa(true);
                throw new WyjatekPrzeladowania("Za dużo o " + p + " pasażerów");
            } else {
                setOdprawa(true);
            }
        }

    }//End of SamolotPasazerski

    public class SamolotTowarowy extends Samolot{


        private int maxLadunek;
        private int aktualnyLadunek;

        public SamolotTowarowy(String nazwa, int maxPredkosc, int maxLadunek, int aktualnyLadunek) {
            super(nazwa, maxPredkosc);
            this.maxLadunek = maxLadunek;
            this.aktualnyLadunek = aktualnyLadunek;
        }
        @Override
        public void odprawa(int aktualnyLadunek) throws WyjatekLotniczy{
            if(aktualnyLadunek < maxLadunek/2){
                throw new WyjatekEkonomiczny("Zbyt mały ładunek, nie opłaca się lecieć");
            } else if(aktualnyLadunek > maxLadunek) {
                int p = aktualnyLadunek-maxLadunek;
                setOdprawa(true);
                throw new WyjatekPrzeladowania("Za dużo o " + p + " ton ładunku");
            } else {
                setOdprawa(true);
            }
        }


    }

    public class Mysliwiec extends Samolot{

        public int iloscRakiet;

        public Mysliwiec(String nazwa, int maxPredkosc,  int iloscRakiet) {
            super(nazwa, maxPredkosc);
            this.iloscRakiet = iloscRakiet;
        }
        @Override
        public void odprawa(int rakiety){
            iloscRakiet = rakiety;
            setOdprawa(true);
        }

        public void atak(){
            if(iswPowietrzu()){
                iloscRakiet--;
                System.out.println("Ataaak!");
            }
            if(iloscRakiet == 0){
                setwPowietrzu(false);
            }
        }

    } //End of Mysliwiec

    public class WyjatekLotniczy extends Exception{
        public WyjatekLotniczy(String message) {super(message);}
    }//End of WyjatekLotniczy

    public class WyjatekEkonomiczny extends WyjatekLotniczy{
        public WyjatekEkonomiczny(String message) { super(message);}
    } //End of WyjatekEkonomiczny

    public class WyjatekPrzeladowania extends WyjatekLotniczy{
        public WyjatekPrzeladowania(String message) { super(message);}
    }










}
