angular.module('app', ['ui.bootstrap', 'ngRoute', 'AuthService', 'xeditable', 'ui.grid', 'ui.grid.moveColumns', 'ui.grid.selection', 'ui.grid.resizeColumns', 'ui.grid.edit', 'schemaForm'])
        .config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {
                $httpProvider.responseInterceptors.push('httpInterceptor');
            
                $routeProvider.when('/login', { templateUrl: 'app/login/login.html'})
                              .when('/logout', { templateUrl: 'app/logout/logout.html'})
                              .when('/fighters', {templateUrl: 'app/fighters/fighters.html'})
                              .when('/cities', { templateUrl: 'app/cities/cities.html'})
                              .when('/events', { templateUrl: 'app/events/events.html'})
                              .when('/groups', { templateUrl: 'app/groups/groups.html'})
                              .when('/users', { templateUrl: 'app/users/users.html' })
                              .when('/presence', { templateUrl: 'app/presence/presence.html' })
                              .otherwise({ redirectTo: '/' });
            }])
        .run(['$rootScope', '$http', '$location', 'feedback', '$window', function ($rootScope, $http, $location, feedback, $window) {
                $http.get("rs/appinfo").success(function (data) {
                    $rootScope.appinfo = data;
                    top.document.title = $rootScope.appinfo.fullName;
                });

                $rootScope.content = {};
                $rootScope.content.menu = 'app/menu/menu.html';
                
                $rootScope.isUsernameDefined = function() {
                    var isUsernameDefined = true;
                    if (undefined == $rootScope.username || null == $rootScope.username) {
                        isUsernameDefined = false;
                    }
                    return isUsernameDefined;
                };
                
                // Load application context if user is logged in
                // If not perform Single Sign On procedure
                $http.get("rs/user").success(function () {
                    $rootScope.loadApplicationContext();
                }).error(function (data, status, headers, config) {
                    if ($location.path() != "/login" && $location.path() != "/logout") {
                        $location.path('/login');
                    }
                });
                
                $rootScope.loadApplicationContext = function() {
                    // load logged in user info
                    $http.get("rs/user").success(function (data) {
                        $rootScope.username = data.username;
                    });

                    //load menu
                    $http.get("rs/menu").success(function (data) {
                        $rootScope.$links = data;

                    })
                };
                
                $rootScope.loadApplicationContextAndRedirectToFirstMenuOption = function() {
                    // load logged in user info
                    $http.get("rs/user").success(function (data) {
                        $rootScope.username = data.username;
                    });

                    //load menu and redirect user to first item
                    $http.get("rs/menu").success(function (data) {
                        $rootScope.$links = data;

                        // If menu contains at least one element redirect to first menu item
                        if (data.links.length > 0) {
                            var firstMenuItem;
                            
                            // Checking, if menu has sub-menu
                            if (data.links[0].sub != null) {
                                firstMenuItem = data.links[0].sub[0].url;
                            } else {
                                firstMenuItem = data.links[0].url;
                            }
                            
                            $location.path('/' + firstMenuItem);
                        }
                    });
                };
                
                //feedback clean
                $rootScope.$on('$routeChangeSuccess', function () {
                    feedback.clearOlder();
                });
                    
            }]);


angular.module('app').factory('httpInterceptor', function httpInterceptor ($q, $window, $location, $log, $rootScope, feedback) {
      return function (promise) {
          
            var clearContextAndRedirectToLoginPage = function() {
                $rootScope.$links = undefined;
                $rootScope.username = undefined;
                $location.path('/login');
                feedback.clearAll(); 
            };
          
            var success = function (response) {
                return response;
            };

            var error = function (response) {
                if (response.status === 403 && response.config.url != "rs/user") {     
                    clearContextAndRedirectToLoginPage();
                    feedback.warning("Your session expired. Please login again.", "Warning");
                }
                
                return $q.reject(response);
            };

          return promise.then(success, error);
      };
});

