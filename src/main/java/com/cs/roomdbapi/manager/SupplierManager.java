package com.cs.roomdbapi.manager;

public interface SupplierManager {

    String signIn(String name, String password);

    String refresh(String name);

}
