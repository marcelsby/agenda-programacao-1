package br.edu.ifg.interfaces;

import java.util.List;

public interface CliCrudInterface<T> {

    T entrada();

    void listar();

    List<T> cadastrar();

    List<T> atualizar();

    List<T> deletar();

    List<T> menu();

}