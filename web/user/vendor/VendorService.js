var app = angular.module("photostock-app");

app.factory('VendorService', ['$http', function($http){
    var service = {};

    service.getAllResolutions = function(){
      return $http.get("http://localhost:8080/Photostock/rest/resolution/getAll");
    };

    service.getAllCategories = function(){
        return $http.get("http://localhost:8080/Photostock/rest/category/getAll");
    };

    service.uploadPhoto = function(title, category, description, resolutions, prices, image, username){
      var inData = new FormData();

      inData.append('title', title);
      inData.append('category', category);
      inData.append('description', description);
      inData.append('resolutions', resolutions);
      inData.append('prices', prices);
      inData.append('photo', image);
      inData.append('username', username);
      return $http({
          url: "http://localhost:8080/Photostock/rest/photo/upload",
          method: "POST",
          data: inData,
          headers: {'Content-Type': undefined}
      });

    };

    return service;
}]);