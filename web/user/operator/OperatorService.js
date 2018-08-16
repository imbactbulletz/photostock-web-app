var app = angular.module("photostock-app");

app.factory('OperatorService', ['$http', function($http){
    var service = {};

    service.changePassword = function(user){
        return $http.post("http://localhost:8080/Photostock/rest/user/changePassword", user);
    };

    service.getAllUsers = function(){
        return $http.post("http://localhost:8080/Photostock/rest/user/getAllUsers");
    };

    service.removeUser = function(username){
        return $http.post("http://localhost:8080/Photostock/rest/user/deleteUser", username);
    };

    return service;
}]);