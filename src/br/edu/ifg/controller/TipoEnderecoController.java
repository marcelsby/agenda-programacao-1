package br.edu.ifg.controller;

import br.edu.ifg.interfaces.CliCrudInterface;
import br.edu.ifg.model.TipoEndereco;
import br.edu.ifg.utils.ListUtils;

import java.time.temporal.ValueRange;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TipoEnderecoController implements CliCrudInterface<TipoEndereco> {

    private int maxTiposEndereco;
    private List<TipoEndereco> tiposEndereco;

    public TipoEnderecoController(List<TipoEndereco> tiposEndereco, int maxTiposEndereco) {
        this.tiposEndereco = tiposEndereco;
        this.maxTiposEndereco = maxTiposEndereco;
    }

    public TipoEnderecoController(List<TipoEndereco> tiposEndereco) {
        this.tiposEndereco = tiposEndereco;
    }

    @Override
    public TipoEndereco entrada() {
        Scanner leitor = new Scanner(System.in);
        leitor.useDelimiter(System.lineSeparator());

        TipoEndereco tipoEnderecoInserido = new TipoEndereco();

        System.out.print("Descrição: ");
        tipoEnderecoInserido.setDescricao(leitor.next().strip());

        return tipoEnderecoInserido;
    }

    @Override
    public void listar() {
        System.out.println("----------- TIPOS DE ENDEREÇO CADASTRADOS -----------");
        for (TipoEndereco tipoEndereco : tiposEndereco) {
            int indiceExibicao = tiposEndereco.indexOf(tipoEndereco) + 1;

            System.out.println("| ID: " + indiceExibicao + " | Descrição: " + tipoEndereco.getDescricao() + " |");
        }
        System.out.println("-----------------------------------------------------");
    }

    @Override
    public List<TipoEndereco> cadastrar() {
        TipoEndereco novoTipoEndereco;

        novoTipoEndereco = entrada();

        if (novoTipoEndereco != null) {
            tiposEndereco.add(novoTipoEndereco);
            System.out.println("Novo tipo de endereço adicionado com sucesso!");
        }

        return tiposEndereco;
    }

    @Override
    public List<TipoEndereco> atualizar() {
        Scanner leitor = new Scanner(System.in);

        int indiceReal;
        int maiorIndiceListaTiposEndereco = tiposEndereco.size() - 1;

        TipoEndereco tipoEnderecoAtualizado;

        listar();

        try {
            System.out.println("Digite o ID do tipo de endereço que deseja atualizar:");
            System.out.print("ID: ");
            indiceReal = leitor.nextInt() - 1;

            if (indiceReal < 0 || indiceReal > maiorIndiceListaTiposEndereco) {
                System.out.println("ID inválido, tente novamente!");
                return tiposEndereco;
            }

            tipoEnderecoAtualizado = entrada();

            tiposEndereco.set(indiceReal, tipoEnderecoAtualizado);
            System.out.println("Tipo de endereço atualizado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("O ID precisa ser um inteiro, tente novamente!");
        }

        return tiposEndereco;
    }

    @Override
    public List<TipoEndereco> deletar() {
        Scanner leitor = new Scanner(System.in);
        int indiceReal;
        int maiorIndiceListaTiposContato = tiposEndereco.size() - 1;

        listar();

        try {
            System.out.println("Digite o ID do tipo de endereço que deseja deletar:");
            System.out.print("ID: ");
            indiceReal = leitor.nextInt() - 1;

            if (indiceReal < 0 || indiceReal > maiorIndiceListaTiposContato) {
                System.out.println("ID inválido, tente novamente!");
                return tiposEndereco;
            }

            tiposEndereco.remove(indiceReal);

            System.out.println("Tipo de endereço deletado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("O ID precisa ser um inteiro, tente novamente!");
        }

        return tiposEndereco;
    }

    public List<TipoEndereco> menu() {
        int continuar = 1;
        ValueRange operacoesDependentesCadastro = ValueRange.of(2, 4);
        boolean nenhumTipoContatoCadastrado;
        boolean listaTiposContatoCheia;

        do {
            Scanner leitor = new Scanner(System.in);
            int opcao;
            nenhumTipoContatoCadastrado = tiposEndereco.isEmpty();
            listaTiposContatoCheia = ListUtils.isFull(tiposEndereco, maxTiposEndereco);

            System.out.println("-------- MENU DE TIPOS DE ENDEREÇO --------");
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
                    System.out.println("Impossível executar esta ação, pois nenhum tipo de endereço está cadastrado.");
                } else {
                    switch (opcao) {
                        case 1:
                            tiposEndereco = cadastrar();
                            break;
                        case 2:
                            listar();
                            break;
                        case 3:
                            tiposEndereco = atualizar();
                            break;
                        case 4:
                            tiposEndereco = deletar();
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

        return tiposEndereco;
    }

}