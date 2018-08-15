var app = angular.module("photostock-app");

app.factory('OperatorService', ['$http', function($http){
    var service = {};

    service.changePassword = function(user){
        return $http.post("http://localhost:8080/Photostock/rest/user/changePassword", user);
    };

    return service;
}]);