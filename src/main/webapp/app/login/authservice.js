angular.module('AuthService', [])
        .factory('login', ['$window', '$http', '$rootScope', '$location', 'feedback', function ($window, $http, $rootScope, $location, feedback) {
                return function (login, password) {
                    //login and/or password cannot be empty
                    if (null == login || '' === login || null == password || '' === password ){
                        feedback.clearAll();
                        feedback.warning("Please provide login and password.", "Warning");
                    }
                    else
                    {
                        $http({
                            method: 'POST',
                            url: 'rs/login',
                            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                            transformRequest: function (obj) {
                                var str = [];
                                for (var p in obj)
                                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                                return str.join("&");
                            },
                            data: {username: login, password: password}
                        }).success(function () {
                            $rootScope.loadApplicationContextAndRedirectToFirstMenuOption();
                        }).error(function () {
                            feedback.danger("Authentication failed.", "Error");
                        });
                    }
                };
            }]);