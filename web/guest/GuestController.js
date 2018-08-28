var app = angular.module("photostock-app");

app.controller("GuestController", ['$scope', '$rootScope', '$location', 'GuestService', 'UserService', function ($scope, $rootScope, $location, GuestService, UserService) {
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
    }
}]);