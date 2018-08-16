var app = angular.module("photostock-app");

app.controller("OperatorController", ['$scope', 'OperatorService', 'UserService', '$location', '$rootScope', function($scope, OperatorService, UserService, $location, $rootScope){


    //calling service to get all users
    OperatorService.getAllUsers().then(function(response){
        $rootScope.users = response.data;
    });


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

                $location.path("/operator_dashboard");
            }
        });
    };

    $scope.removeUser = function(username){

        if(username === undefined){
            alert("Could not delete user. No username exists.");
        }

        OperatorService.removeUser(username).then(function(response){
            var returned_value = response.data;


            if(returned_value == true){
                //calling service to get all users
                OperatorService.getAllUsers().then(function(response){
                    $rootScope.users = response.data;
                });
            }

            else {
                alert("There was an error deleting the user.");
            }
        });

    };

}]);