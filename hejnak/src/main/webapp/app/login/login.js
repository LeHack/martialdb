angular.module('app').controller(
		'LoginController', ['$scope', '$window', 'login', function($scope, $window, login) {

					$scope.loginAction = function() {
						login($scope.login, $scope.password);
					};
				}]);