package br.com.zup.ecommerce.produto;

import br.com.zup.ecommerce.usuario.Usuario;
import br.com.zup.ecommerce.usuario.UsuarioRequest;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Opinioes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(1)
    @Max(5)
    @NotNull
    private int nota;
    @NotBlank
    private String titulo;
    @Size(max = 500)
    private String descricao;
    @NotNull
    @ManyToOne
    private Usuario usuario;
    @NotNull
    @ManyToOne
    private Produto produto;

    public Opinioes(@Min(1) @Max(5) @NotNull int nota, @NotBlank String titulo, @Size(max = 500) String descricao,
                    @NotNull Usuario usuario, @NotNull Produto produto) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.usuario = usuario;
        this.produto = produto;
    }

    public int getNota() {
        return nota;
    }

    public OpiniaoDto paraDto(UsuarioRequest usuario) {
        return new OpiniaoDto(this.id,this.nota,this.titulo,this.descricao,usuario);
    }
}
