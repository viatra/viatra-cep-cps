var deviceServices = angular.module('deviceServices', [ 'ngResource' ]);

deviceServices.factory('Actuator', [ '$resource', function($resource) {
	return $resource('services/actuator', {}, {
		query : {
			method : 'GET',
			isArray : true
		}
	});
} ]);

deviceServices.factory('Sensor', [ '$resource', function($resource) {
	return $resource('services/sensor', {}, {
		query : {
			method : 'GET',
			isArray : true
		}
	});
} ]);