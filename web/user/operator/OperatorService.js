var app = angular.module("photostock-app");

app.factory('OperatorService', ['$http', function($http){
    var service = {};

    service.getAllUsers = function(){
        return $http.post("http://localhost:8080/Photostock/rest/user/getAllUsers");
    };

    service.removeUser = function(username){
        return $http.post("http://localhost:8080/Photostock/rest/user/deleteUser", username);
    };

    service.getPendingApplications = function(){
        return $http.get("http://localhost:8080/Photostock/rest/app/getPendingApplications");
    };

    service.getApplicationPhotos = function(applicant){
        return $http.get("http://localhost:8080/Photostock/rest/app/getApplicationPhotos=" + applicant);
    };

    service.rateApplication = function(applicant, rating){
        return $http.get("http://localhost:8080/Photostock/rest/app/rateApplication/applicant="+ applicant + "/rating=" + rating);
    };

    service.getPendingPhotos = function(){
        return $http.get("http://localhost:8080/Photostock/rest/photo/getPendingPhotos");
    };

    service.setPhotoStatus = function(photoID, status){
        return $http.get("http://localhost:8080/Photostock/rest/photo/setStatus=" + status + ",id=" + photoID);
    };

    service.getPendingCompanies = function(){
        return $http.get("http://localhost:8080/Photostock/rest/company/getPendingCompanies");
    };

    service.setCompanyStatus = function(companyID, status){
        return $http.get("http://localhost:8080/Photostock/rest/company/setCompanyStatus=" + status + ",id=" + companyID);
    };

    return service;
}]);