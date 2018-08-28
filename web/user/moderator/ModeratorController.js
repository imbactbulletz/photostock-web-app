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


    // getting pending applications for company

    ModeratorService.getPendingApplicationFor(companyName).then(function(response){
        var pendingApplications = response.data;

        if(pendingApplications === undefined){
            alert("COULD NOT FETCH PENDING APPLICATIONS FOR THE COMPANY");
            return;
        }

        $rootScope.pendingApplications = pendingApplications;
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
    };

    $scope.reviewApplication = function(applicant){
        OperatorService.getApplicationPhotos(applicant).then(function(response){
            var application_photos = response.data;

            if(application_photos === undefined){
                alert("COULD NOT FETCH APPLICATION PHOTOS");
                return;
            }

            $rootScope.application_photos = application_photos;
            $rootScope.applicant = applicant;
            $location.path("/moderator_review_application");
        });
    };


    $scope.setApplicationStatus = function(status){
        var username = $rootScope.applicant;
        var company = $rootScope.user.company;

        if(username === undefined){
            alert("APPLICANT'S NAME IS UNDEFINED");
            return;
        }

        ModeratorService.setApplicationStatus(username, status, company).then(function(response){
           var succeeded = response.data;

           if(succeeded === undefined || succeeded === false){
               alert("COULDN'T SET APPLICATION STATUS FOR APPLICANT");
               return;
           }

            // getting pending applications for company

            ModeratorService.getPendingApplicationFor(companyName).then(function(response){
                var pendingApplications = response.data;

                if(pendingApplications === undefined){
                    alert("COULD NOT FETCH PENDING APPLICATIONS FOR THE COMPANY");
                    return;
                }

                $rootScope.pendingApplications = pendingApplications;
            });

           $location.path("/moderator_dashboard");
        });
    };
}]);