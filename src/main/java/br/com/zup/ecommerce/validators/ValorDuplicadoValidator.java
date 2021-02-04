package br.com.zup.ecommerce.validators;

import br.com.zup.ecommerce.produto.ProdutoForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class ValorDuplicadoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ProdutoForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        ProdutoForm form = (ProdutoForm) target;
        Set<String> nomesIguais = form.buscarCaracteristicasIguais();
        if (!nomesIguais.isEmpty()) {
            errors.rejectValue("valores", null, "Valor duplicado! " + nomesIguais);
        }
    }
}