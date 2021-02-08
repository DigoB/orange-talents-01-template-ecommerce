package br.com.zup.ecommerce.produto;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Primary
public class UploaderFalso implements Uploader{

    /*
        @param imagens
        @return links para imagens que foram enviadas
     */

    public Set<String> envia(List<MultipartFile> imagens) {

        return imagens.stream().map(imagem -> "http://bucket.io/" + imagem.getOriginalFilename())
                .collect(Collectors.toSet());
    }

}
