package io.yorosoft.ebanking.service;

/**
 * UtilityService
 */
public interface UtilityService {

    String generateAccountNumber(String typeCode, String userInitial);
}