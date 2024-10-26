package com.dam;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Generador implements Runnable {

    private final List<Integer> numeros;
    private Semaphore semaforoNormal;
    private Semaphore semaforoNumero;

    public Generador(List<Integer> numeros, Semaphore semaforoNormal, Semaphore semaforoNumero) {
        this.numeros = numeros;
        this.semaforoNormal = semaforoNormal;
        this.semaforoNumero = semaforoNumero;
    }

    @Override
    public void run() {
        while (true) {
            //mete un numero aleatorio a la lista
            int numero = ThreadLocalRandom.current().nextInt(1, 11);
            synchronized (numeros) {
                numeros.add(numero);
                numeros.notifyAll();
            }
            // se espera un tiempo aleatorio
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(100, 1000));
            } catch (InterruptedException ignored) {
            }
        }
    }
}
