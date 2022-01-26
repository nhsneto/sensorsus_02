package sensorsus_02.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorEstado implements ConstraintValidator<ValidaEstado, String> {
    private List<String> estados;
    
    @Override
    public void initialize(ValidaEstado validaEstado) {
        estados = new ArrayList<>();
        String[] nomes = {"Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará", 
            "Distrito Federal", "Espírito Santo", "Goiás", "Maranhão", "Mato Grosso", 
            "Mato Grosso do Sul", "Minas Gerais", "Paraná", "Paraíba", "Pará", "Pernambuco", 
            "Piauí", "Rio de Janeiro", "Rio Grande do Norte", "Rio Grande do Sul", "Rondonia", 
            "Roraima", "Santa Catarina", "Sergipe", "São Paulo", "Tocantins"};
        estados.addAll(Arrays.asList(nomes));
    }
    
    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        return valor == null ? false : estados.contains(valor);
    }
}
