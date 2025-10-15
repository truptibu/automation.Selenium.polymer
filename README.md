
# 🧪 Polymer Shop Automation Framework

This project is an **end-to-end Selenium Automation Framework** built to test the **Polymer Shop Web Application**.  
It uses **Java, Selenium WebDriver, TestNG, and Maven** along with modern tools for **reporting, logging, and test management**.

## 🚀 Key Features

- **Built using:** Java 11+, Selenium WebDriver 4.x, TestNG 7.x  
- **Build tool:** Maven  
- **Design pattern:** Page Object Model (POM)  
- **Logging:** Log4j 2 (v2.23.1)  
- **Reporting:** ExtentReports 5.x with screenshots  
- **Driver Handling:** WebDriverManager (auto driver setup)  
- **Shadow DOM Support:** Custom methods for handling Shadow DOM elements  

## ⚙️ Prerequisites

Before running, ensure you have the following installed:

- **Java JDK 11+**  
- **Maven**  
- **IDE:** IntelliJ IDEA (Recommended) or Eclipse  

After cloning, run:
\`\`\`bash
mvn clean install
mvn test
\`\`\`

## 📁 Project Structure

\`\`\`
polymershop/
│
├── src/
│   ├── main/
│   │   ├── java/com/polymershop/
│   │   │   ├── managers/
│   │   │   │   └── DriverManager.java           # WebDriver management
│   │   │   ├── pages/
│   │   │   │   ├── BasePage.java                # Common page methods
│   │   │   │   ├── HomePage.java                # Home page
│   │   │   │   ├── ProductListingPage.java      # Product grid
│   │   │   │   ├── ProductDetailPage.java       # Product details
│   │   │   │   ├── CartPage.java                # Cart operations
│   │   │   │   └── CheckoutPage.java            # Checkout process
│   │   │   ├── utils/
│   │   │   │   ├── ConfigReader.java            # Read properties
│   │   │   │   └── ExcelReader.java             # Read test data
│   │   │   └── data/
│   │   │       └── TestDataProvider.java        # Test data provider
│   │   └── resources/
│   │       ├── config.properties                # App config
│   │       └── testdata.xlsx                    # Test data
│   │
│   └── test/
│       ├── java/com/polymershop/
│       │   ├── tests/                           # All TestNG tests
│       │   │   ├── HomePageTests.java
│       │   │   ├── ProductTests.java
│       │   │   ├── CartTests.java
│       │   │   ├── CheckoutTests.java
│       │   │   └── InvalidFeatureTests.java
│       │   ├── listeners/
│       │   │   └── TestListener.java            # Screenshot on failure
│       │   └── suites/
│       │       └── TestNGRunner.java            # Run programmatically
│       └── resources/
│           └── testng.xml                       # TestNG suite config
│
├── output/
│   ├── screenshots/                             # Failure screenshots
│   ├── test-output/                             # Reports
│   └── logs/                                    # Log files
│
├── pom.xml                                      # Maven dependencies
├── testng.xml                                   # Suite configuration
└── README.md
\`\`\`

---

## ▶️ How to Run Tests

### ✅ Run using Maven
\`\`\`bash
mvn clean test
\`\`\`

### ✅ Run from IntelliJ
1. Open project → Right-click **testng.xml**  
2. Select **Run 'testng.xml' as TestNG Suite**

---

## 📊 Reporting & Logs

### **ExtentReports**
- Auto-generated HTML reports in `output/test-output/`
- Includes screenshots for failed steps

### **Log4j 2**
- Logs visible in console and saved under `output/logs/`

---

## 🧩 Highlights
- Page Object Model (POM) design  
- Shadow DOM element handling  
- Thread-safe WebDriver instance  
- Custom listeners for reporting & screenshots  
- Reusable utilities for config, waits, and data  
