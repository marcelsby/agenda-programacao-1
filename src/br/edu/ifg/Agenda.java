package br.edu.ifg;

import br.edu.ifg.controller.PessoaController;
import br.edu.ifg.controller.TipoContatoController;
import br.edu.ifg.controller.TipoEnderecoController;
import br.edu.ifg.model.Pessoa;
import br.edu.ifg.model.TipoContato;
import br.edu.ifg.model.TipoEndereco;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Agenda {

    List<Pessoa> pessoas = new ArrayList();
    List<TipoContato> tiposContato;
    List<TipoEndereco> tiposEndereco;

    public Agenda(List<TipoContato> tiposContato, List<TipoEndereco> tiposEndereco) {
        this.tiposContato = tiposContato;
        this.tiposEndereco = tiposEndereco;
    }

    public void menu() {
        int continuar = 1;
        boolean nenhumTipoContatoCadastrado;
        boolean nenhumTipoEnderecoCadastrado;

        do {
            nenhumTipoContatoCadastrado = tiposContato.isEmpty();
            nenhumTipoEnderecoCadastrado = tiposEndereco.isEmpty();
            Scanner leitor = new Scanner(System.in);
            int opcao;

            System.out.println("-------- MENU PRINCIPAL --------");
            System.out.println("1 - Gerenciar tipos de contato");
            System.out.println("2 - Gerenciar tipos de endereço");
            System.out.println("3 - Gerenciar pessoas, como também seus contatos e endereços");
            System.out.println("0 - Sair do programa");
            System.out.print("Opção (0-3): ");

            try {
                opcao = leitor.nextInt();

                if (opcao == 3 && nenhumTipoContatoCadastrado && nenhumTipoEnderecoCadastrado) {
                    System.out.println("Para executar esta ação é necessário que pelo menos um tipo de endereço e um tipo de contato estejam cadastrados!");
                } else {
                    switch (opcao) {
                        case 1:
                            TipoContatoController tipoContatoController = new TipoContatoController(tiposContato, 10);
                            List<TipoContato> resultadoTiposContato = tipoContatoController.menu();
                            tiposContato = resultadoTiposContato;
                            break;
                        case 2:
                            TipoEnderecoController tipoEnderecoController = new TipoEnderecoController(tiposEndereco, 10);
                            List<TipoEndereco> resultadoTiposEndereco = tipoEnderecoController.menu();
                            tiposEndereco = resultadoTiposEndereco;
                            break;
                        case 3:
                            PessoaController pessoaController = new PessoaController(pessoas, 10, tiposContato, tiposEndereco);
                            List<Pessoa> resultadoPessoas = pessoaController.menu();
                            pessoas = resultadoPessoas;
                            break;
                        case 0:
                            System.out.println("Saindo do programa...");
                            continuar = 0;
                            break;
                        default:
                            System.out.println("Opção inválida, tente novamente!");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("A opção precisa ser um inteiro, tente novamente!");
            }

        } while (continuar != 0);
    }

}