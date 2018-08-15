var app = angular.module("photostock-app");

app.controller("OperatorController", ['$scope', 'OperatorService', '$location', function($scope, OperatorService, $location){

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