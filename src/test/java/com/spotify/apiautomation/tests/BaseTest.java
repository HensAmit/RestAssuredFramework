package com.spotify.apiautomation.tests;

import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;

public class BaseTest {
    @BeforeMethod
    public void beforeMethod(Method m){
        System.out.println("STARTING TEST: " + m.getName());
        System.out.println("Thread ID: " + Thread.currentThread().getId());
    }
}
