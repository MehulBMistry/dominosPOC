# Dominos: Scenario – Add & Remove Products
## Author: Mehul Misrty

### Scenario

    • Open www.dominos.co.in
    • Click Order Online Now
    • Click Enter your address and type pin code and select first suggestion. 
    • Section VEG PIZZA add following product 2Qty each. 
            ▪ Caprese Gourmet Pizza
            ▪ Margherita
            ▪ Peppy Paneer
    • Assert Cart Subtotal Value with Sum of (Each Product Value *Qty)
    • Section BEVERAGES add following product 12Qty each. 
            ▪ Pepsi 475ml
    • Assert Cart Subtotal Value with Sum of (Each Product Value *Qty)
    • Remove following products from Cart. 
            ▪ Margherita – remove 1 Qty.
            ▪ Pepsi 475ml – remove 6 Qty.
    • Assert Cart Subtotal Value with Sum of (Each Product Value *Qty)
    • Get Sub Total Value & Click Check Out 
    • Assert the Subtotal (Checkout) With Subtotal (Place Order Price Details)


## Run Testng.xml file to execute test in multiple browsers