var app = angular.module("photostock-app");

app.factory('GuestService', ['$http', function($http){
    var service = {};

    service.login = function(user){
       return $http.post("http://localhost:8080/Photostock/rest/user/login", user);
    };

    service.register = function(user){
        return $http.post("http://localhost:8080/Photostock/rest/user/register", user);
    };

    service.registerCompany = function(company){
        return $http.post("http://localhost:8080/Photostock/rest/company/register", company);
    };

    service.sendPassword = function(username){
        return $http.get("http://localhost:8080/Photostock/rest/user/sendPassword=" + username);
    };

    service.changePassword = function(user){
        return $http.post("http://localhost:8080/Photostock/rest/user/changePassword", user);
    };

    service.searchPhotos = function(criteria, term){
        return $http.get("http://localhost:8080/Photostock/rest/photo/getPhotosBy=" + criteria + ",term=" + term);
    };

    return service;
}]);