var app = angular.module('app').controller("DataController", function ($scope, $filter, $http, $interval, $log, $modal, feedback) {

    $scope.title = "Example data";
    feedback.clearAll();

    $scope.fields = [];
    $scope.records = [];

    $scope.loadData = function () {
        $http.get('rs/data').success(function (data) {
            $scope.fields = data.fields;
            $scope.records = data.records;
        });
    };

    $scope.clearFeedbacks = function () {
        feedback.clearAll();
    };

    // remove row
    $scope.removeRow = function (index) {
        $scope.captures.splice(index, 1);
// update database
    };

    $scope.confirmRemoval = function (index) {

        var modalInstance = $modal.open({
            templateUrl: 'confirmRemoval.html',
            controller: 'confirmRemovalCtrl',
            resolve: {
                items: function () {
                    return index;
                },
                scope: function () {
                    return $scope;
                }
            }
        });

        modalInstance.result.then(function (index) {
            $scope.removeRow(index);
        }, function () {
        });
    };
});

var app = angular.module('app').controller('confirmRemovalCtrl', function ($scope, $modalInstance, items, scope) {
    $scope.ok = function () {
        $modalInstance.close(items);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss();
    };
});
