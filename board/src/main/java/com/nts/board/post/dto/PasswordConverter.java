package com.nts.board.post.dto;

import javax.persistence.AttributeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordConverter implements AttributeConverter<String, String> {


    @Override
    public String convertToDatabaseColumn(String password) {
        return PasswordAlgorithm.convert(password);
    }

    @Override
    public String convertToEntityAttribute(String dbPassword) {
        return dbPassword;
    }
}
