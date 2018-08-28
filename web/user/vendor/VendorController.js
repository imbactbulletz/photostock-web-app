var app = angular.module("photostock-app");

app.controller("VendorController", ['$scope', '$location', 'VendorService', '$rootScope', 'UserService', function ($scope, $location, VendorService, $rootScope, UserService) {

    // getting all the resolutions
    VendorService.getAllResolutions().then(function (response) {
        var resolutions = response.data;

        if (resolutions === undefined) {
            alert("COULD NOT RETRIEVE RESOLUTIONS");
        }

        $rootScope.resolutions = resolutions;
    });

    // getting all the categories
    VendorService.getAllCategories().then(function(response){
        var categories = response.data;

        if(categories === undefined){
            alert("COULD NOT FETCH CATEGORIES");
        }

        $rootScope.categories = categories;
    });


    // getting uploaded photos
    VendorService.getUploadedPhotos($rootScope.user.username).then(function(response){
        var uploaded_photos = response.data;

        if(uploaded_photos === undefined){
            alert("COULD NOT FETCH UPLOADED PHOTOS");
            return;
        }

        $rootScope.uploadedPhotos = uploaded_photos;
    });


    // getting all the active companies
    VendorService.getActiveCompanies().then(function(response){
        var companies = response.data;

        if(companies === undefined){
            alert("COULD NOT FETCH ACTIVE COMPANIES");
            return;
        }

        $rootScope.activeCompanies = companies;
    });

    $scope.uploadPhoto = function(){

        var username = $rootScope.user.username;

        VendorService.canUpload(username).then(function(response){

            var canUpload= response.data;

            if(canUpload){
                // checking for credit card
                UserService.getCreditCard(username).then(function(response){
                    var hasCreditCard = response.data;

                    if(!hasCreditCard){
                        alert("You have to set up a credit card before uploading!");
                        return;
                    }

                    else {

                        var resolutions = [];
                        var prices = [];
                        // iterating through all available resolutions in rootscope
                        angular.forEach($rootScope.resolutions, function(entry){
                            // if a resolution was checked then an object in rootscope is created with resolution's name and its 'checked' attribute is set to 'true'
                            // we're checking if v
                            if( typeof $rootScope[entry.name] !== 'undefined' && $rootScope[entry.name].checked){
                                var price = $rootScope[entry.name].price;
                                // if price was less then minimal or greater than maximum then we're alerting the user of bad price input

                                if(price === undefined){
                                    alert("You have to set the price on the resolutions you've checked!");
                                    return;
                                }

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

                                // adding resolutions and prices for picture in arrays
                                resolutions.push(entry.name);
                                prices.push($rootScope[entry.name].price)
                            }
                        });


                        var title = $scope.photoTitle;
                        var category = $scope.photoCategory;
                        var description = $scope.photoDescription;
                        var username = $rootScope.user.username;
                        var photo = $scope.photo;


                        var resolutions_str = resolutions.join(";");

                        var prices_str = prices.join(";");

                        VendorService.uploadPhoto(title, category, description, resolutions_str, prices_str, photo, username).then(function(response){
                            var successful = response.data;

                            if(successful){
                                alert("YOU HAVE SUCCESSFULLY UPLOADED THE PHOTO");
                            }
                            else {
                                alert("COULD NOT UPLOAD THE PHOTO");
                            }
                        });
                    }

                });
            }

            else {
                alert("YOU HAVE EXCEEDED THE LIMIT OF UPLOADS FOR THIS DAY/WEEK");
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
    };


    $scope.deletePhoto = function(ID){

        VendorService.deletePhoto(ID).then(function(response){
            // getting uploaded photos
            VendorService.getUploadedPhotos($rootScope.user.username).then(function(response){
                var uploaded_photos = response.data;

                if(uploaded_photos === undefined){
                    alert("COULD NOT FETCH UPLOADED PHOTOS");
                    return;
                }

                $rootScope.uploadedPhotos = uploaded_photos;
            })
        });
    };

    $scope.applyForCompany = function(){


        // validation as md-select required doesn't work
        if($rootScope.selectedCompany === undefined)
            return;

        var selectedCompany = $rootScope.selectedCompany;
        var username = $rootScope.user.username;

        VendorService.applyForCompany(username, selectedCompany).then(function(response){
            var succeeded = response.data;

            if(succeeded === undefined || succeeded === false){
                alert("COULD NOT APPLY FOR COMPANY");
                return;
            }

            $location.path("/apply_for_company_successful");
        });
    }
}]);