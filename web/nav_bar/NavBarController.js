var app = angular.module("photostock-app");

app.controller("NavBarController", ['$scope', '$location', '$rootScope', function ($scope, $location, $rootScope){


    // navigation through navbar
    $scope.goto = function(page){
        switch(page){
            case "login":
                $location.path("/login");
                break;

            case "register":
                $location.path("/register");
                break;

            case "operator_settings":
                $location.path("/operator_settings");
                break;

            case "application":
                $location.path("/application");
                break;

            case "upload":
                $location.path("/upload");
                break;

            case "user_settings":
                $location.path("/user_settings");
                break;

            case "uploaded_photos":
                $location.path("/uploaded_photos");
                break;

            case "apply_for_company":
                $location.path("/apply_for_company");
                break;

            case "moderator_dashboard":
                $location.path("/moderator_dashboard");
                break;

            case "operator_dashboard":
                $location.path("/operator_dashboard");
                break;

            case "administrator_dashboard":
                $location.path("/administrator_dashboard");
                break;

            case "shopping_cart":

                var cart_str = sessionStorage.getItem('cart');

                // disabling user to see empty shopping cart
                // if(cart_str === undefined || cart_str == undefined){
                //     alert("You don't have anything in your shopping cart!");
                //     return;
                // }

                var cart = JSON.parse(cart_str);
                // sorting items by price
                cart.sort(function (a, b) { return b.selectedResolution.price - a.selectedResolution.price });

                // checking how many items are in the cart
                var items = cart.length;
                var itemsToDiscount = Math.floor(items/4);

                // checking prices
                var totalSum = 0;
                var discountedSum = 0;
                for(i = cart.length-1 ; i >= 0; i--){
                    var price = cart[i].selectedResolution.price;

                    if(itemsToDiscount > 0) {
                        discountedSum += (price / 100) * 5;
                        itemsToDiscount -= 1;
                    }

                    totalSum += price;
                }
                $rootScope.cart = cart;
                $rootScope.cart.discountedPrice = discountedSum;
                $rootScope.cart.totalPrice = totalSum;
                $location.path("/shopping_cart");
                break;
                
            default:
                $location.path("/");
        }
    }
}]);