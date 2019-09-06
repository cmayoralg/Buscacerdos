package com.example.carlos.buscacerdos;

import java.util.Random;

public class MemoriaJuego extends MainActivity {
    int[][] casillas;
    int tamaño;

    public void nuevoTablero(int tamaño) {
        casillas = new int[tamaño][tamaño];
        for (int fila = 0; fila < tamaño; fila++) {
            for (int columna = 0; columna < tamaño; columna++) {
                casillas[fila][columna] = 0;
            }
        }
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tam) {
        tamaño = tam;
    }

    public void ponerCerditos(int cerdosOcultos, int tamaño) {
        Random random = new Random();
        while (cerdosOcultos > 0) {
            int fila = random.nextInt(tamaño);
            int columna = random.nextInt(tamaño);
            if (casillas[fila][columna] == 0) {
                casillas[fila][columna] = -1;
                cerdosOcultos--;
            }
        }
    }

    public void rellenarNumeros() {
        for (int x = 0; x < tamaño; x++) {
            for (int y = 0; y < tamaño; y++) {
                int contador = 0;
                if (casillas[x][y] == 0) {
                    if ((x - 1 >= 0) && (y - 1 >= 0) && casillas[x - 1][y - 1] == -1) contador++;
                    if ((x - 1 >= 0) && casillas[x - 1][y] == -1) contador++;
                    if ((x - 1 >= 0) && (y + 1 < tamaño) && casillas[x - 1][y + 1] == -1)
                        contador++;
                    if ((y - 1 >= 0) && casillas[x][y - 1] == -1) contador++;
                    if ((y + 1 < tamaño) && casillas[x][y + 1] == -1) contador++;
                    if ((x + 1 < tamaño) && (y - 1 >= 0) && casillas[x + 1][y - 1] == -1)
                        contador++;
                    if ((x + 1 < tamaño) && casillas[x + 1][y] == -1) contador++;
                    if ((x + 1 < tamaño) && (y + 1 < tamaño) && casillas[x + 1][y + 1] == -1)
                        contador++;
                    casillas[x][y] = contador;
                }
            }
        }

    }
}
