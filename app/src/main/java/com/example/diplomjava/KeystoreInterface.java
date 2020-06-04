package com.example.diplomjava;

public interface KeystoreInterface {

    boolean hasPin();
    boolean checkPin(String pin);
    void saveNew(String pin);

}
