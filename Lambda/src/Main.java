public class Main {
    public static void main(String[] args) throws Lotnisko.WyjatekLotniczy {
        Lotnisko lotnisko = new Lotnisko(5);

        System.out.println("Samoloty na lotnisku:");
        System.out.println("----------------------");
        lotnisko.wypisz();
        System.out.println();

        System.out.println("Próba odlotu: ");
        System.out.println("----------------------");
        lotnisko.odlot();
        System.out.println();

        System.out.println("Odprawa:");
        System.out.println("----------");
        try{
            lotnisko.odprawaSamolotu();
        } catch (Lotnisko.WyjatekEkonomiczny e) {
            System.out.println(e.getMessage());
        } catch (Lotnisko.WyjatekPrzeladowania e) {
            System.out.println(e.getMessage());
        }
        lotnisko.wypisz();
        System.out.println();

        System.out.println("Odlot:");
        System.out.println("---------");
        lotnisko.odlot();
        lotnisko.wypisz();
        System.out.println();




        System.out.println("Działania lotniskowe:");
        System.out.println("----------------");
        try{
            lotnisko.dzialaniaLotniskowe();
        } catch (Lotnisko.WyjatekEkonomiczny e) {
            System.out.println(e.getMessage());
        } catch (Lotnisko.WyjatekPrzeladowania e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        System.out.println("Sortowanie samolotów:");
        System.out.println("--------------------");

        lotnisko.sortowanieSamolotow();
        lotnisko.wypisz();
        System.out.println();

        System.out.println("Sortowanie losowe:");
        System.out.println("------------------");
        lotnisko.sortowanieLosowe();
        lotnisko.wypisz();




    }
}
