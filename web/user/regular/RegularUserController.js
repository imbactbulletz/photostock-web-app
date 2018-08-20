var app = angular.module("photostock-app");

app.controller("RegularUserController", ['$scope', 'UserService', '$location', '$rootScope', function($scope, UserService, $location, $rootScope){

    $scope.uploadTestPhotos = function(){
        var username = angular.copy($scope.user.username);
        var photos = $scope.testPhotos;

        UserService.uploadTestPhotos(username, photos).then(function(response){
            var successfully_uploaded = response.data;

            if(successfully_uploaded){
                // settings rootscope object so 'APPLY AS A VENDOR' doesn't appear
                $rootScope.user.pending_application = true;
                $location.path("/sent_application");
            }
            else {
                alert("Failed to upload.");
            }
        })

    };


    $scope.setPhotos = function(photos){
        // since object is hot-swapped we need to tell angular that we swapped it
        $scope.$apply(function(){
            $scope.testPhotos = photos;
            $scope.testPhotos.alertCount = $scope.testPhotos.length;


            // converting blobs to local image URLs
            var testPhotosURL = [];

            angular.forEach($scope.testPhotos, function(entry){
                testPhotosURL.push(URL.createObjectURL(entry));
            });

            $scope.testPhotosURL = testPhotosURL;

        });
    };
}]);