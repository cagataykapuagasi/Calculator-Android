package com.calculator;
import java.util.ArrayList;
import java.util.Arrays;

import android.util.Log;

public class Calculator {


    //işlemleri parçalı tuttuğumuz arraylist
    private ArrayList<String> allOperations;

    //işaret değişimini tutan boolean
    private boolean isNegative = false;




    // constructor
    public Calculator() {

    }

    public String calculateResult(String operations) {
        //bol liste oluşturulur
        allOperations = new ArrayList<String>();

        //örneğin 5+25*5 bu ifadeyi array içinde = {5, +, 25, *, 5} şekline getiriyor
        String[] regexed = operations.split("(?<=[-+*/])|(?=[-+*/])");
        allOperations.addAll(Arrays.asList(regexed));

        String lastElement = allOperations.get(allOperations.size() - 1);
        String firstElement = allOperations.get(0);

        //yanına sayı yazılmadan operatör kullanıldıysa hata oluşmaması için siliniyor.
        if (lastElement.equals("*") || lastElement.equals("/") || lastElement.equals("+") || lastElement.equals("-")) {
            allOperations.remove(allOperations.size() - 1);
        }

        //başlangıçta -/+ operatörü çalıştırıldıysa sonucu negatif ile çarpmak için kontrol
        if (firstElement.equals("*") || firstElement.equals("/") || firstElement.equals("+") || firstElement.equals("-")) {
            allOperations.remove(0);
            isNegative = true;
        }


        do {
            int index = 0;

            //döngü devam ederken değişiklik yapabilmemiz için farklı bir arraylist tanımlıyoruz
            ArrayList<String> newOperations = new ArrayList<String>();
            newOperations.addAll(allOperations);

            for (String s : allOperations) {

                //eğer işlemde * yada / operatörü varsa işleme geçiyoruz
                if (s.equals("*") || s.equals("/")) {

                    //örneğin * işaretinin solunu ve sağını alıp çarpıyoruz, ardından soldaki indexe sonucumuzu yazıp * işaretini ve sağını silip yeni şeklimizi alıyoruz.
                    double result, first, second;
                    first = Double.parseDouble(newOperations.get(index - 1));
                    second = Double.parseDouble(newOperations.get(index + 1));
                    result = s.equals("*") ? first * second : first / second;
                    newOperations.set(index - 1, String.format("%.2f", result));
                    newOperations.remove(index + 1);
                    newOperations.remove(index);

                    //bir işlem gerçekleştirdiğimizde indexlerde kayma olacağı için döngüyü sonlandırıyoruz.
                    break;
                }

                //index arttırımı
                index++;
            }

            //yeni şekillenmiş arraylistimizi asıl  arraylistimize atıyoruz.
            allOperations = new ArrayList<String>(newOperations);


            //baştan döngüye gireceğimiz için indexi 0 luyoruz.
            index = 0;

            for (String s : allOperations) {

                //burada dikkat etmemiz gereken konu: Eğer yukarıdaki işlemden break ile çıktıysak içerde hala öncelikli operatör bulunabilir. Bu yüzden tekrar kontrol ediyoruz.
                //Eğer var ise break ile döngüyü sonlandırıyoruz.
                if (allOperations.contains("/") || allOperations.contains("*")) {
                    break;
                }

                //eğer işlemde + yada - operatörü varsa işleme geçiyoruz
                if (s.equals("+") || s.equals("-")) {

                    //yukarıda uyguladığımız işlemlerin aynısını uyguluyoruz.
                    double result, first, second;
                    first = Double.parseDouble(newOperations.get(index - 1));
                    second = Double.parseDouble(newOperations.get(index + 1));
                    result = s.equals("+") ? first + second : first - second;
                    newOperations.set(index - 1, String.format("%.2f", result));
                    newOperations.remove(index + 1);
                    newOperations.remove(index);
                    break;
                }

                index++;
            }


            allOperations = new ArrayList<String>(newOperations);


            //arraylistte operatör kalmayana kadar do işlemi devam edecek
        } while (allOperations.contains("/") || allOperations.contains("*") || allOperations.contains("+") || allOperations.contains("-"));


        String result = allOperations.get(0);

        //başlangıçta işaret değiştiyse sonuç negatif verilir.
        if (isNegative) {
            double newResult = Double.parseDouble(result);
            newResult = newResult * -1;

            return String.format("%.2f", newResult);
        } else {
            return result;
        }

    }




































}

