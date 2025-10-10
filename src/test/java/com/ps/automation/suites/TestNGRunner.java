package com.ps.automation.suites;

import org.testng.TestNG;

import java.util.Collections;

public class TestNGRunner {
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        testng.setTestSuites(Collections.singletonList("resources/testng.xml"));
        testng.run();
    }
}
