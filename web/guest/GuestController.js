var app = angular.module("photostock-app");

app.controller("GuestController", ['$scope', '$location', function ($scope, $location){

    // navigate through navbar
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
    };


    $scope.login = function(){
        var user = $scope.user;


        // validates
        if (user === undefined || user.username === undefined || user.password === undefined || user.username == "" || user.password == ""){
            return;
        }


        $scope.loginError = true;
    };


    $scope.goto = function(page){
        if(page == "/forgotPassword"){
            $location.path("/forgotPassword");
        }
    }
}]);