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

        .when("/operator_dashboard",{
            templateUrl: "user/operator/dashboard.html",
            controller: "OperatorController"
        })

        .when("/setup_initial_settings",{
            templateUrl: "user/operator/setup_initial_settings.html",
            controller: "OperatorController"
        })

        .when("/operator_settings",{
            templateUrl: "user/operator/settings.html",
            controller: "OperatorController"
        })

        .when("/review_application",{
            templateUrl: "user/operator/review_application.html",
            controller: "OperatorController"
        })

        .when("/application",{
            templateUrl: "user/regular/application.html",
            controller: "RegularUserController"
        })

        .when("/sent_application",{
            templateUrl: "user/regular/sent_application.html"
        })

        .when("/administrator_dashboard",{
            templateUrl: "user/administrator/dashboard.html",
            controller: "AdministratorController"
        })

        .when("/upload",{
            templateUrl: "user/vendor/upload.html",
            controller: "VendorController"
        })

        .when("/user_settings",{
            templateUrl: "user/regular/settings.html",
            controller: "RegularUserController"
        })

        .when("/uploaded_photos",{
            templateUrl: "user/vendor/uploaded_photos.html",
            controller: "VendorController"
        })

        .when("/moderator_set_up_settings", {
            templateUrl: "user/moderator/setup_initial_settings.html",
            controller: "ModeratorController"
        })

        .when("/moderator_dashboard", {
            templateUrl: "user/moderator/dashboard.html",
            controller: "ModeratorController"
        })


        .otherwise({
            redirectTo: "/"
        });
});