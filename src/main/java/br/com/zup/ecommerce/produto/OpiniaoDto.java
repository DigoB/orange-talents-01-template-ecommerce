package br.com.zup.ecommerce.produto;

import br.com.zup.ecommerce.usuario.UsuarioRequest;

public class OpiniaoDto {
    private Long id;
    private Integer nota;
    private String titulo;
    private String descricao;
    private UsuarioRequest opinador;

    public OpiniaoDto(Long id, Integer note, String title, String description, UsuarioRequest opinador) {
        this.id = id;
        this.nota = note;
        this.titulo = title;
        this.descricao = description;
        this.opinador = opinador;
    }

    public Long getId() {
        return this.id;
    }

    public Integer getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public UsuarioRequest getOpinador() {
        return opinador;
    }
}
