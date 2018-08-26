var app = angular.module("photostock-app");

app.controller("AdministratorController", ['$scope', 'AdministratorService', 'OperatorService', 'UserService', '$location', '$rootScope', 'VendorService', function($scope, AdministratorService, OperatorService, UserService, $location, $rootScope, VendorService){


    //calling service to get all users
    OperatorService.getAllUsers().then(function(response){
        $rootScope.users = response.data;
    });

    // getting all categories
    VendorService.getAllCategories().then(function(response){
        var categories = response.data;

        if(categories === undefined){
            alert("COULDN'T FETCH CATEGORIES!");
        }

        $rootScope.categories = response.data;
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

    $scope.addOperator = function(){

        var user = angular.copy($scope.tmp_operator);

        if(user === undefined || user.username === undefined || user.password === undefined ||
            user.email === undefined || user.username == "" || user.password == "" || user.email == ""){
            $scope.addOperatorErrorMessage = "Please fill out all the fields.";
            return;
        }

        AdministratorService.addUser(user).then(function(){
            OperatorService.getAllUsers().then(function(response){
                $rootScope.users = response.data;
            });
        });

    };


    $scope.addCategory = function(){
        var name = $scope.tmp_category_name;

        AdministratorService.addCategory(name).then(function(response){
            var succeeded = response.data;

            if(succeeded){

                // getting all categories
                VendorService.getAllCategories().then(function(response){
                    var categories = response.data;

                    if(categories === undefined){
                        alert("COULDN'T FETCH CATEGORIES!");
                    }

                    $rootScope.categories = response.data;
                });
            }

            else {
                alert("COULDN'T ADD THE CATEGORY!");
            }
        });

    };

    $scope.deleteCategory = function(name){
        AdministratorService.deleteCategory(name).then(function(response){
            alert("ALEEERT ZENERAAAAAAL");

            var succeeded = response.data;

            if(succeeded){

                // getting all categories
                VendorService.getAllCategories().then(function(response){
                    var categories = response.data;

                    if(categories === undefined){
                        alert("COULDN'T FETCH CATEGORIES!");
                    }

                    $rootScope.categories = response.data;
                });
            }
        });
    };
}]);