var app = angular.module("photostock-app");

app.controller("VendorController", ['$scope', '$location', 'VendorService', '$rootScope', function ($scope, $location, VendorService, $rootScope) {

    VendorService.getAllResolutions().then(function (response) {
        var resolutions = response.data;

        if (resolutions === undefined) {
            alert("COULD NOT RETRIEVE RESOLUTIONS");
        }

        $rootScope.resolutions = resolutions;
    });

    $scope.uploadPhoto = function(){

        // iterating through all available resolutions in rootscope
        angular.forEach($rootScope.resolutions, function(entry){
            // if a resolution was checked then an object in rootscope is created with resolution's name and its 'checked' attribute is set to 'true'
            // we're checking if v
           if($rootScope[entry.name].checked){
               var price = $rootScope[entry.name].price;
               // if price was less then minimal or greater than maximum then we're alerting the user of bad price input
               if(price < entry.lowestPrice || price > entry.highestPrice){
                   alert("YOU CANT SET PRICE LESS THAN MINIMUM OR GREATER THAN MAXIMUM!");
                   // telling user to gtfo and send him back to form
                   return;
               }


               // getting dimensions of user's picture
               var width = $scope.imgw;
               var height = $scope.imgh;


               // if image resolution is less resolution's dimensions
               if(width < entry.width || height < entry.height){
                   // telling user to gtfo
                   alert("YOU CANT SELL PICTURES IN HIGHER RESOLUTIONS THAN THEIR OWN!");
                   return;
               }
           }
        });

    };

    $scope.setPhotos = function (photos) {
        // since object is hot-swapped we need to tell angular that we swapped it
        $scope.$apply(function () {
            $scope.photo = photos[0];
            $scope.photo.alertCount = photos[0].length;

            var debug = photos[0];

            // converting blobs to local image URLs
            var photoURL = URL.createObjectURL(photos[0]);
            $scope.photoURL = photoURL;


                var img = new Image();
                img.src = photoURL;

                img.onload = function () {
                    $scope.imgw = img.width;
                    $scope.imgh = img.height;
                };

        });
    }
}]);