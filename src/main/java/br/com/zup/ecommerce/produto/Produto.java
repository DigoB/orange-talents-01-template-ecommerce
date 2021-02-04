package br.com.zup.ecommerce.produto;

import br.com.zup.ecommerce.categoria.Categoria;
import br.com.zup.ecommerce.usuario.Usuario;
import br.com.zup.ecommerce.validators.ValorUnico;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @NotBlank
    private String nome;
    @NotNull
    @Positive
    private BigDecimal preco;
    @Positive
    private Integer quantidadeEmEstoque;
    @NotBlank
    @Length(max = 1000)
    private String descricao;
    @NotNull
    @Valid
    @ManyToOne
    private Categoria categoria;
    @NonNull
    @Valid
    @ManyToOne
    private Usuario dono;
    private LocalDateTime dataCadastro = LocalDateTime.now();
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Set<ValorProduto> valores = new HashSet<>();

    @Deprecated
    public Produto() {}

    public Produto(@NotBlank String nome, @NotNull @Positive BigDecimal preco,
                   @Positive Integer quantidadeEmEstoque, @NotBlank @Length(max = 1000) String descricao,
                   @NotNull @Valid Categoria categoria, @NotNull @Valid Usuario dono,
                   @Size(min = 3) @Valid Collection<NovoValorRequest> valores) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.descricao = descricao;
        this.categoria = categoria;
        this.dono = dono;
        this.valores.addAll(valores.stream().map(valor -> valor.paraProduto(this)).collect(Collectors.toSet()));

        Assert.isTrue(this.valores.size() >= 3, "Todo produto deve ter pelo menos 3 valores!");
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidadeEmEstoque=" + quantidadeEmEstoque +
                ", descricao='" + descricao + '\'' +
                ", categoria=" + categoria +
                ", dono=" + dono +
                ", dataCadastro=" + dataCadastro +
                ", valores=" + valores +
                '}';
    }
}
