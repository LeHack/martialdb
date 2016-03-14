angular.module("app").controller("LogoutController", function($rootScope, $http, $location, feedback) {
    feedback.clearAll();
    
    // clear username and links 
    $rootScope.username = undefined;
    $rootScope.$links = undefined;
    
    $http.get("rs/logout").finally(function(data) {
        $location.path('/login');        
    });
});
