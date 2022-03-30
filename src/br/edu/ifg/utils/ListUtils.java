package br.edu.ifg.utils;

import java.util.List;

public class ListUtils {
    public static boolean isFull(List lista, int tamanho) {
        return lista.size() == tamanho;
    }
}