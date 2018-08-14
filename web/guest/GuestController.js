var app = angular.module("photostock-app");

app.controller("GuestController", ['$scope', '$rootScope', '$location', 'GuestService', function ($scope, $rootScope, $location, GuestService) {

    // navigate through navbar
    $scope.goto = function (page) {
        alert("opened");

        switch (page) {
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

    // validates input and tries to log user in
    $scope.login = function () {
        var user = angular.copy($scope.user);

        // validates
        if (user === undefined || user.username === undefined || user.password === undefined || user.username == "" || user.password == "") {
            return;
        }

        // sends request to REST API via service
        GuestService.login(user).then(function (response) {
            var returned_value = response.data;

            // bad login
            if (returned_value == null) {
                $scope.loginError = true;
                return;
            }

            if (returned_value.account_type === "regular") {
                alert("You've logged in with a regular account!");
            }

            else if (returned_value.account_type === "vendor") {
                alert("You've logged in with a vendor account!");
            }

            else if (returned_value.account_type === "moderator") {
                alert("You've logged in with an moderator account!");
            }

            else if (returned_value.account_type === "administrator") {
                alert("You've logged in with an administrator account!");
            }

            else if (returned_value.account_type === "operator") {
                alert("You've logged in with an operator account!");
            }

            // saving user to rootScope
            $rootScope.user = response.data;
        });
    };


    $scope.register = function() {

        alert("ALERT ZENERAAAAAAAAAAAAL");

        var user = angular.copy($scope.user);

        // validates
        if (user === undefined || user.username === undefined || user.password === undefined || user.email === undefined || user.username == "" || user.password == "" || user.email == "") {
            return;
        }

        GuestService.register(user).then(function(response){
            var returned_value = response.data;

            // failed registration
            if(returned_value == null){
                alert("Registration failed.");
                $scope.registrationError = true;
            }
            else{
                alert("You have successfully registered!");
                // saving the user to rootScope
                $rootScope.user = response.data;
            }
        });
    };

    // redirects to a template page
    $scope.goto = function (page) {
        if (page == "/forgotPassword") {
            $location.path("/forgotPassword");
        }
    }
}]);