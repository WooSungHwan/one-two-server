package com.blackdog.onetwo.configuration.validation;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import com.blackdog.onetwo.configuration.validation.Extension.ExtensionValidator;


@Constraint(validatedBy = {ExtensionValidator.class})
@Retention(RUNTIME)
@Target(FIELD)
public @interface Extension {

    String message() default "파일 확장자를 확인해주세요.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String[] allows();


    public static class ExtensionValidator implements ConstraintValidator<Extension, MultipartFile> {

        private String[] allows;

        @Override
        public void initialize(Extension constraintAnnotation) {
            this.allows = constraintAnnotation.allows();
        }

        @Override
        public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
            if (Objects.isNull(value)) {
                return true;
            }

            String extension = FilenameUtils.getExtension(value.getOriginalFilename());

            return Arrays.stream(allows).anyMatch(allow -> allow.equalsIgnoreCase(extension));
        }
    }

}
