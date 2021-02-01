package br.com.zup.ecommerce.categoria;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    public String criaCategoria(@RequestBody @Valid NovaCategoriaRequest request) {
        Categoria novaCategoria = request.paraCategoria(manager);
        manager.persist(novaCategoria);
        return novaCategoria.toString();
    }
}
