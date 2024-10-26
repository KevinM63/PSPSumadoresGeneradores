package com.dam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //1. Crea la coleccion
        List<Integer> numeros = new ArrayList<>();

        // Crear semaforos
        Semaphore semaforoNormal = new Semaphore(1, true);
        Semaphore semaforoNumero = new Semaphore(0, true);
        //2. crea sumadores, pasandoles la coleccion
        var sumador = new Sumador(numeros, semaforoNormal, semaforoNumero);
        //3. crea generadores,pasandoles la coleccion
        var generador = new Generador(numeros, semaforoNormal, semaforoNumero);
        //4. arranca todos los hilos
        new Thread(generador).start();
        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 20; i++) {
                pool.execute(sumador);
                pool.execute(generador);
            }
        }

    }
}