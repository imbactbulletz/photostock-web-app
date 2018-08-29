var app = angular.module("photostock-app");

app.controller("RegularUserController", ['$scope', 'UserService', '$location', '$rootScope', function($scope, UserService, $location, $rootScope){

    $scope.uploadTestPhotos = function(){
        var username = angular.copy($scope.user.username);
        var photos = $scope.testPhotos;

        // validation
        if(photos.length < 10){
            alert("YOU HAVE TO SELECT EXACTLY 10 IMAGES");
            return;
        }
        
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

            var debug = photos;

            // converting blobs to local image URLs
            var testPhotosURL = [];

            angular.forEach($scope.testPhotos, function(entry){
                testPhotosURL.push(URL.createObjectURL(entry));
            });

            $scope.testPhotosURL = testPhotosURL;


            angular.forEach($scope.testPhotosURL, function(testPhotoURL){
                var img = new Image();
                img.src = testPhotoURL;

                img.onload = function(){
                    //todo logika za proveravanje velicine slike
                };
                console.log(img.width, img.height);
            });
        });

    };

    $scope.userSettings = function(){
        var password = $scope.change_password;
        var credit_card = $scope.change_credit_card;
        var deactivate = $scope.change_deactivate;
        var username = $rootScope.user.username;

        UserService.userSettings(username, password, credit_card, deactivate).then(function(response){

           var succeeded = response.data;

           if(succeeded){
               alert("Successfully changed settings!");
               $rootScope.user.creditcard = credit_card;
           }
           else {
               alert("Could not change settings!");
           }
        });
    }
}]);