package br.com.zup.ecommerce.produto;

import br.com.zup.ecommerce.usuario.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;

public class OpiniaoForm {

    @NotNull @Min(1) @Max(5)
    private int nota;
    @NotNull @NotBlank
    private String titulo;
    @NotNull
    @NotBlank
    @Size(max = 500)
    private String descricao;

    public OpiniaoForm(Integer nota, String titulo, String descricao) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Opinioes paraOpiniao(EntityManager manager, Produto produto) {
        Usuario usuarioLogado = (Usuario) manager.createQuery("SELECT u FROM User u WHERE u.email = :email", Usuario.class)
                .setParameter("email", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        produto.validarOpinadorTrue(usuarioLogado.getEmail(), "The product owner can't post a opinion");
        return new Opinioes(this.nota, this.titulo, this.descricao, usuarioLogado, produto);
    }

    public Opinioes novaOpiniao(Produto produto, Usuario usuario) {
        return new Opinioes(nota,titulo,descricao,usuario ,produto);
    }
}