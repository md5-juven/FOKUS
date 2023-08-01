package com.vc.UserAuthentication.security;

import java.security.NoSuchAlgorithmException;

public interface Encryptor {

    String generateHash(String pass, String alg, byte[] salt) throws NoSuchAlgorithmException;
    byte[] createdSalt();
}