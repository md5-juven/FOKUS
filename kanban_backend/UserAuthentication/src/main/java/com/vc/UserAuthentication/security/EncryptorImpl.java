package com.vc.UserAuthentication.security;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class EncryptorImpl implements Encryptor {
    @Override
    public String generateHash(String pass,String alg,byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(alg);
        messageDigest.reset();
        messageDigest.update(salt);
        byte[] md = messageDigest.digest(pass.getBytes());
        BigInteger bigInteger = new BigInteger(1,md);
        return bigInteger.toString(16);
    }

    @Override
    public byte[] createdSalt(){
        byte[] bytes = new byte[10];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        return bytes;
    }
}
