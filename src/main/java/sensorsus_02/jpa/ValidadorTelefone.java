package sensorsus_02.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorTelefone implements ConstraintValidator<ValidaTelefone, Collection<String>> {
    private List<String> ddds;
    
    @Override
    public void initialize(ValidaTelefone validaTelefone) {
        ddds = new ArrayList<>();
        String[] listaDDDs = {"11", "12", "13", "14", "15", "16", "17", "18", "19", "21", "22", 
            "24", "27", "28", "31", "32", "33", "34", "35", "37", "38", "41", "42", "43", "44", 
            "45", "46", "47", "48", "49", "51", "53", "54", "55", "61", "62", "63", "64", "65", 
            "66", "67", "68", "69", "71", "73", "74", "75", "77", "79", "81", "82", "83", "84", 
            "85", "86", "87", "88", "89", "91", "92", "93", "94", "95", "96", "97", "98", "99"};
        ddds.addAll(Arrays.asList(listaDDDs));
    }
    
    @Override
    public boolean isValid(Collection<String> telefones, ConstraintValidatorContext context) {
        return telefones == null ? true : isTelefonesValidos(telefones);
    }
    
    private boolean isTelefonesValidos(Collection<String> telefones) {
        for (String telefone : telefones)
            if (!Pattern.compile("[0-9]{10}").matcher(telefone).matches() 
                    || !isDDDValid(telefone)) return false;
        return true;
    }
    
    private boolean isDDDValid(String telefone) { 
        return ddds.contains(telefone.substring(0, 2));
    }
}
