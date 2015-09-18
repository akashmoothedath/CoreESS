'use strict';

/* Directives */

app.directive('homeview', [function() {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'templates/HomeView.html'
    };
}]);

app.directive('timesheets', [function() {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'templates/TimesheetTemplate.html'
    };
}]);

/*app.directive('ooomanager', [function() {
	
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'templates/OutOfOfficeManagerTemplate.html'
    };
}]);*/

app.directive('ooomanager', [function() {
	
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'templates/OutOfOfficeManagerTemplate.html'
    };
}]);

app.directive('oooemployee', [function() {
	
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'templates/OutOfOfficeTemplate.html'
    };
}]);

app.directive('ngEnter', function(){
	return function(scope, element, attrs){
		element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
               scope.verify();
                event.preventDefault();
            };
		});
	};
});

app.directive('hcPie', function(){
	return{
		restrict: 'C',
		replace: true,
		scope: {
			items: '='},
		controller: function($scope, $element, $attrs){
			
			console.log("Inside pie-chart directive - controller");
		},
		template: '<div id="container" style="min-width: 200px; height: 200px; margin: 0 auto"">not working</div>',
		link: function(scope, element, attrs){
			
			console.log("Inside the pie-chart directive - link function");
			var chart = new Highcharts.Chart({
				
				chart: {
					renderTo: 'container',
					plotBackgroundColor: null,
					plotBorderWidth: null,
					plotShadow: false
				},
				title:{
					text:'My first pie - chart'
				},
				 plotOptions: {
			          pie: {
			            allowPointSelect: true,
			            cursor: 'pointer',
			            dataLabels: {
			              enabled: true,
			              color: '#000000',
			              connectorColor: '#000000',
			              formatter: function () {
			                return '<b>' + this.point.name + '</b>: ' + this.percentage + ' %';
			              }
			            }
			          }
				 },
				 series: [{
			          type: 'pie',
			          name: 'Days ',
			          data: scope.items
			        }]
			      });
			      
					scope.$watch("items", function (newValue) {
			        chart.series[0].setData(newValue, true);
			      }, true);
			
		}
	}
});