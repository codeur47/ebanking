package io.yorosoft.ebanking.service.UserServiceImpl;

import java.util.Random;

import org.springframework.stereotype.Service;

import io.yorosoft.ebanking.service.UtilityService;

/**
 * UtilityServiceImpl
 */
@Service
public class UtilityServiceImpl implements UtilityService{

    @Override
    public String generateAccountNumber(String typeCode, String userInitial) {
        int min = 1000000000;
		Random r = new Random();
        Integer randomNumber =  r.nextInt((Integer.MAX_VALUE - min) + 1) + min;
        StringBuilder accountNumber = new StringBuilder(userInitial);
        accountNumber = accountNumber.append(typeCode);
        accountNumber = accountNumber.append(randomNumber);
		return accountNumber.toString();
    }

    
}