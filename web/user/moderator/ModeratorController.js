var app = angular.module("photostock-app");

app.controller("ModeratorController", ['$scope', 'OperatorService', 'UserService', '$location', '$rootScope', '$mdDialog', 'ModeratorService', function($scope, OperatorService, UserService, $location, $rootScope, $mdDialog, ModeratorService){

    $scope.changePassword = function(){

        var user = angular.copy($scope.user);

        if(user === undefined){
            return;
        }

        UserService.changePassword(user).then(function(response){
            var returned_value = response.data;

            if(returned_value == null || returned_value == false){
                $scope.ChangePasswordErrorMessage = "Password could not be changed.";
                return;
            }
            else{
                alert("You have successfully changed your password!");
                $location.path("/moderator_dashboard");
            }
        });
    };

}]);