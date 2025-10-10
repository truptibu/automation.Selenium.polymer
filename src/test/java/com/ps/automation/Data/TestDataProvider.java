package com.ps.automation.Data;

import com.ps.automation.utils.ExcelReader;
import org.testng.annotations.DataProvider;

import java.io.InputStream;

public class TestDataProvider {

    private static final String TESTDATA_FILE = "testdata.xlsx";

    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() {
        return getDataFromSheet("Checkout");
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return getDataFromSheet("Login");
    }

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() {
        return getDataFromSheet("Search");
    }

    private Object[][] getDataFromSheet(String sheetName) {
        try {
            InputStream input = TestDataProvider.class.getClassLoader()
                    .getResourceAsStream(TESTDATA_FILE);

            if (input == null) {
                throw new RuntimeException("testdata.xlsx not found in src/test/resources!");
            }

            ExcelReader excelReader = new ExcelReader(input);
            return excelReader.getTestData(sheetName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read test data from sheet: " + sheetName, e);
        }
    }
}

