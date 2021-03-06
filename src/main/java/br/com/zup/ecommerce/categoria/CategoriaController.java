package br.com.zup.ecommerce.categoria;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<?> criaCategoria(@RequestBody @Valid NovaCategoriaRequest request, UriComponentsBuilder uriBuilder) {
        Categoria novaCategoria = request.paraCategoria(manager);
        manager.persist(novaCategoria);

        return ResponseEntity.ok().build();
    }
}