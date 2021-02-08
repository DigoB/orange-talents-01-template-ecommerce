package br.com.zup.ecommerce.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UsuarioRequest {

    @NotBlank(message = "Campo obrigatório!")
    @Email(message = "O email deve ter um formato valido!")
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank
    @Size(min = 6, message = "A senha deve ter no minimo 6 caracteres!")
    private String senha;

    public UsuarioRequest(@NotBlank @Email String email, @NotBlank @Size(min = 6) String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public Usuario paraUsuario() {
        return new Usuario(email,senha);
    }
}
