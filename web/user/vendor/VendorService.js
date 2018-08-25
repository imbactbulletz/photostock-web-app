var app = angular.module("photostock-app");

app.factory('VendorService', ['$http', function($http){
    var service = {};

    service.getAllResolutions = function(){
      return $http.get("http://localhost:8080/Photostock/rest/resolution/getAll");
    };

    return service;
}]);