package br.com.zup.ecommerce.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class OpiniaoController {

    @Autowired
    private ProdutoForm produtoForm;
    @Autowired
    private EntityManager manager;

    @PutMapping("/produtos/{id}/opiniao")
    @Transactional
    public ResponseEntity<OpiniaoDto> criaOpiniaoProduto(@PathVariable Long id, @RequestBody @Valid OpiniaoForm form) {

        Produto produto = manager.find(Produto.class, id);
        produto.criaOpiniao(form.paraOpiniao(manager, produto));

        manager.merge(produto);

        return ResponseEntity.ok().build();
    }
}
