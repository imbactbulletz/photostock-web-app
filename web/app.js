var app = angular.module("photostock-app", ["ngRoute", "ngMaterial", "ngMessages", "md.data.table", "file-model", "infinite-scroll"]);

app.run(function($rootScope){
    // initializing radio button value
    $rootScope.company = {};
    $rootScope.company.membership = "regular";

});
