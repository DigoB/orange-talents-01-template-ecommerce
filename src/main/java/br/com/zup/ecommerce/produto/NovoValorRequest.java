package br.com.zup.ecommerce.produto;

import javax.validation.constraints.NotBlank;

public class NovoValorRequest {

    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;

    public NovoValorRequest(@NotBlank String nome, @NotBlank String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }


    @Override
    public String toString() {
        return "NovoValorRequest{" +
                "nome='" + nome + '\'' +
                ", valor='" + descricao + '\'' +
                '}';
    }

    public ValorProduto paraProduto(Produto produto) {
        return new ValorProduto(nome,descricao,produto);
    }
}
