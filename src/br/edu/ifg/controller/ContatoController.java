package br.edu.ifg.controller;

import br.edu.ifg.interfaces.CliCrudInterface;
import br.edu.ifg.model.Contato;
import br.edu.ifg.model.TipoContato;
import br.edu.ifg.utils.ListUtils;

import java.time.temporal.ValueRange;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ContatoController implements CliCrudInterface<Contato> {

    List<TipoContato> tiposContato;
    List<Contato> contatos;
    TipoContatoController tipoContatoController;
    int maxContatos;

    public ContatoController(List<TipoContato> tiposContato, List<Contato> contatos, int maxContatos) {
        this.tiposContato = tiposContato;
        this.contatos = contatos;
        this.maxContatos = maxContatos;
        tipoContatoController = new TipoContatoController(this.tiposContato);
    }

    @Override
    public Contato entrada() {
        Scanner leitor = new Scanner(System.in);
        leitor.useDelimiter(System.lineSeparator());

        Contato contatoInserido = new Contato();

        try {
            System.out.print("Descrição: ");
            contatoInserido.setDescricao(leitor.next().strip());

            System.out.println("Selecione um tipo de contato dos exibidos abaixo: ");
            tipoContatoController.listar();
            System.out.print("ID: ");
            contatoInserido.setTipoContato(tiposContato.get(leitor.nextInt() - 1));
        } catch (InputMismatchException e) {
            System.out.println("O ID precisa ser um inteiro, tente novamente!");
            return null;
        }

        return contatoInserido;
    }

    @Override
    public void listar() {
        System.out.println("----------- CONTATOS CADASTRADOS PARA ESSA PESSOA -----------");
        for (Contato contato : contatos) {
            int indiceExibicao = contatos.indexOf(contato) + 1;

            System.out.println("| ID: " + indiceExibicao + " | Descrição: " + contato.getDescricao() +
                    " | Tipo contato: " + contato.getTipoContato().getDescricao() + " |");
        }
        System.out.println("--------------------------------------------------------------");
    }

    @Override
    public List<Contato> cadastrar() {
        Contato novoContato;

        novoContato = entrada();

        if (novoContato != null) {
            contatos.add(novoContato);
            System.out.println("Novo contato adicionado com sucesso!");
        }

        return contatos;
    }

    @Override
    public List<Contato> atualizar() {
        Scanner leitor = new Scanner(System.in);

        int indiceReal;
        int maiorIndiceListaContatos = contatos.size() - 1;

        Contato contatoAtualizado;

        listar();

        try {
            System.out.println("Digite o ID do contato que deseja atualizar:");
            System.out.print("ID: ");
            indiceReal = leitor.nextInt() - 1;

            if (indiceReal < 0 || indiceReal > maiorIndiceListaContatos) {
                System.out.println("ID inválido, tente novamente!");
                return contatos;
            }

            contatoAtualizado = entrada();

            contatos.set(indiceReal, contatoAtualizado);
            System.out.println("Contato atualizado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("O ID precisa ser um inteiro, tente novamente!");
        }

        return contatos;
    }

    @Override
    public List<Contato> deletar() {
        Scanner leitor = new Scanner(System.in);
        int indiceReal;
        int maiorIndiceListaContatos = contatos.size() - 1;

        listar();

        try {
            System.out.println("Digite o ID do contato que deseja deletar:");
            System.out.print("ID: ");
            indiceReal = leitor.nextInt() - 1;

            if (indiceReal < 0 || indiceReal > maiorIndiceListaContatos) {
                System.out.println("ID inválido, tente novamente!");
                return contatos;
            }

            contatos.remove(indiceReal);

            System.out.println("Contato deletado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("O ID precisa ser um inteiro, tente novamente!");
        }

        return contatos;
    }

    @Override
    public List<Contato> menu() {
        int continuar = 1;
        ValueRange operacoesDependentesCadastro = ValueRange.of(2, 4);
        boolean nenhumContatoCadastrado;
        boolean listaContatosCheia;

        do {
            Scanner leitor = new Scanner(System.in);
            int opcao;
            nenhumContatoCadastrado = contatos.isEmpty();
            listaContatosCheia = ListUtils.isFull(contatos, maxContatos);

            System.out.println("-------- MENU DE CONTATOS --------");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Alterar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.print("Opção (0-4): ");

            try {
                opcao = leitor.nextInt();

                if (opcao == 1 && listaContatosCheia) {
                    System.out.println("A lista está cheia, não é possível cadastrar mais contatos.");
                } else if (operacoesDependentesCadastro.isValidValue(opcao) && nenhumContatoCadastrado) {
                    System.out.println("Impossível executar esta ação, pois nenhum contato está cadastrado.");
                } else {
                    switch (opcao) {
                        case 1:
                            contatos = cadastrar();
                            break;
                        case 2:
                            listar();
                            break;
                        case 3:
                            contatos = atualizar();
                            break;
                        case 4:
                            contatos = deletar();
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

        return contatos;
    }
}