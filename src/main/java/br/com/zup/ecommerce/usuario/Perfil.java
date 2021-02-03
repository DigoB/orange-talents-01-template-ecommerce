package br.com.zup.ecommerce.usuario;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Perfil implements GrantedAuthority {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomePerfil;

    public Perfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

    public Long getId() {
        return id;
    }

    public String getNomePerfil() {
        return nomePerfil;
    }

    @Override
    public String getAuthority() {
        return nomePerfil;
    }
}
