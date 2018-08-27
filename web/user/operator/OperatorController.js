var app = angular.module("photostock-app");

app.controller("OperatorController", ['$scope', 'OperatorService', 'UserService', '$location', '$rootScope', '$mdDialog', function($scope, OperatorService, UserService, $location, $rootScope, $mdDialog){


    //calling service to get all users
    OperatorService.getAllUsers().then(function(response){
        $rootScope.users = response.data;
    });

    // getting pending applications
    OperatorService.getPendingApplications().then(function(response){
        var pending_applications = response.data;

        if(pending_applications === undefined){
            alert("COULD NOT FETCH PENDING APPLICATIONS");
            return;
        }

        $rootScope.pending_applications = response.data;
    });

    // getting pending pictures
    OperatorService.getPendingPhotos().then(function(response){
        var photos = response.data;

        if(photos === undefined){
            alert("COULD NOT FETCH PENDING PHOTOS");
            return;
        }

        $rootScope.pendingPhotos = photos;
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

    $scope.cancelDialog = function () {
        $mdDialog.cancel();
    };

    $scope.previewPhoto = function (ev, photo) {

        $rootScope.selectedPhoto = photo;

        $mdDialog.show({
            controller: 'OperatorController',
            templateUrl: 'user/operator/preview_dialog.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
        })
    };

    $scope.setPhotoStatus = function(photoID, status){
       OperatorService.setPhotoStatus(photoID, status).then(function(response){
           var succeeded = response.data;

           if(succeeded){
               // getting pending pictures
               OperatorService.getPendingPhotos().then(function(response){
                   var photos = response.data;

                   if(photos === undefined){
                       alert("COULD NOT FETCH PENDING PHOTOS");
                       return;
                   }

                   $rootScope.pendingPhotos = photos;
               });
           }

       });
    };

}]);