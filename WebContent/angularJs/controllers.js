'use strict';

/* Controllers */

function loginController($scope,$http,$location) {
	$scope.verify = function() {
	$http({
		   method : 'POST',
		   url: '/CoreESS/data/getdata/userdetails',
		   data: '{"username":"'+ $scope.user.username +'","password":"'+$scope.user.password+'"}',
		   headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		  }).success(function (data, status, headers, config) {
              sessionStorage.userdata = JSON.stringify(data); 
              sessionStorage.username = $scope.user.username;
              if(data != null || data != undefined)
            	  $location.path("/home");
              
          }).error(function (data, status, headers, config) {
              $scope.status = status;
              console.log("failed");
          });

	};
}

function baseController($scope,$http,$location, limitToFilter) {

	console.log("In base controller");
	$scope.user = JSON.parse(sessionStorage.userdata);
	$scope.username = sessionStorage.username;
	
	var daysOffTaken = parseInt($scope.user.daysOffTaken);
	var rollOver = parseInt($scope.user.rollOver);
	var daysOffRemaining = parseInt($scope.user.daysOffRemaining);
	
	$scope.ideas = [
	                ['Days off taken', daysOffTaken],
	                ['Roll over', rollOver],
	                ['Days Off Remaining', daysOffRemaining]
	                ];
	
	$scope.limitedIdeas = limitToFilter($scope.ideas, 3);

	$scope.template = "Home";
	$scope.home = function() {
		$scope.template = 'Home';
	};
	
	$scope.logout = function(){
		$scope.username = null;
		$location.path("/");
		
	};
	
	$scope.timesheet = function() {
		
		$http({
			   method : 'POST',
			   url: '/CoreESS/data/getdata/timesheetdetails',
			   data: '{"username":"'+ sessionStorage.username +'"}',
			   headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			  }).success(function (data, status, headers, config) {
	              $scope.timesheetdata = data; 
	              sessionStorage.beforeDataLength = data.length;
	              if(data != null || data != undefined)
	            	  $location.path("/home");
	              
	          }).error(function (data, status, headers, config) {
	              $scope.status = status;
	              console.log("failed");
	          });

		$scope.template = "Timesheets";
		
		$scope.removeRow = function(index) {
			
			$scope.date = $scope.timesheetdata[index].Date;
			console.log($scope.date);
		    $scope.timesheetdata.splice(index, 1);
		    
		    $http({
				   method : 'POST',
				   url: '/CoreESS/data/getdata/deletetimesheetdetails',
				   data: '{"date":"'+ $scope.date +'"}',
				   headers: {'Content-Type': 'application/x-www-form-urlencoded'}
				  }).success(function (data, status, headers, config) {
					  console.log("Successfully inserted");
		              
		          }).error(function (data, status, headers, config) {
		              $scope.status = status;
		              console.log("failed");
		          });
		    
		    
		    
		};
		
		$scope.saveRow=function(newdata, id){
			
			//Validate cell data
						
			$scope.Start = newdata.Start;
			$scope.Break1Start = newdata.Break1_Start;
			$scope.Break1End = newdata.Break1_End;
			$scope.Break2Start = newdata.Break2_Start;
			$scope.Break2End = newdata.Break2_End;
			$scope.EOD = newdata.EOD;
			
			if($scope.Start != null)
			{
				if (($scope.Break1Start < $scope.Start) && ($scope.Break1Start != ""))
				{
					return ("Please enter correct Break 1 Start time");
				}
			
				if (($scope.Break1End < $scope.Break1Start) && ($scope.Break1End != ""))
				{
					return ("Please enter correct Break 1 End time");
				}
			
				if (($scope.Break2Start < $scope.Break1End) && ($scope.Break2Start != ""))
				{
					return ("Please enter correct Break 2 Start time");
				}
			
				if (($scope.Break2End < $scope.Break2Start) && ($scope.Break2End != ""))
				{
					return ("Please enter correct Break 2 End time");
				}
			
				if (($scope.EOD < $scope.Break2End) && ($scope.EOD != ""))
				{
					return ("Please enter correct End of Day");
				}
			}
			else
				return ("Please enter valid time");
			
			angular.extend(newdata, {id: id} );
			newdata['username'] = sessionStorage.username;
			newdata = JSON.stringify(newdata);
			if(sessionStorage.beforeDataLength < $scope.timesheetdata.length){	
			$http({
				   method : 'POST',
				   url: '/CoreESS/data/getdata/inserttimesheetdetails',
				   data: newdata,
				   headers: {'Content-Type': 'application/x-www-form-urlencoded'}
				  }).success(function (data, status, headers, config) {
					  console.log("Successfully inserted");
		              
		          }).error(function (data, status, headers, config) {
		              $scope.status = status;
		              console.log("failed");
		          });
			
			}
			
			else if(sessionStorage.beforeDataLength = $scope.timesheetdata.length){
				
				$http({
					   method : 'POST',
					   url: '/CoreESS/data/getdata/updatetimesheetdetails',
					   data: newdata,
					   headers: {'Content-Type': 'application/x-www-form-urlencoded'}
					  }).success(function (data, status, headers, config) {
						  console.log("In success function of updatetimesheetdetails");
			              
			          }).error(function (data, status, headers, config) {
			              $scope.status = status;
			              console.log("failed");
			          });
				
			}
			
		};
		
		$scope.addRow = function(){
			
			$scope.inserted = {
					id: $scope.timesheetdata.length+1,
					Date: new Date(),
					Start: new Date(),
					Break1_Start: new Date(),
					Break1_End: new Date(),
					Break2_Start: new Date(),
					Break2_End: new Date(),
					EOD: new Date()
			};
			console.debug($scope.inserted.id);			
			$scope.timesheetdata.push($scope.inserted);
		};
	};
	
	$scope.outofoffice = function() {

		console.debug("inside manager");
		if(!($scope.username == 'manager')){
		$http({
			   method : 'POST',
			   url: '/CoreESS/data/getdata/ooodetails',
			   data: '{"username":"'+ sessionStorage.username +'"}',
			   headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			  }).success(function (data, status, headers, config) {
	              $scope.ooodata = data;

			  }).error(function (data, status, headers, config) {
	              $scope.status = status;
	              console.log("failed" +  $scope.status);
	          });
	
		$scope.template = "OOOEmployeeTemplate";
		}
		else{
			console.debug("inside manager template");
			$http({
				   method : 'POST',
				   url: '/CoreESS/data/getdata/ooodetails',
				   data: '{"username":"'+ sessionStorage.username +'"}',
				   headers: {'Content-Type': 'application/x-www-form-urlencoded'}
				  }).success(function (data, status, headers, config) {
		              $scope.ooodata = data;

				  }).error(function (data, status, headers, config) {
		              $scope.status = status;
		              console.log("failed" +  $scope.status);
		          });
		
			$scope.template = "OOOManagerTemplate";
			
		}
			
		
		
	};
}
