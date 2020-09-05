package com.example.diplomjava.Interfaces;

public interface KeyStoreInterface {

    boolean hasPin();
    boolean checkPin(String pin);
    void saveNew(String pin);

}
