var app = angular.module("photostock-app");

app.config(function ($routeProvider) {

    $routeProvider
        .when("/", {
            templateUrl: "guest/main.html",
            controller: "GuestController"
        })

        .when("/login", {
            templateUrl: "guest/login.html",
            controller: "GuestController"
        })

        .when("/register", {
            templateUrl: "guest/register.html",
            controller: "GuestController"
        })

        .when("/forgotPassword", {
            templateUrl: "guest/forgot_password.html",
            controller: "GuestController"
        })

        .otherwise({
            redirectTo: "/"
        });
});