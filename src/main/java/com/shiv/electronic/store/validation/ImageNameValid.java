package com.shiv.electronic.store.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    //Default Error/Invalid Message
    String message() default "Invalid Image Name !!";

    //Represent group of Constraint
    Class<?>[] groups() default {};

    //Addition information about annotation
    Class<? extends Payload>[] payload() default {};

}




/*
    @Documented
    @Constraint(validatedBy = FileSizeValidator.class)
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ValidFileSize {
        String message() default "File size exceeds maximum allowed size";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};

        long maxSizeInBytes() default 5 * 1024 * 1024; // 5MB default
        String maxSize() default "5MB";
    }




    public class FileSizeValidator implements ConstraintValidator<ValidFileSize, MultipartFile> {

    private long maxSizeInBytes;
    private String maxSizeDisplay;

    @Override
    public void initialize(ValidFileSize constraintAnnotation) {
        this.maxSizeInBytes = constraintAnnotation.maxSizeInBytes();
        this.maxSizeDisplay = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true; // Let other validators handle empty files
        }

        boolean isValid = file.getSize() <= maxSizeInBytes;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                String.format("File size (%s) exceeds maximum allowed size (%s)",
                    formatFileSize(file.getSize()), maxSizeDisplay)
            ).addConstraintViolation();
        }

        return isValid;
    }

    private String formatFileSize(long sizeInBytes) {
        if (sizeInBytes >= 1024 * 1024) {
            return String.format("%.2f MB", sizeInBytes / (1024.0 * 1024.0));
        } else if (sizeInBytes >= 1024) {
            return String.format("%.2f KB", sizeInBytes / 1024.0);
        } else {
            return sizeInBytes + " bytes";
        }
    }
}


 */
