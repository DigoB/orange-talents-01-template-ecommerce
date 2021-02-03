package br.com.zup.ecommerce.usuario;

import br.com.zup.ecommerce.validators.EmailDuplicadoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/usuarios")
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
    public ResponseEntity<?> cadastraUsuario(@RequestBody @Valid UsuarioRequest request, UriComponentsBuilder uriBuilder) {
        Usuario novoUsuario = request.paraUsuario();

        usuarioRepository.save(novoUsuario);

        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(novoUsuario.getId()).toUri();
        return ResponseEntity.ok().build();
    }
}
