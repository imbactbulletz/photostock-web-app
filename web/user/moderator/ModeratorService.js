var app = angular.module("photostock-app");

app.factory('ModeratorService', ['$http', function($http){
    var service = {};

    service.getMembersFor = function (companyName){
        return $http.get("http://localhost:8080/Photostock/rest/user/getMembersFor="+ companyName);
    };

    service.addModerator = function(moderator){
        return $http.post("http://localhost:8080/Photostock/rest/user/addModerator", moderator);
    };

    service.removeMember = function(username){
        return $http.get("http://localhost:8080/Photostock/rest/user/removeMembership=" + username);
    };

    service.getPendingApplicationFor = function(companyName){
        return $http.get("http://localhost:8080/Photostock/rest/app/getPendingApplicationsFor=" + companyName);
    };

    service.setApplicationStatus = function(username, status, company){
        return $http.get("http://localhost:8080/Photostock/rest/app/setApplicationStatus=" + status + ",username=" + username + ",company=" + company);
    };

    return service;
}]);