package com.bikkadit.electronicstore.electronicstore.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageNameValidator implements ConstraintValidator<imageNameValid, String> {

	private Logger logger = LoggerFactory.getLogger(ImageNameValidator.class);
	
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		logger.info("Message from isValid :{}",value);
		if(value.isBlank()) {
			return false ;
		}
		else
		    return true;
	}

}
