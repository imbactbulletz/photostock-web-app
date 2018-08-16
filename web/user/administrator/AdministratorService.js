var app = angular.module("photostock-app");

app.factory('AdministratorService', ['$http', function($http){
    var service = {};

    service.addUser = function(user){
      return $http.post("http://localhost:8080/Photostock/rest/user/addUser", user);
    };

    return service;
}]);