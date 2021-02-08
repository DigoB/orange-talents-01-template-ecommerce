package br.com.zup.ecommerce.produto;

import br.com.zup.ecommerce.categoria.Categoria;
import br.com.zup.ecommerce.usuario.Usuario;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
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
    private Usuario usuario;
    private LocalDateTime dataCadastro = LocalDateTime.now();
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    @NotNull
    @Size(min = 3)
    @Valid
    private Set<ValorProduto> valores = new HashSet<>();
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private Set<Opinioes> opinioes = new HashSet<>();
    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagemProduto> imagens = new HashSet<>();

    @Deprecated
    public Produto() {
    }

    public Produto(@NotBlank String nome, @NotNull @Positive BigDecimal preco, @Positive Integer quantidadeEmEstoque,
                   @NotBlank @Length(max = 1000) String descricao, Categoria categoria, Usuario usuario,
                   @NotNull @Size(min = 3) @Valid Collection<NovoValorRequest> valores, Collection<OpiniaoForm> opinioes) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.descricao = descricao;
        this.categoria = categoria;
        this.usuario = usuario;
        this.valores.addAll(valores.stream().map(valor -> valor.paraProduto(this))
                .collect(Collectors.toSet()));
        this.opinioes.addAll(opinioes.stream().map(opiniao -> opiniao.novaOpiniao(this,this.usuario))
                .collect(Collectors.toSet()));

        Assert.isTrue(this.valores.size() >= 3, "Todo produto deve ter pelo menos 3 valores!");
    }

    public Produto(String nome, BigDecimal preco, Integer quantidadeEmEstoque, String descricao, Categoria categoria,
                   Usuario usuario,  Collection<NovoValorRequest> valores) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.descricao = descricao;
        this.categoria = categoria;
        this.usuario = usuario;
        this.valores.addAll(valores.stream().map(valor -> valor.paraProduto(this))
                .collect(Collectors.toSet()));
    }

    public Long getId() {
        return id;
    }

    @NonNull
    public Usuario getUsuario() {
        return usuario;
    }

    public void associaImagens(Set<String> links) {
        Set<ImagemProduto> imagens = links.stream().map(link -> new ImagemProduto(this, link))
                .collect(Collectors.toSet());
        this.imagens.addAll(imagens);
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
                ", dono=" + usuario +
                ", dataCadastro=" + dataCadastro +
                ", valores=" + valores +
                ", imagens=" + imagens +
                '}';
    }

    public void validarOpinadorTrue(String email, String mensagem) {
        if (email.equals(this.usuario.getEmail())) {
            throw new BadCredentialsException(mensagem);
        }
    }

    public void criaOpiniao(Opinioes paraOpiniao) {
        this.opinioes.add((Opinioes) opinioes);
    }

}