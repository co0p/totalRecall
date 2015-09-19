'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp')
  .controller('MainCtrl', function ($scope) {

    $scope.model = {};
    $scope.model.customerNumber = '';
    $scope.model.providers = [{label: 'DHL Kundenservice', number: 02284333112}];
  });
