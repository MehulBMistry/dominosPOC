package dominosTest;

import base.BaseTests;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.DominosPage;
import utils.ExcelUtil;

import java.io.IOException;

public class DominosTests extends BaseTests {

    @Test
    public void dominosCartTest() throws InterruptedException, IOException {
        //Open www.dominos.co.in
        DominosPage dominosPage = homePage.openDominosSite();

        double sumOfEachItemTotalPrice = 0.00;
        double sumOfEachItemTotalPrice2 = 0.00;
        SoftAssert softAssert = new SoftAssert();

        //Click Order Online Now
        dominosPage.clickOrderOnlineButton();

        //Click Enter your address and type pin code and select first suggestion
        dominosPage.clickAddAddress();
        dominosPage.clickAreaLocality("600100");
        dominosPage.clickFirstLocality();

        /*
            • Section VEG PIZZA add following product 2Qty each.
            ▪ Caprese Gourmet Pizza
            ▪ Margherita
            ▪ Peppy Paneer

            • Assert Cart Subtotal Value with Sum of (Each Product Value *Qty)

            • Section BEVERAGES add following product 12Qty each.
            ▪ Pepsi 475ml
            • Assert Cart Subtotal Value with Sum of (Each Product Value *Qty)
         */

        ExcelUtil.setExcelFileSheet("Sheet1");
        for (int i = 0; i < ExcelUtil.getRowCount(); i++) {
            dominosPage.addItemToCart(ExcelUtil.getCellData(i + 1, 0), ExcelUtil.getCellData(i + 1, 1));
            dominosPage.setItemQuantity(ExcelUtil.getCellData(i + 1, 1), ExcelUtil.getCellIntData(i + 1, 2));

            sumOfEachItemTotalPrice = sumOfEachItemTotalPrice +
                    dominosPage.getItemUnitPrice(ExcelUtil.getCellData(i + 1, 0), ExcelUtil.getCellData(i + 1, 1)) * dominosPage.getItemQuantity(ExcelUtil.getCellData(i + 1, 1));

            softAssert.assertEquals(dominosPage.getItmesSubTotalPrice(), sumOfEachItemTotalPrice, "Price don't match");
        }


        /*
            • Remove following products from Cart.
            ▪ Margherita – remove 1 Qty.
            ▪ Pepsi 475ml – remove 6 Qty.
         */

        ExcelUtil.setExcelFileSheet("Sheet2");
        for (int i = 0; i < ExcelUtil.getRowCount(); i++) {
            dominosPage.setItemQuantity(ExcelUtil.getCellData(i + 1, 0), ExcelUtil.getCellIntData(i + 1, 1));
        }

        //• Assert Cart Subtotal Value with Sum of (Each Product Value *Qty)

        ExcelUtil.setExcelFileSheet("Sheet1");
        for (int i = 0; i < ExcelUtil.getRowCount(); i++) {
            sumOfEachItemTotalPrice2 = sumOfEachItemTotalPrice2 +
                    dominosPage.getItemUnitPrice(ExcelUtil.getCellData(i + 1, 0), ExcelUtil.getCellData(i + 1, 1)) * dominosPage.getItemQuantity(ExcelUtil.getCellData(i + 1, 1));
        }

        softAssert.assertEquals(dominosPage.getItmesSubTotalPrice(), sumOfEachItemTotalPrice2, "Price don't match");

        //Get Sub Total Value & Click Check Out
        dominosPage.miniCartCheckoutClick();

        //Assert the Subtotal (Checkout) With Subtotal (Place Order Price Details)
        softAssert.assertEquals(dominosPage.getCheckoutSubTotal(), sumOfEachItemTotalPrice2, "Price don't match");
        softAssert.assertAll();
    }

}
