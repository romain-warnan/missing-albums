angular.module('capitales', []).controller(
	'GameController', [
	'$scope',
	'$http',
	function ($scope, $http) {
	
	var controller = this;
	
	controller.step1 = true;
	controller.step2 = false;
	
	controller.startGame = function() {
		$http.get('/capitales/service/game/' + controller.size + '/' + controller.username)
			.success(function(game, status, headers, config) {
				controller.quizz = game.quizz;
				controller.step1 = false;
				controller.step2 = true;
				return game;
			})
			.error(function(game, status, headers, config) {
				return "Erreur";
			});
	};
}]);