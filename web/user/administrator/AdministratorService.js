var app = angular.module("photostock-app");

app.factory('AdministratorService', ['$http', function($http){
    var service = {};

    service.addUser = function(user){
      return $http.post("http://localhost:8080/Photostock/rest/user/addUser", user);
    };

    service.addCategory = function(name){
      return $http.get("http://localhost:8080/Photostock/rest/category/addCategory=" + name);
    };

    service.deleteCategory = function(name){
      return $http.get("http://localhost:8080/Photostock/rest/category/deleteCategory=" + name);
    };

    return service;
}]);