package br.com.zup.ecommerce.produto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface Uploader {

    public default Set<String> envia(List<MultipartFile> imagens) {

        return imagens.stream().map(imagem -> "http://bucket.io/" + imagem.getOriginalFilename())
                .collect(Collectors.toSet());
    }
}
