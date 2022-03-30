package br.edu.ifg.controller;

import br.edu.ifg.interfaces.CliCrudInterface;
import br.edu.ifg.model.Endereco;
import br.edu.ifg.model.TipoEndereco;
import br.edu.ifg.utils.ListUtils;

import java.time.temporal.ValueRange;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EnderecoController implements CliCrudInterface<Endereco> {

    List<TipoEndereco> tiposEndereco;
    List<Endereco> enderecos;
    TipoEnderecoController tipoEnderecoController;
    int maxEnderecos;

    public EnderecoController(List<TipoEndereco> tiposEndereco, List<Endereco> enderecos, int maxEnderecos) {
        this.tiposEndereco = tiposEndereco;
        this.enderecos = enderecos;
        this.maxEnderecos = maxEnderecos;
        tipoEnderecoController = new TipoEnderecoController(this.tiposEndereco);
    }

    @Override
    public Endereco entrada() {
        Scanner leitor = new Scanner(System.in);
        leitor.useDelimiter(System.lineSeparator());

        Endereco enderecoInserido = new Endereco();

        try {
            System.out.print("Descrição: ");
            enderecoInserido.setDescricao(leitor.next().strip());

            System.out.print("Bairro: ");
            enderecoInserido.setBairro(leitor.next().strip());

            System.out.print("Cidade: ");
            enderecoInserido.setCidade(leitor.next().strip());

            System.out.println("Selecione um tipo de endereço dos exibidos abaixo: ");
            tipoEnderecoController.listar();
            System.out.print("ID: ");
            enderecoInserido.setTipoEndereco(tiposEndereco.get(leitor.nextInt() - 1));
        } catch (InputMismatchException e) {
            System.out.println("O ID precisa ser um inteiro, tente novamente!");
            return null;
        }

        return enderecoInserido;
    }

    @Override
    public void listar() {
        System.out.println("----------- ENDEREÇOS CADASTRADOS PARA ESSA PESSOA -----------");
        for (Endereco endereco : enderecos) {
            int indiceExibicao = enderecos.indexOf(endereco) + 1;

            System.out.println("| ID: " + indiceExibicao + " | Descrição: " + endereco.getDescricao() + " | Bairro: " + endereco.getBairro() +
                    " | Cidade: " + endereco.getCidade() + " | Tipo do endereço: " + endereco.getTipoEndereco().getDescricao() + " |");
        }
        System.out.println("--------------------------------------------------------------");
    }

    @Override
    public List<Endereco> cadastrar() {
        Endereco novoEndereco;

        novoEndereco = entrada();

        if (novoEndereco != null) {
            enderecos.add(novoEndereco);
            System.out.println("Novo endereço adicionado com sucesso!");
        }

        return enderecos;
    }

    @Override
    public List<Endereco> atualizar() {
        Scanner leitor = new Scanner(System.in);

        int indiceReal;
        int maiorIndiceListaEnderecos = enderecos.size() - 1;

        Endereco enderecoAtualizado;

        listar();

        try {
            System.out.println("Digite o ID do endereço que deseja atualizar:");
            System.out.print("ID: ");
            indiceReal = leitor.nextInt() - 1;

            if (indiceReal < 0 || indiceReal > maiorIndiceListaEnderecos) {
                System.out.println("ID inválido, tente novamente!");
                return enderecos;
            }

            enderecoAtualizado = entrada();

            enderecos.set(indiceReal, enderecoAtualizado);
            System.out.println("Endereço atualizado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("O ID precisa ser um inteiro, tente novamente!");
        }

        return enderecos;
    }

    @Override
    public List<Endereco> deletar() {
        Scanner leitor = new Scanner(System.in);
        int indiceReal;
        int maiorIndiceListaEnderecos = enderecos.size() - 1;

        listar();

        try {
            System.out.println("Digite o ID do endereço que deseja deletar:");
            System.out.print("ID: ");
            indiceReal = leitor.nextInt() - 1;

            if (indiceReal < 0 || indiceReal > maiorIndiceListaEnderecos) {
                System.out.println("ID inválido, tente novamente!");
                return enderecos;
            }

            enderecos.remove(indiceReal);

            System.out.println("Endereço deletado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("O ID precisa ser um inteiro, tente novamente!");
        }

        return enderecos;
    }

    @Override
    public List<Endereco> menu() {
        int continuar = 1;
        ValueRange operacoesDependentesCadastro = ValueRange.of(2, 4);
        boolean nenhumEnderecoCadastrado;
        boolean listaEnderecosCheia;

        do {
            Scanner leitor = new Scanner(System.in);
            int opcao;
            nenhumEnderecoCadastrado = enderecos.isEmpty();
            listaEnderecosCheia = ListUtils.isFull(enderecos, maxEnderecos);

            System.out.println("-------- MENU DE ENDEREÇOS --------");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Alterar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.print("Opção (0-4): ");

            try {
                opcao = leitor.nextInt();

                if (opcao == 1 && listaEnderecosCheia) {
                    System.out.println("A lista está cheia, não é possível cadastrar mais endereços.");
                } else if (operacoesDependentesCadastro.isValidValue(opcao) && nenhumEnderecoCadastrado) {
                    System.out.println("Impossível executar esta ação, pois nenhum endereço está cadastrado.");
                } else {
                    switch (opcao) {
                        case 1:
                            enderecos = cadastrar();
                            break;
                        case 2:
                            listar();
                            break;
                        case 3:
                            enderecos = atualizar();
                            break;
                        case 4:
                            enderecos = deletar();
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

        return enderecos;
    }

}