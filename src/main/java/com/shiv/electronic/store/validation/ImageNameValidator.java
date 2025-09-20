package com.shiv.electronic.store.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String> {

    private Logger logger = LoggerFactory.getLogger(ImageNameValidator.class);

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        logger.info("isValid called and value is : {}", s);


        //Write Your own Logic

        //Get the Extension
        String extension = s.substring(s.lastIndexOf("."));

        //Set the Extension which we allowed
        return extension.equalsIgnoreCase(".jpg")
                || extension.equalsIgnoreCase(".png")
                || extension.equalsIgnoreCase(".jpeg");

    }
}
