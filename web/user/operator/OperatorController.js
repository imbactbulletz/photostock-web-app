var app = angular.module("photostock-app");

app.controller("OperatorController", ['$scope', 'OperatorService', 'UserService', '$location', '$rootScope', function($scope, OperatorService, UserService, $location, $rootScope){


    //calling service to get all users
    OperatorService.getAllUsers().then(function(response){
        $rootScope.users = response.data;
    });

    OperatorService.getPendingApplications().then(function(response){
        var pending_applications = response.data;

        if(pending_applications === undefined){
            alert("COULD NOT FETCH PENDING APPLICATIONS");
            return;
        }

        $rootScope.pending_applications = response.data;
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

    $scope.reviewApplication = function(applicant){
          OperatorService.getApplicationPhotos(applicant).then(function(response){
              var application_photos = response.data;

              if(application_photos === undefined){
                  alert("COULD NOT FETCH APPLICATION PHOTOS");
                  return;
              }

              $rootScope.application_photos = application_photos;
              $location.path("/review_application");
              $rootScope.applicant = applicant;
          });
    };

    $scope.rateApplication = function(){
        var rating = $rootScope.rating;
        var applicant = $rootScope.applicant;

        OperatorService.rateApplication(applicant, rating).then(function(response){
            var successfully_rated = response.data;

            if(successfully_rated){
                alert("You have sucessfully rated the user");
            }

            else {
                alert("Could not rate the user!");
            }
        });
    };
}]);