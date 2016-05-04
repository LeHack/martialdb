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
        $scope.record = {"record": $scope.records[index]};

        var req = {
            'method': 'POST',
            'url': 'rs/data_rm',
            'headers': {
                'Content-Type': 'application/json'
            },
            'data': angular.toJson($scope.record, true)
        };

//        $http(req).success(function (data, status, headers, config) {
//            if (0 === data.status) {
                $scope.records.splice(index, 1);
//            } else {
//                feedback.danger("Failed to remove record: " + data.message, "Error");
//            }
//        }).error(function (data, status, headers, config) {
            //feedback.danger("Failed to remove record", "Error");;
        //});
    };

    $scope.Add = function () {
        //$scope.inserted = {
            //define fields here
        //};
        
        //add inserted on save
        //$scope.records.push($scope.inserted);
    };

    $scope.Remove = function (index) {

        var modalInstance = $modal.open({
            templateUrl: 'Remove.html',
            controller: 'RemoveCtrl',
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

var app = angular.module('app').controller('RemoveCtrl', function ($scope, $modalInstance, items, scope) {
    $scope.ok = function () {
        $modalInstance.close(items);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss();
    };
});
