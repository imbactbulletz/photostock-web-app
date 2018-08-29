var app = angular.module("photostock-app");

app.controller("GuestController", ['$scope', '$rootScope', '$location', 'GuestService', 'UserService', '$mdDialog', function ($scope, $rootScope, $location, GuestService, UserService, $mdDialog) {
    // navigate through navbar
    $scope.goto = function (page) {
        alert("opened");

        switch (page) {
            case "login":
                $location.path("/login");
                break;

            case "register":
                $location.path("/register");
                break;

            default:
                $location.path("/");
        }
    };

    // validates input and tries to log user in
    $scope.login = function () {
        var user = angular.copy($scope.user);

        // validates
        if (user === undefined || user.username === undefined || user.password === undefined || user.username == "" || user.password == "") {
            return;
        }

        // sends request to REST API via service
        GuestService.login(user).then(function (response) {
            var returned_value = response.data;


            // bad login
            if (returned_value == undefined || returned_value == "") {
                $scope.loginErrorMessage = "Wrong username/password.";
                return;
            }


            // saving user to rootScope
            $rootScope.user = response.data;

            if (returned_value.account_type === "regular") {

                if(returned_value.account_status === "unverified"){
                    $location.path("/unverified_account");
                    return;
                }

                // checking for user apps
                else{
                    var username = angular.copy($scope.user.username);

                    UserService.hasPendingApplication(username).then(function(response){
                        var returned_value = response.data;

                        if(returned_value){
                            $rootScope.user.pending_application = true;
                        }

                        else {
                            $rootScope.user.pending_application = false;
                        }
                    });
                }

                alert("You've logged in with a regular account!");
            }

            else if (returned_value.account_type === "vendor") {
                // alert("You've logged in with a vendor account!");
            }

            else if (returned_value.account_type === "moderator") {
                if(returned_value.account_status === "unverified"){
                    $location.path("/moderator_set_up_settings");
                }
                else
                    $location.path("/moderator_dashboard");
            }

            else if (returned_value.account_type === "administrator") {
                $location.path("/administrator_dashboard")
            }

            else if (returned_value.account_type === "operator") {
                if( returned_value.account_status === "unverified"){
                    $location.path("/setup_initial_settings");
                }

                else {
                    $location.path("/operator_dashboard")
                }
            }
        });
    };


    $scope.register = function() {

        var user = angular.copy($scope.user);

        // validates
        if (user === undefined || user.username === undefined || user.password === undefined || user.email === undefined || user.username == "" || user.password == "" || user.email == "") {
            return;
        }

        GuestService.register(user).then(function(response){
            var returned_value = response.data;


            // failed registration
            if(returned_value == ""){ // empty 204 no content
                $scope.registrationErrorMessage = "User with such username/e-mail already exists.";
            }
            else{
                $location.path("/registration_successful_individual");
            }
        });
    };

    $scope.sendPassword = function(){

        var username = angular.copy($scope.forgottenUsername);

        if(username === undefined){
            $scope.forgottenPasswordErrorMessage = "Please enter a username.";
            return;
        }

        GuestService.sendPassword(username).then(function(response){
            var returned_value = response.data;
            if(returned_value == null){
                $scope.forgottenPasswordErrorMessage = "No such username has been found.";
                return;
            }
            else{
                $location.path("/sent_password");
            }
        });
    };


    $scope.registerCompany = function(){
        var company = angular.copy($scope.company);



        //validates
        if(company === undefined || company.name === undefined || company.email === undefined || company.membership === undefined || company.pib === undefined
            || company.name == "" || company.membership == "" || company.pib == ""){
            return;
        }

        GuestService.registerCompany(company).then(function(response){
           var returned_value = response.data;


           if(returned_value == undefined || returned_value == ""){
               $scope.registrationCompanyErrorMessage = "Company with such name/PIB already exists.";
           }

           else{
               $location.path("/registration_successful_company");
           }

        });
    };

    // redirects to a template page
    $scope.goto = function (page) {
        if (page == "/forgotPassword") {
            $location.path("/forgotPassword");
        }
    }


    $scope.searchPhotos = function(criteria, term){
      var criteria = $rootScope.searchCriteria;
      var term = $rootScope.searchTerm;

      if(criteria === undefined || term === undefined)
          return;

      GuestService.searchPhotos(criteria, term).then(function(response){
          var resultPhotos = response.data;

          if(resultPhotos === undefined){
              alert("Could not fetch photos for the given search result!");
              return;
          }


          $rootScope.displayedPhotos = resultPhotos;

          if(resultPhotos.length > 10){
              $rootScope.displayedPhotos = resultPhotos.slice(0,10)
          }

          $rootScope.resultPhotos = resultPhotos;
          $location.path("/search_results");
      });
    };

    $scope.loadMore = function(){
        var displayedItems = $rootScope.displayedPhotos.length;
        var totalItems = $rootScope.resultPhotos.length;
        var diff = totalItems - displayedItems;

        var extraItems;

        if(diff > 10){
            extraItems = $rootScope.resultPhotos.slice(displayedItems, displayedItems+10);
        }
        else{
            extraItems = $rootScope.resultPhotos.slice(displayedItems, displayedItems + diff);
        }

        $rootScope.displayedPhotos = $rootScope.displayedPhotos.concat(extraItems);
    };

    $scope.sortPictures = function(){

        if($rootScope.sortBy === undefined || $rootScope.flow === undefined)
            return;

        var criteria = $rootScope.sortBy;
        var flow = $rootScope.flow;

        var displayedPhotos = $rootScope.displayedPhotos;


        if(criteria == 'date'){
            if(flow == 'ascending')
                displayedPhotos.sort(function(a,b){ return a.dateUploaded - b.dateUploaded});
            else
                displayedPhotos.sort(function(a,b){ return b.dateUploaded - a.dateUploaded});
        }

        else if(criteria == 'rating'){
            // $rootScope.sort(function(a,b){ return a.rating - b.rating});
        }
        else if(criteria == 'title'){
            if(flow == 'ascending')
                displayedPhotos.sort(function(a,b){ return a.title > b.title ? 1 : a.title < b.title ? -1 : 0 });
            else
                displayedPhotos.sort(function(a,b){ return b.title > a.title ? 1 : b.title < a.title ? -1 : 0 });
        }

        $rootScope.displayedPhotos = displayedPhotos;
    };

    $scope.moreInfo = function moreInfo(ev,photo){

            $rootScope.selectedPhoto = photo;

            // calling the service to get us photo resolutions and prices
            GuestService.getPhotoResolutions(photo.id).then(function(response){
                var resolutions = response.data;

                if(resolutions === undefined){
                    alert("COULD NOT FETCH PHOTO RESOLUTIONS FOR THE SELECTED PHOTO");
                    return;
                }

                $rootScope.selectedPhoto.resolutions = resolutions;
            });


            $mdDialog.show({
                controller: 'GuestController',
                templateUrl: 'guest/more_info.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
            })

    };

    $scope.cancelDialog = function () {
        $mdDialog.cancel();
    };


    $scope.addToCart = function(){
        var selectedPhoto = $rootScope.selectedPhoto;
        var selectedResolution_str = $rootScope.selectedResolution;
        var selectedResolution;

        if(typeof selectedResolution_str === 'undefined'){
            alert("You didn't select any resolution");
            return;
        }

        selectedResolution = JSON.parse(selectedResolution_str);

        selectedPhoto.selectedResolution = selectedResolution;

        var cart_str = sessionStorage.getItem('cart');
        var cart;

        if(cart_str === undefined || cart_str == null)
            cart = [];
        else
            cart = JSON.parse(cart_str);


        // sorting cart by price
        cart.push(selectedPhoto);

        cart_str = JSON.stringify(cart, null, 2);
        sessionStorage.setItem('cart', cart_str);
    };

    $scope.checkOut = function(){

        if($rootScope.user === undefined){
            alert("You have to log in in order to check out!");
            return;
        }

        var user = $rootScope.user;

        if(user.creditcard === undefined || user.creditcard == null || user.creditcard == ''){
            alert("You have to set up a credit card before checking out!");
            return;
        }
    };
}]);