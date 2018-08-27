var app = angular.module("photostock-app");

app.controller("ModeratorController", ['$scope', 'OperatorService', 'UserService', '$location', '$rootScope', '$mdDialog', 'ModeratorService', function($scope, OperatorService, UserService, $location, $rootScope, $mdDialog, ModeratorService){

    var companyName = $rootScope.user.company;

    // getting members for the company
    ModeratorService.getMembersFor(companyName).then(function(response){
        var members = response.data;

        if(members === undefined){
            alert("COULD NOT FETCH MEMBERS FOR THE COMPANY");
            return;
        }

        $rootScope.members = members;
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
                alert("You have successfully changed your password!");
                $location.path("/moderator_dashboard");
            }
        });
    };

    $scope.addModerator = function(){
        var moderator = $scope.tmp_moderator;
        // setting newly added moderator's company to current user's company
        moderator.company = $rootScope.user.company;

        ModeratorService.addModerator(moderator).then(function(response){
           var succeeded = response.data;

           if(succeeded === undefined || succeeded === false){
               alert("COULD NOT ADD A MODERATOR");
               return;
           }

           else {
               // getting members for the company
               ModeratorService.getMembersFor(companyName).then(function(response){
                   var members = response.data;

                   if(members === undefined){
                       alert("COULD NOT FETCH MEMBERS FOR THE COMPANY");
                       return;
                   }

                   $rootScope.members = members;
               });
           }
        });
    };

    $scope.removeMember = function(username){
        ModeratorService.removeMember(username).then(function(response){
            var succeeded = response.data;

            if(succeeded === undefined || succeeded == false){
                alert("COULD NOT REVOKE MEMBERSHIP");
                return;
            }

            else {
                ModeratorService.getMembersFor(companyName).then(function(response){
                    var members = response.data;

                    if(members === undefined){
                        alert("COULD NOT FETCH MEMBERS FOR THE COMPANY");
                        return;
                    }

                    $rootScope.members = members;
                });
            }

        });
    }
}]);