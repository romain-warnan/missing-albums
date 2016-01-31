angular.module('capitales', [])

.controller(
	'GameController', [
	'$scope',
	'$http',
	function ($scope, $http) {
	
	var controller = this;
	
	controller.step1 = true;
	controller.step2 = false;
	controller.step3 = false;
	
	controller.startGame = function() {
		$http.put('/capitales/service/game/' + controller.size + '/' + controller.username).success(
			function(challenge, status, headers, config) {
				controller.step1 = false;
				controller.step2 = true;
				controller.step3 = false;
				controller.nextQuestion();
		});
	};
	
	controller.giveAnswer = function() {
		$http.get('/capitales/service/answer/' + controller.answer).success(
			function(proposition, status, headers, config) {
				controller.proposition = proposition;
				controller.nextQuestion();
				controller.answer = '';
		});
	};
	
	controller.end = function() {
		$http.get('/capitales/service/end').success(
			function(paper, status, headers, config) {
				controller.paper = paper;
		});
	};
	
	controller.nextQuestion = function() {
		$http.get('/capitales/service/next').success(
			function(question, status, headers, config) {
				if(typeof question.value === 'undefined'){
					controller.step1 = false;
					controller.step2 = false;
					controller.step3 = true;
					controller.end();
				}
				else {
					controller.question = question.value;
				}
		});
	};
}])
.directive('gmEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.gmEnter);
                });
                event.preventDefault();
            }
        });
    };
});