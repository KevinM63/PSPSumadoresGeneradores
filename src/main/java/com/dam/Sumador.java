package com.dam;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Sumador implements Runnable {

    private final List<Integer> numeros;
    private Semaphore semaforoNormal;
    private Semaphore semaforoNumero;

    public Sumador(List<Integer> numeros, Semaphore semaforoNormal, Semaphore semaforoNumero) {
        this.numeros = numeros;
        this.semaforoNormal = semaforoNormal;
        this.semaforoNumero = semaforoNumero;
    }

    @Override
    public void run() {
        while (true) {
            int num1;
            int num2;
            //1. Espera hasta que tenga dos n'umeros
            //2. los saca de la lista
            synchronized (numeros) {
                while (numeros.size() < 2) {
                    try {
                        numeros.wait();
                    } catch (InterruptedException ignored) {
                    }
                }
                num1 = numeros.removeFirst();
                num2 = numeros.removeFirst();
            }

            int suma = num1 + num2;

            //3. muestra la suma
            System.out.printf("%s : %d + %d = %d\n", Thread.currentThread().getName(), num1, num2, suma);
            // se espera un rato aleatorio
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(100, 1000));
            } catch (InterruptedException ignored) {
            }
        }
    }
}
