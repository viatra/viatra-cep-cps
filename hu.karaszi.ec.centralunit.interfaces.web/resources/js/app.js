var myApp = angular.module('spicyApp2', [ 'ngRoute', 'deviceControllers', 'deviceServices' ]);

myApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/welcome', {
		templateUrl : 'partials/welcome.html',
		controller : ''
	}).when('/devices', {
		templateUrl : 'partials/devices.html',
		controller : 'DeviceController'
	}).otherwise({
		redirectTo : '/welcome'
	});
} ]);