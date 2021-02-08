package br.com.zup.ecommerce.produto;

import br.com.zup.ecommerce.seguranca.AutenticacaoService;
import br.com.zup.ecommerce.usuario.Usuario;
import br.com.zup.ecommerce.usuario.UsuarioRepository;
import br.com.zup.ecommerce.usuario.UsuarioRequest;
import br.com.zup.ecommerce.validators.ValorDuplicadoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private Uploader uploaderFalso;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @InitBinder(value = "ProdutoForm")
    public void init(WebDataBinder binder) {
        binder.addValidators(new ValorDuplicadoValidator());
    }

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping(value = "/produtos")
    public ResponseEntity<Produto> cadastraProduto(@RequestBody @Valid ProdutoForm form) {

        UserDetails usuarioLogado = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioRepository.findByEmail(usuarioLogado.getUsername())
                .orElseThrow(() ->new UsernameNotFoundException("Usuário não encontrado"));

        Produto novoProduto = form.paraProduto(manager, usuario);

        produtoRepository.save(novoProduto);

        return ResponseEntity.ok().body(novoProduto);
    }

    @PostMapping(value = "/produtos/{id}/imagens")
    @Transactional
    public ResponseEntity<Produto> adicionaImagem(@PathVariable("id") Long id, @Valid NovaImagemRequest request) {


        Produto produto = manager.find(Produto.class,id);
        UserDetails usuarioLogado = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (produto.getUsuario().getEmail().equals(usuarioLogado.getUsername())) {
            Set<String> links = uploaderFalso.envia(request.getImagens());
            produto.associaImagens(links);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}