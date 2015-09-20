'use strict';

angular.module('frontendApp')
  .controller('MainCtrl', function ($scope, $http) {

    var URL;
    $scope.model = {};
    $scope.call = call;
    $scope.model.providers = [];

    init();
    getConfig();

    function init() {
      $scope.model.customerNumber = '';
      $scope.model.isValidNumber  = false;
      $scope.model.selectedProvider = $scope.model.providers[0] || '';
    }

    $scope.$watch('model.customerNumber', function(newVal , oldVal) {
			$scope.model.isValidNumber = newVal.length > 5;
		});

    function getConfig() {
      $http.get('configuration.json').then(function(response) {
        $scope.model.providers = response.data.providers;
        URL = response.data.url;

        // preselect first provider
        if ($scope.model.providers.length > 0) {
          $scope.model.selectedProvider = $scope.model.providers[0];
        }
      });
    }

    function call() {
      var payload = {
        customerNumber: $scope.model.customerNumber,
        provider: $scope.model.selectedProvider.key,
        date: new Date()
      };
      $http({
        withCredentials: true,
        url: URL,
        method: 'GET',
        params: payload
      })
      .finally(function() {
        init();
      });
    }
  });
