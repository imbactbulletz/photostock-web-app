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

        .when("/registration_successful_individual", {
            templateUrl: "guest/registration_successful_individual.html"
        })

        .when("/registration_successful_company", {
            templateUrl: "guest/registration_successful_company.html"
        })

        .when("/unverified_account",{
            templateUrl: "user/regular/unverified_account.html"
        })

        .when("/sent_password",{
            templateUrl: "user/regular/sent_password.html"
        })


        .otherwise({
            redirectTo: "/"
        });
});