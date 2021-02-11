package com.company;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Random;



public class Main {

    public static void main(String[] args) throws IOException {

        int[][] tablicaOdleglosci = wczytajWTablice( "C:\\Users\\Kinga\\Desktop\\Semestr 5\\Ćwiczenia\\KOmiwojażer\\b.txt");
        int size = pierwszaLinia("C:\\Users\\Kinga\\Desktop\\Semestr 5\\Ćwiczenia\\KOmiwojażer\\b.txt");
        int[][] populacja = towrzeniePopulacji(40, size);

        //Zliczamy długośc osobników
        int[] tabSum = ocenyOsobnikow(tablicaOdleglosci,populacja);

        //Pierwsze wytypowanie najlepszego osobnika
        int najlepszy = znajdzNajlepszego(tabSum);
        int najlepszyTymczasowy = znajdzNajlepszego(tabSum);

        int[][] poSelekcjiTurniejowej = selekcjaTurniejowa(populacja, tabSum);

        //int[][] poKrzyzowaniu = krzyzowanieOX(poSelekcjiTurniejowej,size);

        int[][] poMutacjiPrzezInwersje = mutacjaPrzezInwersje(poSelekcjiTurniejowej, 20,size);

        tabSum = ocenyOsobnikow(tablicaOdleglosci, poMutacjiPrzezInwersje);

        najlepszyTymczasowy = znajdzNajlepszego(tabSum);

        if(najlepszyTymczasowy < najlepszy)
        {
            najlepszy = najlepszyTymczasowy;
        }

        //Zaczynamy pętle programu
        for(int i = 0; i < 10000; i++)
        {
            poSelekcjiTurniejowej = selekcjaTurniejowa(poMutacjiPrzezInwersje, tabSum);

//          int[][] poSelekcjiKolemRuletki = selekcjaKolemRuletki(poSelekcjiTurniejowej,tabSum);   TEGO NIE UZYWAM BLAD PRZY WIEKSZYCH DANYCH

            //poKrzyzowaniu = krzyzowanieOX(poSelekcjiTurniejowej,size);

            poMutacjiPrzezInwersje = mutacjaPrzezInwersje(poSelekcjiTurniejowej, 200,size);  // TU BEDZIEMY UZYWAC PARAMETRU ZAKRES (200 -> 0,5% ; 20 -> 5%)

            tabSum = ocenyOsobnikow(tablicaOdleglosci, poMutacjiPrzezInwersje);
            najlepszyTymczasowy = znajdzNajlepszego(tabSum);

            if(najlepszyTymczasowy < najlepszy)
            {
                najlepszy = najlepszyTymczasowy;
            }

        }
//int[][] najlepszyOsobnik = znajdzNajlepzegoOsobnika(poMutacjiPrzezInwersje size);
        System.out.println("Najlepsza trasa dla osobnika: ");
        System.out.println("Wynosi: " + najlepszy);
    }
    public static int pierwszaLinia(String nazwa) throws IOException {

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(nazwa));
            String line= in.readLine();
            int size = Integer.parseInt(line);

            in.close();

            return size;

        }catch(IOException ioException){}

        return 0;
    }

    public static int[][] wczytajWTablice(String nazwa) throws IOException {

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(nazwa));
            String line= in.readLine();
            int size = Integer.parseInt(line);
            int[][] table = new int[size][size];
            int x =0;

            line = in.readLine();

            while(line != null){

                String[] parts = line.split("\\s+");

                for (int i = 0; i< parts.length; i++){
                    int insert = Integer.parseInt(parts[i]);
                    table[x][i]= insert;
                    table[i][x]= insert;
                }
                line= in.readLine();
                x++;
            }

            in.close();

            return table;

        }catch(IOException ioException){}
        return null;
    }

    public static void wyswietlTablice(int[][] nazwa)  {

        for (int i = 0; i < nazwa.length; i++) {
            for (int j = 0; j < nazwa[i].length; j++) {
                System.out.print(nazwa[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public static int[][] towrzeniePopulacji(int mysize, int size) {

        Random random = new Random();
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        for (int i = 0; i < mysize; i++) {
            List<Integer> integers = new ArrayList<Integer>();
            while (integers.size() < size) {
                int randomOne = random.nextInt(size);
                if (!integers.contains(randomOne)) {
                    integers.add(randomOne);
                }
            }
            list.add(integers);
        }

        int[][] villagers = new int[list.size()][];
        for (int i = 0; i < villagers.length; i++) {
            villagers[i] = new int[list.get(i).size()];
        }
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                villagers[i][j] = list.get(i).get(j);
            }
        }

        return villagers;
    }

    public static int[] ocenyOsobnikow(int[][] valueTab, int[][] indexTab) {

        int[] tablicaSum= new int[indexTab.length];

        for(int i= 0; i<= indexTab.length-1; i++){
            for (int j = 0; j <= indexTab.length-2; j++) {
                tablicaSum[i]+= valueTab[indexTab[i][j]][indexTab[i][j+1]];
                if(j == valueTab.length - 2){
                    tablicaSum[i]+= valueTab[indexTab[i][j+1]][indexTab[i][0]];
                }
            }
        }

        return tablicaSum;
    }

    public static int znajdzNajlepszego(int[] tabSum){

        int najlepszy = tabSum[0];

        for(int i = 1; i < tabSum.length; i++){
            if(tabSum[i] < najlepszy){
                najlepszy = tabSum[i];
            }
        }

        return najlepszy;
    }

    public static int[][] znajdzNajlepzegoOsobnika(int[][] tablicaOsobnikow, int size)
    {
int[][] tablicaNaj = new int[size][tablicaOsobnikow.length];
return  tablicaNaj;
    }

    public static int[] odwrocTablice(int[] sumy){

        int suma = 0;
        int[] odwroconaTablica = new int[sumy.length];

        for (int i =0; i < sumy.length; i++){
            suma += sumy[i];
        }

        for (int i =0; i < sumy.length; i++) {
            odwroconaTablica[i] = suma - sumy[i];
        }

        return odwroconaTablica;
    }

    public static int[][] selekcjaKolemRuletki(int[][] populacjaOsobnikow, int[] tablicaSum){

        int[][] nowaPopulacjaKolemRuletki = new int[populacjaOsobnikow.length][];
        int[] odwroconeSumy = odwrocTablice(tablicaSum);
        int[] tablicaPrzedzialow = new int[tablicaSum.length];
        int[][] kandydaci = new int[populacjaOsobnikow.length][];
        int sumaTras = 0; // Wieksze wartości wychodzą poza przedzial 2 mln !!!!

        for(int i = 0; i < odwroconeSumy.length; i++){

            System.out.println("odwrocone to  "+i + ":" + odwroconeSumy[i]);
        }

        for(int i = 0; i < odwroconeSumy.length; i++){
            sumaTras += odwroconeSumy[i];
            tablicaPrzedzialow[i] = sumaTras;
            System.out.println("dodaje "+ i+ " : " + sumaTras);
        }
        System.out.println("moja suma to "+ sumaTras);
        Random random = new Random();

        for(int j = 0; j < nowaPopulacjaKolemRuletki.length; j++) {

            //int x = random.nextInt(sumaTras);  //nextInt(y-x+1)+x;

            for (int i = 0; i < tablicaSum.length; i++) {
                if(4 <= tablicaPrzedzialow[i]){  //zamienić 4 na x
                    kandydaci[j] = populacjaOsobnikow[i];
                    break;
                }
            }
        }
        nowaPopulacjaKolemRuletki = kandydaci;

        return nowaPopulacjaKolemRuletki;
    }

    public static int[][] selekcjaTurniejowa(int[][] populacjaOsobnikow, int[] tablicaSum){

        int[][] nowaPopulacjaTurniejowa = new int[populacjaOsobnikow.length][];
        int k = (int) Math.ceil(0.1 * populacjaOsobnikow.length);
        int lengthtable = populacjaOsobnikow.length;
        int[] tymczasowiZwyciezcySumy = new int[k];
        int[] indexy = new int[k];
        int najlepszy;
        int index;

        for(int j = 0; j < nowaPopulacjaTurniejowa.length; j++) {

            Random random = new Random();

            for (int i = 0; i < k; i++) {
                index = random.nextInt(lengthtable);
                tymczasowiZwyciezcySumy[i] = tablicaSum[index];
                indexy[i]=index;

            }
            najlepszy = tymczasowiZwyciezcySumy[0];
            nowaPopulacjaTurniejowa[j]=populacjaOsobnikow[indexy[0]];

            for (int i = 0; i < tymczasowiZwyciezcySumy.length; i++) {
                if (tymczasowiZwyciezcySumy[i] < najlepszy) {
                    najlepszy = tymczasowiZwyciezcySumy[i];
                    nowaPopulacjaTurniejowa[j] = populacjaOsobnikow[indexy[i]];
                }
            }
        }
        return nowaPopulacjaTurniejowa;
    }

    public static int[][] krzyzowanieOX(int[][] populacjaOsonikow, int size) {

        int[][] populacjaPoKrzyzowaniu = new int[populacjaOsonikow.length][size];
        int[][] krzyzowanie = new int[2][size];
        int[][] zamiana = new int[2][size];
        Random random = new Random();

        int w1 = 0;
        int w2 = 0;
        int stop = 0;

        //Tworzymy pętle wykonującą się co 2, biąrąc tym samym kolejnym pary
        for (int i = 0; i < populacjaOsonikow.length; i += 2) {

            w1 = random.nextInt(Math.round(size / 2));
            w2 = random.nextInt(Math.round(size - (size / 2))) + Math.round(size / 2);

            //Uzupełniamy 2 wiersze z tablicy
            for (int x = 0; x < size; x++) {
                krzyzowanie[0][x] = populacjaOsonikow[i][x];
                krzyzowanie[1][x] = populacjaOsonikow[i + 1][x];

                zamiana[0][x] = populacjaOsonikow[i][x];
                zamiana[1][x] = populacjaOsonikow[i + 1][x];
            }

            //Zamieniamy srodki
            for (int j = w1; j <= w2; j++) {
                zamiana[0][j] = krzyzowanie[1][j];
                zamiana[1][j] = krzyzowanie[0][j];
            }

            //Uzupelniamy reszte w petli
             for(int d = 0; d <= 1; d++) {

            int j = w2 + 1;
            int x = w2 + 1;
            int w = size - (w2 - w1 + 1);

            if (j >= size) { j = 0; }
            if (x >= size) { x = 0; }

            while (w != 0) {
                boolean czyKopiowac = true;

                for (int k = w1; k <= w2; k++) {
                    if (krzyzowanie[d][j] == zamiana[d][k]) {
                        czyKopiowac = false;
                        break;
                    }
                }
                if (czyKopiowac) {
                    zamiana[d][x] = krzyzowanie[d][j];
                    x += 1;
                    w -= 1;
                }

                j += 1;

                if (j >= size) {
                    j = 0;
                }
                if (x >= size) {
                    x = 0;
                }
            }
             }

            for (int u = 0; u < size; u++) {
                populacjaPoKrzyzowaniu[i][u]= zamiana[0][u];
                populacjaPoKrzyzowaniu[i + 1][u] = zamiana[1][u];
            }
        }
        return populacjaPoKrzyzowaniu;
    }

    public static int[][] mutacjaPrzezInwersje(int[][] populacjaOosbnikow, int prawdopodobienstwo, int size) {
        int[][] populacjaPoMutacji = new int[populacjaOosbnikow.length][size];
        int[][] tymczasowyWiersz = new int[1][size];
        int[][] tymczasowyWiersz2 = new int[1][size];

        Random random = new Random();

        for(int i= 0; i < populacjaPoMutacji.length; i++ )
        {
            int doIt = random.nextInt(prawdopodobienstwo);

            if(doIt == 1)
            {
                int w1 = random.nextInt(Math.round(size / 2));
                int w2 = random.nextInt(Math.round(size - (size / 2))) + Math.round(size / 2);
                int lower = w2;

                for(int j = 0; j < size; j++)
                {
                    tymczasowyWiersz[0][j] = populacjaOosbnikow[i][j];
                    tymczasowyWiersz2[0][j] = populacjaOosbnikow[i][j];

                }

                //Zamieniamy srodki
                for (int j = w1; j <= w2; j++) {
                    tymczasowyWiersz[0][j] =tymczasowyWiersz2[0][lower];
                    lower--;
                }

                for(int k = 0; k < size; k++)
                {
                    populacjaPoMutacji[i][k] = tymczasowyWiersz[0][k];
                }
            } else
            {
                for(int k = 0; k < size; k++)
                {
                    populacjaPoMutacji[i][k] = populacjaOosbnikow[i][k];
                }
            }
        }

        return  populacjaPoMutacji;
    }

}