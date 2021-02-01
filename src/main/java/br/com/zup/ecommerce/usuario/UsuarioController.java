package br.com.zup.ecommerce.usuario;

import br.com.zup.ecommerce.validators.EmailDuplicadoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailDuplicadoValidator emailDuplicadoValidator;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(emailDuplicadoValidator);
    }

    @PostMapping
    public void cadastraUsuario(@RequestBody @Valid UsuarioRequest request) {
        Usuario novoUsuario = request.paraUsuario();

        usuarioRepository.save(novoUsuario);

    }
}
