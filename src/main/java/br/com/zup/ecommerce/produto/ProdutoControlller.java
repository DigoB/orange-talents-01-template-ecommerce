package br.com.zup.ecommerce.produto;

import br.com.zup.ecommerce.usuario.UsuarioRepository;
import br.com.zup.ecommerce.validators.ValorDuplicadoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoControlller {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(new ValorDuplicadoValidator());
    }

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    public ResponseEntity<Produto> cadastraProduto(@RequestBody @Valid ProdutoForm form) {

        Produto novoProduto = form.paraProduto(manager);

        produtoRepository.save(novoProduto);

        return ResponseEntity.ok().body(novoProduto);
    }
}