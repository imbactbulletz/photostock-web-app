var app = angular.module("photostock-app");

app.controller("GuestController", ['$scope', '$location', function ($scope, $location){


    // navigation through navbar
    $scope.goto = function(page){
        alert("opened");

        switch(page){
            case "login":
                $location.path("/login");
                break;

            case "register":
                $location.path("/register");
                break;

            default:
                $location.path("/");
        }
    }
}]);