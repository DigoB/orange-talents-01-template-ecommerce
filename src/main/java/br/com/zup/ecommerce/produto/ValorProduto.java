package br.com.zup.ecommerce.produto;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class ValorProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private @NotBlank String nome;
    @NotBlank
    private @NotBlank String descricao;
    @ManyToOne
    private @NotNull @Valid Produto produto;

    public ValorProduto() {}

    public ValorProduto(@NotBlank String nome, @NotBlank String descricao, @NonNull @Valid Produto produto) {
        this.nome = nome;
        this.descricao = descricao;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Produto getProduto() {
        return produto;
    }
}
