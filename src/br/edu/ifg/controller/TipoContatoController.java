package br.edu.ifg.controller;

import br.edu.ifg.interfaces.CliCrudInterface;
import br.edu.ifg.model.TipoContato;
import br.edu.ifg.utils.ListUtils;

import java.time.temporal.ValueRange;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TipoContatoController implements CliCrudInterface<TipoContato> {

    private int maxTiposContato;
    private List<TipoContato> tiposContato;

    public TipoContatoController(List<TipoContato> tiposContato, int maxTiposContato) {
        this.tiposContato = tiposContato;
        this.maxTiposContato = maxTiposContato;
    }

    public TipoContatoController(List<TipoContato> tiposContato) {
        this.tiposContato = tiposContato;
    }

    @Override
    public TipoContato entrada() {
        Scanner leitor = new Scanner(System.in);
        leitor.useDelimiter(System.lineSeparator());

        TipoContato tipoContatoInserido = new TipoContato();

        System.out.print("Descrição: ");
        tipoContatoInserido.setDescricao(leitor.next().strip());

        return tipoContatoInserido;
    }

    @Override
    public void listar() {
        System.out.println("----------- TIPOS DE CONTATO CADASTRADOS -----------");
        for (TipoContato tipoContato : tiposContato) {
            int indiceExibicao = tiposContato.indexOf(tipoContato) + 1;

            System.out.println("| ID: " + indiceExibicao + " | Descrição: " + tipoContato.getDescricao() + " |");
        }
        System.out.println("-------------------------------------------");
    }

    @Override
    public List cadastrar() {
        TipoContato novoTipoContato;

        novoTipoContato = entrada();

        if (novoTipoContato != null) {
            tiposContato.add(novoTipoContato);
            System.out.println("Novo tipo de contato adicionado com sucesso!");
        }

        return tiposContato;
    }

    @Override
    public List atualizar() {
        Scanner leitor = new Scanner(System.in);

        int indiceReal;
        int maiorIndiceListaTiposContato = tiposContato.size() - 1;

        TipoContato tipoContatoAtualizado;

        listar();

        try {
            System.out.println("Digite o ID do tipo de contato que deseja atualizar:");
            System.out.print("ID: ");
            indiceReal = leitor.nextInt() - 1;

            if (indiceReal < 0 || indiceReal > maiorIndiceListaTiposContato) {
                System.out.println("ID inválido, tente novamente!");
                return tiposContato;
            }

            tipoContatoAtualizado = entrada();

            tiposContato.set(indiceReal, tipoContatoAtualizado);
            System.out.println("Tipo de contato atualizado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("O ID precisa ser um inteiro, tente novamente!");
        }

        return tiposContato;
    }

    @Override
    public List deletar() {
        Scanner leitor = new Scanner(System.in);
        int indiceReal;
        int maiorIndiceListaTiposContato = tiposContato.size() - 1;

        listar();

        try {
            System.out.println("Digite o ID do tipo de contato que deseja deletar:");
            System.out.print("ID: ");
            indiceReal = leitor.nextInt() - 1;

            if (indiceReal < 0 || indiceReal > maiorIndiceListaTiposContato) {
                System.out.println("ID inválido, tente novamente!");
                return tiposContato;
            }

            tiposContato.remove(indiceReal);

            System.out.println("Tipo de contato deletado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("O ID precisa ser um inteiro, tente novamente!");
        }

        return tiposContato;
    }

    public List<TipoContato> menu() {
        int continuar = 1;
        ValueRange operacoesDependentesCadastro = ValueRange.of(2, 4);
        boolean nenhumTipoContatoCadastrado;
        boolean listaTiposContatoCheia;

        do {
            Scanner leitor = new Scanner(System.in);
            int opcao;
            nenhumTipoContatoCadastrado = tiposContato.isEmpty();
            listaTiposContatoCheia = ListUtils.isFull(tiposContato, maxTiposContato);

            System.out.println("-------- MENU DE TIPOS DE CONTATO --------");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Alterar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Opção (0-4): ");

            try {
                opcao = leitor.nextInt();

                if (opcao == 1 && listaTiposContatoCheia) {
                    System.out.println("A lista está cheia, não é possível cadastrar mais tipos de contato.");
                } else if (operacoesDependentesCadastro.isValidValue(opcao) && nenhumTipoContatoCadastrado) {
                    System.out.println("Impossível executar esta ação, pois nenhum tipo de contato está cadastrado.");
                } else {
                    switch (opcao) {
                        case 1:
                            tiposContato = cadastrar();
                            break;
                        case 2:
                            listar();
                            break;
                        case 3:
                            tiposContato = atualizar();
                            break;
                        case 4:
                            tiposContato = deletar();
                            break;
                        case 0:
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

        return tiposContato;
    }

}