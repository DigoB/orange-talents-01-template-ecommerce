package br.com.zup.ecommerce.validators;

import br.com.zup.ecommerce.usuario.Usuario;
import br.com.zup.ecommerce.usuario.UsuarioRepository;
import br.com.zup.ecommerce.usuario.UsuarioRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class EmailDuplicadoValidator implements Validator {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UsuarioRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        UsuarioRequest request = (UsuarioRequest) target;

        Optional<Usuario> possivelUsuario = usuarioRepository.findByEmail(request.getEmail());

        if (possivelUsuario.isPresent()) {
            errors.rejectValue("email", null, "Email já cadastrado!");
        }
    }
}
