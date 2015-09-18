var app = angular.module('MainModule',["xeditable", "ui.bootstrap","ngRoute"]).
	config(function($routeProvider,$locationProvider){
		$routeProvider.
			when("/",{templateUrl: "templates/LoginPage.html",controller: loginController}).
			when("/home",{templateUrl:"templates/HomePage.html"});
	});