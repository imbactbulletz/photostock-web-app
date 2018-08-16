var app = angular.module("photostock-app");

app.controller("OperatorController", ['$scope', 'OperatorService', '$location', '$rootScope', function($scope, OperatorService, $location, $rootScope){


    //calling service to get all users
    OperatorService.getAllUsers().then(function(response){
        $rootScope.users = response.data;
    });


    $scope.changePassword = function(){

        var user = angular.copy($scope.user);

        if(user === undefined){
            return;
        }

        OperatorService.changePassword(user).then(function(response){
            var returned_value = response.data;

            if(returned_value == null || returned_value == false){
                $scope.ChangePasswordErrorMessage = "Password could not be changed.";
                return;
            }
            else{

                $location.path("/operator_dashboard");
            }
        });
    };

}]);