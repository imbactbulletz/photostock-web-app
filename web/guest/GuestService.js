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

    service.getPhotoResolutions = function(photoID){
        return $http.get("http://localhost:8080/Photostock/rest/photo/getPhotoResolutions=" + photoID);
    };

    service.checkOut = function(username, cart){
          var inData = new FormData();

          inData.append("username", username);

          for(i = 0 ; i < cart.length ; i++){
              // selected photo's id
              var photoID = cart[i].id;
              // id of selected resolution of the photo
              var photoResolutionID = cart[i].selectedResolution.id;

              var bundle = photoID + ";" + photoResolutionID;

              inData.append(i, bundle);
          }

        return $http({
            url: "http://localhost:8080/Photostock/rest/photo/checkOut",
            method: "POST",
            data: inData,
            headers: {'Content-Type': undefined}
        });

    };

    service.ratePhoto = function(username, photoID, rating){
      return $http.get("http://localhost:8080/Photostock/rest/photo/ratePhoto=" + photoID + ",user=" + username + ",rating=" + rating);
    };

    return service;
}]);