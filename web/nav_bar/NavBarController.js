var app = angular.module("photostock-app");

app.controller("NavBarController", ['$scope', '$location', function ($scope, $location){


    // navigation through navbar
    $scope.goto = function(page){
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