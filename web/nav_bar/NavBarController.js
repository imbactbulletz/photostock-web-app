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

                
            default:
                $location.path("/");
        }
    }
}]);