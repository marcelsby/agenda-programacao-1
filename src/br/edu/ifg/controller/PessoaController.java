package br.edu.ifg.controller;

import br.edu.ifg.interfaces.CliCrudInterface;
import br.edu.ifg.model.*;
import br.edu.ifg.utils.ListUtils;

import java.time.temporal.ValueRange;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PessoaController implements CliCrudInterface<Pessoa> {

    private final int maxPessoas;
    private final List<TipoContato> tiposContato;
    private final List<TipoEndereco> tiposEndereco;
    private List<Pessoa> pessoas;

    public PessoaController(
            List<Pessoa> pessoas,
            int maxPessoas,
            List<TipoContato> tiposContato,
            List<TipoEndereco> tiposEndereco
    ) {
        this.pessoas = pessoas;
        this.maxPessoas = maxPessoas;
        this.tiposContato = tiposContato;
        this.tiposEndereco = tiposEndereco;
    }

    private int selecionar() {
        Scanner leitor = new Scanner(System.in);
        int indiceReal = -1;
        int maiorIndiceListaPessoas = pessoas.size() - 1;

        listar();

        try {
            System.out.print("ID: ");
            int indiceRecebido = leitor.nextInt();

            indiceReal = indiceRecebido - 1;

            if (indiceReal < 0 || indiceReal > maiorIndiceListaPessoas) {
                System.out.println("ID inválido, tente novamente!");
                return -1;
            }
        } catch (InputMismatchException e) {
            System.out.println("O ID precisa ser um inteiro, tente novamente!");
        }

        return indiceReal;
    }

    @Override
    public Pessoa entrada() {
        Scanner leitor = new Scanner(System.in);
        leitor.useDelimiter(System.lineSeparator());

        Pessoa pessoaInserida = new Pessoa();

        try {
            System.out.print("Nome: ");
            pessoaInserida.setNome(leitor.next().strip());

            System.out.print("Idade: ");
            pessoaInserida.setIdade(leitor.nextInt());

            System.out.print("Sexo (M-F): ");
            pessoaInserida.setSexo(leitor.next(".").charAt(0));
        } catch (InputMismatchException e) {
            System.out.println("A idade precisa ser um inteiro, tente novamente!");
            return null;
        }

        return pessoaInserida;
    }

    @Override
    public void listar() {
        System.out.println("----------- PESSOAS CADASTRADAS -----------");
        for (Pessoa pessoa : pessoas) {
            int indiceExibicao = pessoas.indexOf(pessoa) + 1;

            System.out.println("| ID: " + indiceExibicao + " | Nome: " + pessoa.getNome() + " | Idade: " + pessoa.getIdade() +
                    " | Sexo: " + pessoa.getSexo() + " |");
        }
        System.out.println("-------------------------------------------");
    }

    @Override
    public List<Pessoa> cadastrar() {
        Pessoa novaPessoa;

        novaPessoa = entrada();

        if (novaPessoa != null) {
            pessoas.add(novaPessoa);
            System.out.println("Nova pessoa adicionada com sucesso!");
        }

        return pessoas;
    }

    @Override
    public List<Pessoa> atualizar() {
        Scanner leitor = new Scanner(System.in);

        int indiceReal;
        int maiorIndiceListaPessoas = pessoas.size() - 1;

        Pessoa pessoaAtualizada;

        listar();

        try {
            System.out.println("Digite o ID da pessoa que deseja atualizar:");
            System.out.print("ID: ");
            indiceReal = leitor.nextInt() - 1;

            if (indiceReal < 0 || indiceReal > maiorIndiceListaPessoas) {
                System.out.println("ID inválido, tente novamente!");
                return pessoas;
            }

            pessoaAtualizada = entrada();

            if (pessoaAtualizada != null) {
                pessoas.set(indiceReal, pessoaAtualizada);
                System.out.println("Pessoa atualizada com sucesso!");
            }
        } catch (InputMismatchException e) {
            System.out.println("O ID precisa ser um inteiro, tente novamente!");
        }

        return pessoas;
    }

    @Override
    public List<Pessoa> deletar() {
        Scanner leitor = new Scanner(System.in);
        int indiceReal;
        int maiorIndiceListaPessoas = pessoas.size() - 1;

        listar();

        try {
            System.out.println("Digite o ID da pessoa que deseja deletar:");
            System.out.print("ID: ");
            indiceReal = leitor.nextInt() - 1;

            if (indiceReal < 0 || indiceReal > maiorIndiceListaPessoas) {
                System.out.println("ID inválido, tente novamente!");
                return pessoas;
            }

            pessoas.remove(indiceReal);

            System.out.println("Pessoa deletada com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("O ID precisa ser um inteiro, tente novamente!");
        }

        return pessoas;
    }

    @Override
    public List<Pessoa> menu() {
        int continuar = 1;
        ValueRange operacoesDependentesCadastro = ValueRange.of(2, 6);
        boolean nenhumaPessoaCadastrada;
        boolean listaPessoasCheia;
        int indicePessoaSelecionada;

        do {
            Scanner leitor = new Scanner(System.in);
            int opcao;
            nenhumaPessoaCadastrada = pessoas.isEmpty();
            listaPessoasCheia = ListUtils.isFull(pessoas, maxPessoas);

            System.out.println("-------- MENU DE PESSOAS --------");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Alterar");
            System.out.println("4 - Excluir");
            System.out.println("5 - Gerenciar os contatos de uma pessoa");
            System.out.println("6 - Gerenciar os endereços de uma pessoa");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Opção (0-6): ");

            try {
                opcao = leitor.nextInt();

                if (opcao == 1 && listaPessoasCheia) {
                    System.out.println("A lista está cheia, não é possível cadastrar mais pessoas.");
                } else if (operacoesDependentesCadastro.isValidValue(opcao) && nenhumaPessoaCadastrada) {
                    System.out.println("Impossível executar esta ação, pois nenhuma pessoa está cadastrada.");
                } else {
                    switch (opcao) {
                        case 1:
                            pessoas = cadastrar();
                            break;
                        case 2:
                            listar();
                            break;
                        case 3:
                            pessoas = atualizar();
                            break;
                        case 4:
                            pessoas = deletar();
                            break;
                        case 5:
                            System.out.println("Escolha abaixo a pessoa que você deseja gerenciar os contatos:");
                            indicePessoaSelecionada = selecionar();

                            if (indicePessoaSelecionada != -1) {
                                Pessoa pessoaSelecionada = pessoas.get(indicePessoaSelecionada);
                                ContatoController contatoController = new ContatoController(
                                        tiposContato,
                                        pessoas.get(indicePessoaSelecionada).getContatos(),
                                        3);

                                List<Contato> resultadoContatoController = contatoController.menu();

                                pessoaSelecionada.setContatos(resultadoContatoController);

                                pessoas.set(indicePessoaSelecionada, pessoaSelecionada);
                            }
                            break;
                        case 6:
                            System.out.println("Escolha abaixo a pessoa que você deseja gerenciar os endereços:");
                            indicePessoaSelecionada = selecionar();

                            if (indicePessoaSelecionada != -1) {
                                Pessoa pessoaSelecionada = pessoas.get(indicePessoaSelecionada);
                                EnderecoController enderecoController = new EnderecoController(
                                        tiposEndereco,
                                        pessoas.get(indicePessoaSelecionada).getEnderecos(),
                                        5);

                                List<Endereco> resultadoEnderecoController = enderecoController.menu();

                                pessoaSelecionada.setEnderecos(resultadoEnderecoController);

                                pessoas.set(indicePessoaSelecionada, pessoaSelecionada);
                            }
                            break;
                        case 0:
                            System.out.println("Voltando para o menu principal...");
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

        return pessoas;
    }

}