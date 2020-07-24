var app = angular.module('myApp', []);
app.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.defaults.timeout = 20000;
}])
app.controller('myCtrl', function ($scope, $http) {
    this.retrieve = function () {
        $http.get('http://127.0.0.1:8302/api/stock-service/rest/stock/' + $scope.name)
            .then(function (response) {

                $scope.quotes = response.data;
                console.log('inside' + response.data);
            }, function (response) {
                console.log('came here');
            });
    }


    this.add = function () {
        var message = {
            userName: $scope.name,
            quotes: [$scope.quote]
        }
        $http.post('http://127.0.0.1:8302/api/db-service/rest/db/add', message)
            .then(function (response) {
                $scope.quotes1 = response.data;
                console.log('inside' + response.data);
            }, function (response) {
                console.log('error..');
            });
    }

    this.delete = function () {
        var message = {
            userName: $scope.name,
        }
        $http.post('http://127.0.0.1:8302/api/db-service/rest/db/delete/' + $scope.name)
            .then(function (response) {
                $scope.isdeleted = "User deleted successfully";
                console.log('inside' + response.data);
            }, function (response) {
                console.log('came here');
            });
    }
});