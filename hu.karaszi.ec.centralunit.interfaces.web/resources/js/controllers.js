var deviceControllers = angular.module('deviceControllers',
		[ 'deviceServices' ]);

deviceControllers.controller('DeviceController', [ '$scope', 'Actuator', 'Sensor',
		function($scope, Sensor, Actuator) {
			$scope.sensors = Sensor.query();
			$scope.actuators = Actuator.query();
		} ]);