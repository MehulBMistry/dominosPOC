package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class DominosPage {
    private WebDriver driver;
    private WebDriverWait wait;
    public DominosPage(WebDriver driver, WebDriverWait wait){
        this.driver=driver;
        this.wait=wait;
    }
    private By orderOnlineButton = By.cssSelector("#online-banner button");
    private By addressFieldText = By.cssSelector("input[placeholder='Enter your delivery address']");
    private By areaLocalityFieldText = By.cssSelector("input[placeholder='Enter Area / Locality']");
    private By localityListFirstElem = By.xpath("//p[@class='suggestions']/../ul/li[1]");
    private By cartItemName=By.xpath("//div[@class='crt-itms']//span[@class='crt-cnt-descrptn-ttl']");
    private By increaseCartItem = By.xpath("../../..//div[@data-label='increase']");
    private By descreaseCartItem = By.xpath("../../..//div[@data-label='decrease']");
    private By itemQuantity = By.xpath("../../..//div[@data-label='quantity']/span");
    private By itemTotalPrice = By.xpath("../../..//span[@class='rupee']");
    private By itmesSubTotalPrice = By.cssSelector(".chkot-ftr span.rupee");
    private By extraCheeseOption = By.xpath("//div[@data-label='add extra cheese']");
    private By noThanksButton = By.xpath("//div[@data-label='add extra cheese']//button[@data-label='Add button']");
    private By itemAddToCartButton(String sectionName, String itemName){
        return By.cssSelector("#mn-lft>div div[data-label='" + sectionName +"']>div>div[data-label='"+ itemName+"']>div button[data-label='addTocart']");
    }
    private By itemPrice(String sectionName, String itemName){
        return By.cssSelector("#mn-lft>div div[data-label='"+sectionName+"']>div>div[data-label='"+itemName+"']>div span.rupee");
    }
    private By checkoutButton = By.cssSelector("button[data-label='miniCartCheckout']");
    private By checkoutSubTotal = By.cssSelector("span[data-label='Sub Total']~span span.rupee");


    public void clickOrderOnlineButton(){
        driver.findElement(orderOnlineButton).click();
    }
    public void clickAddAddress(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(addressFieldText));
        driver.findElement(addressFieldText).click();
    }
    public void clickAreaLocality(String pinCode){
        wait.until(ExpectedConditions.visibilityOfElementLocated(areaLocalityFieldText));
        driver.findElement(areaLocalityFieldText).click();
        driver.findElement(areaLocalityFieldText).sendKeys(pinCode);
    }
    public void clickFirstLocality(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(localityListFirstElem));
        driver.findElement(localityListFirstElem).click();
    }
    public void addItemToCart(String sectionName, String itemName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(itemAddToCartButton(sectionName, itemName)));
        driver.findElement(itemAddToCartButton(sectionName, itemName)).click();

            if(driver.findElements(noThanksButton).size() !=0 ){
                driver.findElement(noThanksButton).click();
            }
            else{
                System.out.println("No option to add extra cheese");
            }


        wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemName));

    }
    public void increaseCartItemQunatity(String itemName){
        List<WebElement> cartItems = driver.findElements(cartItemName);

        for(int i=0;i<cartItems.size();i++){
            String name = cartItems.get(i).getText();
            //System.out.println(name);
            if(cartItems.get(i).getText().equals(itemName)){
                cartItems.get(i).findElement(increaseCartItem).click();
            }

        }
    }
    public void decreaseCartItemQunatity(String itemName){
        List<WebElement> cartItems = driver.findElements(cartItemName);

        for(int i=0;i<cartItems.size();i++){
            String name = cartItems.get(i).getText();
            //System.out.println(name);
            if(cartItems.get(i).getText().equals(itemName)){
                cartItems.get(i).findElement(descreaseCartItem).click();
            }

        }
    }
    public void setItemQuantity(String itemName, int quantity){
        List<WebElement> cartItems = driver.findElements(cartItemName);
        int currentItemQuantity;
        var jsExecutor = (JavascriptExecutor)driver;
        String script = "arguments[0].scrollIntoView();";

        for(int i=0;i<cartItems.size();i++){
            String name = cartItems.get(i).getText();
            //System.out.println(name);

            currentItemQuantity = Integer.parseInt(cartItems.get(i).findElement(itemQuantity).getText());

            if(cartItems.get(i).getText().equals(itemName)){
                if(quantity > currentItemQuantity){
                    do{
                        jsExecutor.executeScript(script,cartItems.get(i).findElement(increaseCartItem));
                        cartItems.get(i).findElement(increaseCartItem).click();
                        currentItemQuantity = Integer.parseInt(cartItems.get(i).findElement(itemQuantity).getText());
                    }while(quantity != currentItemQuantity);
                }
                else if(quantity < currentItemQuantity)
                {
                    do{
                        jsExecutor.executeScript(script,cartItems.get(i).findElement(increaseCartItem));
                        cartItems.get(i).findElement(descreaseCartItem).click();
                        currentItemQuantity = Integer.parseInt(cartItems.get(i).findElement(itemQuantity).getText());
                    }while(quantity != currentItemQuantity);
                }
                else{
                    System.out.println("Desired item quantity matches existing quantity!");
                }
            }
        }

    }

    public double getItemUnitPrice(String sectionName, String itemName){
        return Double.parseDouble(driver.findElement(itemPrice(sectionName, itemName)).getText());
    }
    public int getItemQuantity(String itemName){
        List<WebElement> cartItems = driver.findElements(cartItemName);
        int cartQuantity =0;
        for(int i=0;i<cartItems.size();i++){
            if(cartItems.get(i).getText().equals(itemName)){
                cartQuantity = Integer.parseInt(cartItems.get(i).findElement(itemQuantity).getText());
            }
        }
        return cartQuantity;
    }
    public double getItemTotalPrice(String itemName){
        List<WebElement> cartItems = driver.findElements(cartItemName);
        double itemTotalPrice =0.00;
        for(int i=0;i<cartItems.size();i++){
            if(cartItems.get(i).getText().equals(itemName)){
                itemTotalPrice = Double.parseDouble(cartItems.get(i).findElement(this.itemTotalPrice).getText());
            }
        }
        return itemTotalPrice;
    }

    public double getItmesSubTotalPrice(){
        return Double.parseDouble(driver.findElement(itmesSubTotalPrice).getText());
    }
    public void miniCartCheckoutClick(){
        driver.findElement(checkoutButton).click();
    }
    public double getCheckoutSubTotal(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutSubTotal));
        return Double.parseDouble(driver.findElement(checkoutSubTotal).getText());
    }
}
