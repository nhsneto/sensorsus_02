package sensorsus_02.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorConselhoRegional implements ConstraintValidator<ValidaConselhoRegional, String> {
    private List<String> ufs;
    
    @Override
    public void initialize(ValidaConselhoRegional validaConselhoRegional) {
        String[] siglas = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", 
            "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
        ufs = new ArrayList<>();
        ufs.addAll(Arrays.asList(siglas));
    }
    
    @Override
    public boolean isValid(String conselhoRegional, ConstraintValidatorContext context) {
        if (conselhoRegional == null) return true;
        
        // O padrao a seguir apenas aceita: coren-uf000.000-te ou coren-uf000.000-enf ou crm-uf00000
        String regex = "(coren-[a-z]{2}[0-9]{3}[.][0-9]{3}-(te|enf))|(crm-[a-z]{2}[0-9]{5})";
        return Pattern.compile(regex).matcher(conselhoRegional.toLowerCase()).matches()
                && isUfValida(conselhoRegional);
    }
    
    private boolean isUfValida(String conselhoRegional) {
        String uf;
        if (conselhoRegional.substring(0, 3).equalsIgnoreCase("crm"))
            uf = conselhoRegional.substring(4, 6).toUpperCase();
        else
            uf = conselhoRegional.substring(6, 8).toUpperCase();
        return ufs.contains(uf);
    }
}
