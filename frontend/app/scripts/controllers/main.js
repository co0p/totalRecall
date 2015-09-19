'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp')
  .controller('MainCtrl', function ($scope, $http) {

    var URL = 'http://localhost:9000/api';
    $scope.model = {};
    $scope.call = call;
    $scope.model.providers = [];

    init();
    getProviders();

    function init() {
      $scope.model.customerNumber = '';
      $scope.model.selectedProvider = $scope.model.providers[0] || '';
    }

    function getProviders() {
      $http.get('providers.json').then(function(response) {
        $scope.model.providers = response.data;

        if (response.data.length > 0) {
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
      $http.post(URL, payload)
      .then(function (data) {
        init();
      }, function (err) {
        init();
      });
    }
  });
