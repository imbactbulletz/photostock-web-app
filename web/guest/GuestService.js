var app = angular.module("photostock-app");

app.factory('GuestService', ['$http', function($http){
    var service = {};

    service.login = function(user){
       return $http.post("http://localhost:8080/Photostock/rest/user/login", user);
    };

    return service;
}]);