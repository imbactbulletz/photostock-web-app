var app = angular.module("photostock-app");

app.config(function ($routeProvider) {

    $routeProvider
        .when("/", {
            templateUrl: "guest/main.html",
            controller: "GuestController"
        })

        .when("/login", {
            templateUrl: "guest/login.html"
        })

        .otherwise({
            redirectTo: "/"
        });
});