var app = angular.module("photostock-app");

app.config(function ($routeProvider) {

    $routeProvider
        .when("/", {
            templateUrl: "guest/main.html"
        })
        .otherwise({
            redirectTo: "/"
        });
});