var app = angular.module('app').controller("CitiesController", function ($scope, $http, $modal, feedback) {
    var vm = this;

    $scope.title = "Miasta";

    feedback.clearAll();

    $scope.editForm = [];

    $scope.editFormSchema = {
        type: 'object',
        properties: {}
    }
    
    $scope.emptyEntity = {};
    
    vm.martialGrid = {
        enableHorizontalScrollbar : 0,
        enableVerticalScrollbar : 0,
        enableRowSelection : true,
        enableRowHeaderSelection : false,
        multiSelect : false,
        enableSorting : true,
        enableFiltering : false,
        enableGridMenu : true,
        rowTemplate : "<div ng-dblclick=\"grid.appScope.vm.editRow(grid, row)\" ng-repeat=\"(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name\" class=\"ui-grid-cell\" ng-class=\"{ 'ui-grid-row-header-cell': col.isRowHeader }\" ui-grid-cell></div>"
    };

    loadCities = function () {
        $http.get('rs/city').success(function (data) {
            vm.martialGrid.columnDefs = [];
            for(var i = 0; i < data.fields.length; i++) {

                $scope.editFormSchema.properties[data.fields[i].name] = { type: 'string', title: data.fields[i].name };
                $scope.editForm.push(data.fields[i].name);

                $scope.emptyEntity[data.fields[i].name] = "";

                var gridColumn = {
                    field : data.fields[i].name,
                    displayName : data.fields[i].name,
                    enableSorting : true,
                    //type : 'number',
                    enableCellEdit : false,
                    width : "*",
                }
                if ( data.fields[i].name == "id" ) {
                    gridColumn.cellTemplate = 'row-buttons.html';
                    gridColumn.displayName = '';
                }
                vm.martialGrid.columnDefs.push(gridColumn);
            }
            
            vm.martialGrid.data = data.records;
        });
    };

    $scope.loadGridData = loadCities;

    // remove row
    remove = function (row) {
        if (row.entity.id != '0') {
            var index = vm.martialGrid.data.indexOf(row.entity);
            vm.martialGrid.data.splice(index, 1);
            /*
             * $http.delete('http://localhost:8080/service/delete/'+row.entity.id).success(function(response) { $modalInstance.close(row.entity); }).error(function(response) { alert('Cannot delete row (error in console)'); console.dir(response); });
            */

//       var req = {
//           'method': 'POST',
//           'url': 'rs/data_rm',
//           'headers': {
//               'Content-Type': 'application/json'
//           },
//           'data': angular.toJson($scope.record, true)
//       };
//        $http(req).success(function (data, status, headers, config) {
//            if (0 === data.status) {
//               $scope.records.splice(index, 1);
//            } else {
//                feedback.danger("Failed to remove record: " + data.message, "Error");
//            }
//        }).error(function (data, status, headers, config) {
            //feedback.danger("Failed to remove record", "Error");;
        //});


        }
    };

    $scope.addRow = function (grid) {
        var newEntity = angular.copy($scope.emptyEntity);
        var tmpRow = {};
        tmpRow.entity = newEntity;
        tmpRow.entity.id = 0;
        
        $scope.editRow(grid,tmpRow);
    };

    $scope.removeRow = function (grid, row) {

        var modalInstance = $modal.open({
            templateUrl: 'remove-modal.html',
            controller: 'removeRowCtrl',
            resolve: {
                grid: function () { return grid; },
                row: function () { return row; },
            }
        });

        modalInstance.result.then(function (row) {
            remove(row);
        }, function () {
        });
    };
    
    $scope.editRow = function (grid,row) {
        var modalInstance = $modal.open({
            templateUrl: 'edit-modal.html',
            controller: 'editRowCtrl',
            controlerAs: 'vm',
            scope: $scope,
            resolve: {
                grid: function() { return grid; },
                row: function() { return row; },
                scope: function () { return $scope; }
            }
        });

        modalInstance.result.then(function (entity) {
            if (entity.id != '0') {
                row.entity = angular.copy(entity);
            } else {
                entity.id = 100; // get it from database
                vm.martialGrid.data.push(entity);
             }
        }, function () {
        });
    };

    vm.editRow = $scope.editRow;
    vm.removeRow = $scope.removeRow;

});
