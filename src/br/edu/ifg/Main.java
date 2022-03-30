package br.edu.ifg;

import br.edu.ifg.model.TipoContato;
import br.edu.ifg.model.TipoEndereco;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<TipoContato> tiposContatoIniciais = Arrays.asList(new TipoContato("Celular"),
                new TipoContato("WhatsApp"), new TipoContato("Fixo"));

        List<TipoEndereco> tiposEnderecoIniciais = Arrays.asList(new TipoEndereco("Casa"),
                new TipoEndereco("Trabalho"), new TipoEndereco("Escola"));

        Agenda agenda = new Agenda(tiposContatoIniciais, tiposEnderecoIniciais);

        agenda.menu();
    }

}