Feature: Ecart E2ETest

Scenario: Validate with correct data
Given user should be registered already and generate login token
When the user should get all the products and fetch iPhone productId from it
Then user adds Iphone to the cart
And user should order the items added in cart
Then user should fetch the orders list and validate Iphone is ordered
And user should delete all the orders in the orders 