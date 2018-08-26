var app = angular.module("photostock-app");

app.factory('UserService', ['$http', function($http){
    var service = {};

    service.changePassword = function(user){
        return $http.post("http://localhost:8080/Photostock/rest/user/changePassword", user);
    };


    service.uploadTestPhotos = function(username, photos){

        var formData = new FormData();

        formData.append('username', username);
        formData.append('photo1', photos[0]);
        formData.append('photo2', photos[1]);
        formData.append('photo3', photos[2]);
        formData.append('photo4', photos[3]);
        formData.append('photo5', photos[4]);
        formData.append('photo6', photos[5]);
        formData.append('photo7', photos[6]);
        formData.append('photo8', photos[7]);
        formData.append('photo9', photos[8]);
        formData.append('photo10', photos[9]);


        return $http({
            url: "http://localhost:8080/Photostock/rest/app/uploadTestPhotos",
            method: 'POST',
            data: formData,
            headers: { 'Content-Type': undefined}
        });
    };

    service.hasPendingApplication = function(username){
        return $http.post("http://localhost:8080/Photostock/rest/app/hasPendingApplication", username);
    };

    service.userSettings = function(username, changed_password, changed_credit_card, deactivate){

      alert("ALERT ZENERAAAAAAAAAL");

      var inData = new FormData();

      inData.append('username', username);

      if(changed_password)
          inData.append('password', changed_password);

      if(changed_credit_card)
          inData.append('creditCard', changed_credit_card);

      if(deactivate)
          inData.append('deactivate', deactivate);

      return $http({
          url: "http://localhost:8080/Photostock/rest/user/changeSettings",
          method: "POST",
          data: inData,
          headers: {'Content-Type': undefined}
      });
    };


    service.getCreditCard = function(username){
      return $http.get("http://localhost:8080/Photostock/rest/user/hasCreditCard=" + username);
    };

    return service;
}]);