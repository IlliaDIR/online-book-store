package com.example.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, Object> {
    private String first;
    private String second;

    @Override
    public void initialize(FieldsMatch constraintAnnotation) {
        this.first = constraintAnnotation.first();
        this.second = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        Object firstValue = beanWrapper.getPropertyValue(first);
        Object secondValue = beanWrapper.getPropertyValue(second);
        return Objects.equals(firstValue, secondValue);
    }
}
