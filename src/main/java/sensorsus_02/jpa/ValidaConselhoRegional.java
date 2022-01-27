package sensorsus_02.jpa;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorConselhoRegional.class)
@Documented
public @interface ValidaConselhoRegional {
    String message() default "{sensorsus_02.jpa.ProfissionalSaude.inscricaoConselhoRegional}";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
