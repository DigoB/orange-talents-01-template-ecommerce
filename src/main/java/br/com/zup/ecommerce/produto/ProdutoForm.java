package br.com.zup.ecommerce.produto;

import br.com.zup.ecommerce.categoria.Categoria;
import br.com.zup.ecommerce.usuario.Usuario;
import br.com.zup.ecommerce.usuario.UsuarioRepository;
import br.com.zup.ecommerce.validators.ExistsId;
import br.com.zup.ecommerce.validators.ValorUnico;
import org.hibernate.validator.constraints.Length;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.*;

public class ProdutoForm {

    @NotBlank
    @ValorUnico(domainClass = Produto.class,fieldName = "nome")
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
    @ExistsId(domainClass = Categoria.class, fieldName = "id")
    private Long idCategoria;
    @NotNull
    @Size(min = 3)
    @Valid
    private List<NovoValorRequest> valores = new ArrayList<>();

    public ProdutoForm(@NotBlank String nome, @NotNull @Positive BigDecimal preco, @Positive Integer quantidadeEmEstoque,
                       @NotBlank @Length(max = 1000) String descricao, @NotNull Long idCategoria) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.descricao = descricao;
        this.idCategoria = idCategoria;
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

    public Long getIdCategoria() {
        return idCategoria;
    }

    public List<NovoValorRequest> getValores() {
        return valores;
    }

    public Produto paraProduto(EntityManager manager, Usuario usuario) {
        Categoria categoria = manager.find(Categoria.class,idCategoria);
        return new Produto(nome,preco,quantidadeEmEstoque,descricao,categoria,usuario,valores);
    }

    public Set<String> buscarCaracteristicasIguais() {
        HashSet<String> nomesIguais = new HashSet<>();
        HashSet<String> resultados = new HashSet<>();
        for (NovoValorRequest valor : valores) {
            String nome = valor.getNome();
            if (!nomesIguais.add(nome)) {
                resultados.add(nome);
            }
        }
        return resultados;
    }

    @Override
    public String toString() {
        return "ProdutoForm{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidadeEmEstoque=" + quantidadeEmEstoque +
                ", descricao='" + descricao + '\'' +
                ", idCategoria=" + idCategoria +
                ", caracteristicas=" + valores +
                '}';
    }


}
