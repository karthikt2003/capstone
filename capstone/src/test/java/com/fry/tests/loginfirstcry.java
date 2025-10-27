package com.fry.tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.fry.base.BaseTest;
import com.fry.utilities.Screenshots;
import com.aventstack.extentreports.ExtentTest;

public class loginfirstcry extends BaseTest {

    ExtentTest test;

    @Test(priority = 0)
    public void loginToPepperfry() throws IOException {
        navigateurl("https://www.pepperfry.com/customer/login?from=cart&source=logincta");
        test = extent.createTest("Login to Pepperfry");

        try {
           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            WebElement phoneInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='take-mobile-email-container']/form/div/div/div/div[2]/input")));
            js.executeScript("arguments[0].scrollIntoView(true);", phoneInput);
            Thread.sleep(1000);
            phoneInput.sendKeys("8148921824");

            test.pass("Phone number entered manually. Please press Continue and enter OTP manually.");

            Thread.sleep(20000); // Wait for manual OTP entry

            // Switch to new tab if opened
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
            }

            test.pass("Switched to logged-in tab successfully.");

        } catch (Exception e) {
            String screenpath = Screenshots.Capture(driver, "Login Failed");
            test.fail("Login failed: " + e.getMessage())
                .addScreenCaptureFromPath(screenpath);
        }
    }

    @Test(priority = 1)
    public void searchAndAddBedToCart() throws IOException {
    	navigateurl("https://www.pepperfry.com/");
        test = extent.createTest("Search and add 'bed' to cart");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
            searchBox.sendKeys("bed");
            searchBox.sendKeys(Keys.ENTER);

            WebElement firstBedProduct = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//*[@id='scroller']//pf-clip-product-card)[1]//a")));
            js.executeScript("arguments[0].scrollIntoView(true);", firstBedProduct);
            Thread.sleep(1000);
            js.executeScript("window.scrollBy(0, -100);");
            js.executeScript("arguments[0].click();", firstBedProduct);
            test.pass("Clicked on the first bed product.");

            WebElement addToCartBed = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='ADD TO CART']")));
            js.executeScript("arguments[0].scrollIntoView(true);", addToCartBed);
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", addToCartBed);
            test.pass("Added bed to cart.");

            WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/checkout/cart')]")));
            cartIcon.click();

            WebElement cartTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(),'Your Cart') and contains(text(),'Item')]")));
            String cartText = cartTitle.getText();
            if (cartText.contains("1 Item")) {
                test.pass("Cart has 1 item after adding bed: " + cartText);
            } else {
                test.fail("Cart item count mismatch after adding bed. Found: " + cartText);
            }

        } catch (Exception e) {
            String screenpath = Screenshots.Capture(driver, "Bed Cart Flow Failed");
            test.fail("Failed during bed cart flow: " + e.getMessage())
                .addScreenCaptureFromPath(screenpath);
        }
    }

    @Test(priority = 2)
    public void searchAndAddMultipleItemsToCart() throws IOException {
        navigateurl("https://www.pepperfry.com/");
        test = extent.createTest("Search 'bed' and 'TV Units', add both to cart and verify cart item count");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Search for "bed"
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
            searchBox.sendKeys("bed");
            searchBox.sendKeys(Keys.ENTER);

            // Click first bed product
            WebElement firstBedProduct = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//*[@id='scroller']//pf-clip-product-card)[1]//a")));
            js.executeScript("arguments[0].scrollIntoView(true);", firstBedProduct);
            Thread.sleep(1000);
            js.executeScript("window.scrollBy(0, -100);");
            js.executeScript("arguments[0].click();", firstBedProduct);
            test.pass("Clicked on the first bed product.");

            // Click Add to Cart for bed
            WebElement addToCartBed = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='ADD TO CART']")));
            js.executeScript("arguments[0].scrollIntoView(true);", addToCartBed);
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", addToCartBed);
            test.pass("Added bed to cart.");


            // Search for "tv"
            navigateurl("https://www.pepperfry.com/");
            WebElement searchBoxTv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
            searchBoxTv.sendKeys("TV Units");
            searchBoxTv.sendKeys(Keys.ENTER);

            // Click first TV product
            WebElement firstTvProduct = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//*[@id='scroller']//pf-clip-product-card)[1]//a")));
            js.executeScript("arguments[0].scrollIntoView(true);", firstTvProduct);
            Thread.sleep(1000);
            js.executeScript("window.scrollBy(0, -100);");
            js.executeScript("arguments[0].click();", firstTvProduct);
            test.pass("Clicked on the first TV product.");

            // Click Add to Cart for TV
            WebElement addToCartTv = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='ADD TO CART']")));
            js.executeScript("arguments[0].scrollIntoView(true);", addToCartTv);
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", addToCartTv);
            test.pass("Added TV to cart.");

            // Go to cart page and verify 2 items
            WebElement cartIcon2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/checkout/cart')]")));
            cartIcon2.click();

            WebElement cartTitle2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(),'Your Cart') and contains(text(),'Item')]")));
            String cartText2 = cartTitle2.getText();
            if (cartText2.contains("2 Items") || cartText2.contains("2 Item")) {
                test.pass("Cart has 2 items after adding TV: " + cartText2);
            } else {
                test.fail("Cart item count mismatch after adding TV. Found: " + cartText2);
            }

        } catch (Exception e) {
            String screenpath = Screenshots.Capture(driver, "Cart Flow Failed");
            test.fail("Failed during cart flow: " + e.getMessage())
                .addScreenCaptureFromPath(screenpath);
        }
    }
   
    
    @Test(priority = 3)
    public void openWishlistProductAfterSaving() throws IOException {
        navigateurl("https://www.pepperfry.com/customer/login?from=cart&source=logincta");
        test = extent.createTest("Save product to wishlist and open it from wishlist");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Enter phone number manually
            WebElement phoneInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='take-mobile-email-container']/form/div/div/div/div[2]/input")));
            js.executeScript("arguments[0].scrollIntoView(true);", phoneInput);
            Thread.sleep(1000);
            phoneInput.sendKeys("8148921824");

            test.pass("Phone number entered manually. Please press Continue and enter OTP manually.");

            // Wait for manual login
            Thread.sleep(20000); // Adjust as needed

            // Switch to new tab if opened
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
            }
            test.pass("Switched to logged-in tab.");

            // Navigate to homepage
            navigateurl("https://www.pepperfry.com/");

            // Search for "bed"
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
            searchBox.sendKeys("bed");
            searchBox.sendKeys(Keys.ENTER);

            // Click heart icon to save first product to wishlist
            WebElement heartIcon = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='scroller']/div/div[1]/div/pf-clip-product-card/div/div[1]/div[2]/img")));
            js.executeScript("arguments[0].scrollIntoView(true);", heartIcon);
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", heartIcon);
            test.pass("Clicked heart icon to save product to wishlist.");

            // Navigate to wishlist page
            navigateurl("https://www.pepperfry.com/customer/wishlist");

            // Click the product in wishlist using correct XPath
            WebElement wishlistProductLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='card-content']/div/div/div[2]/div/div/div[2]/h4[1]/a")));
            js.executeScript("arguments[0].scrollIntoView(true);", wishlistProductLink);
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", wishlistProductLink);
            test.pass("Clicked wishlist product to open detail page.");

        } catch (Exception e) {
            String screenpath = Screenshots.Capture(driver, "Wishlist Product Open Failed");
            test.fail("Failed during wishlist product open flow: " + e.getMessage())
                .addScreenCaptureFromPath(screenpath);
        }
    } 
    
    
    @Test(priority = 4)
    public void logoutFromPepperfry() throws IOException {
        navigateurl("https://www.pepperfry.com/customer/login?from=cart&source=logincta");
        test = extent.createTest("Logout from Pepperfry");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Actions actions = new Actions(driver);

            // Enter phone number manually
            WebElement phoneInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='take-mobile-email-container']/form/div/div/div/div[2]/input")));
            js.executeScript("arguments[0].scrollIntoView(true);", phoneInput);
            Thread.sleep(1000);
            phoneInput.sendKeys("8148921824");

            test.pass("Phone number entered manually. Please press Continue and enter OTP manually.");

            // Wait for manual login
            Thread.sleep(20000); // Adjust as needed

            // Switch to new tab if opened
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
            }
            test.pass("Switched to logged-in tab.");

            // Navigate to homepage
            navigateurl("https://www.pepperfry.com/");

            // Hover over profile/account icon
            WebElement profileIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[text()='Hi, Karthik']")));
            actions.moveToElement(profileIcon).perform();
            test.pass("Hovered over profile/account icon.");

            // Click Logout
            WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[text()='Logout']")));
            js.executeScript("arguments[0].click();", logoutBtn);
            test.pass("Clicked on Logout and logged out successfully.");

        } catch (Exception e) {
            String screenpath = Screenshots.Capture(driver, "Logout Failed");
            test.fail("Failed during logout flow: " + e.getMessage())
                .addScreenCaptureFromPath(screenpath);
        }
    }
 }