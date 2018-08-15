var app = angular.module("photostock-app", ["ngRoute", "ngMaterial", "ngMessages", "md.data.table"]);

app.run(function($rootScope){
    // initializing radio button value
    $rootScope.company = {};
    $rootScope.company.membership = "regular";

});