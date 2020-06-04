package com.example.diplomjava.Interfaces;

public interface KeystoreInterface {

    boolean hasPin();
    boolean checkPin(String pin);
    void saveNew(String pin);

}
