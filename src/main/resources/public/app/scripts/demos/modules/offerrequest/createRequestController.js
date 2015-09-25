angular
  .module('theme.demos.request', [])
  .controller('CreateRequestController', ['$scope', '$timeout' , '$log', '$http','LookUpService', 'UserAuthentication','$window','$route', function($scope, $timeout, $log, $http,LookUpService,UserAuthentication,$window,$route) {
	    'use strict';
	    
	    $scope.id = $route.current.params.id;
	    console.log("Request COntroller: "+$scope.id);
	    $scope.waiting = true;

	    LookUpService.getStates().then(function(data){
	        $scope.states = data;
	      },function(error){
	        $log.log(error);
	      });
	    LookUpService.getQuestions('R').then(function(data){
	        $scope.questions = data;
	      },function(error){
	      	console.log('In questions http');
	        $log.log(error);
	      });
	    LookUpService.getIntAudience().then(function(data){
	        $scope.intendedAudience = data;
	      },function(error){
	        $log.log(error);
	      });
	    
	    $scope.selectedIndustries = { "selected": [] };
	    $scope.selectedStates = { "selected": [] };
	    $scope.selectedAudience = 0;	    
	    $scope.industries = [];
	    $http.get('utilservices/industries').success(function(response) {
	      $scope.industries = response;
	    });
	    
	    if($scope.id !== undefined){
	    	$http({
		          url : '/services/request/detail/'+ $scope.id,
		          method: 'GET'
		        }).then(function(response){
		        	var data = response.data;
		        	$scope.requestTitle=data.title;
			        $scope.requestDescription=data.description;			        
			        for(var i=0;i<data.trgtIndustry.length;i++){
    	              for(var j=0;j<$scope.industries.length;j++){
    	                if(data.trgtIndustry[i] == $scope.industries[j].industryId){
    	                  $scope.selectedIndustries.selected.push($scope.industries[j]);
    	                  break;
    	                }
    	              }
	    	        }
    	          	for(var i=0;i<data.trgtLocation.length;i++){
	    	            for(var j=0;j<$scope.states.length;j++){
	    	                if(data.trgtLocation[i] == $scope.states[j].stateId){
	    	                  $scope.selectedStates.selected.push($scope.states[j]);
	    	                  break;
	    	                }
	    	            }
    	            }
    	            for(var i=0;i<data.questionList.length;i++){
    	            	//Loop through the Question and set the value
    	            }    	            
	    	        $scope.selectedAudience = data.intdAudience;
	    	        $scope.waiting = false;
	    
		        },function(error){
		        console.log('Error in pulling the offer data');
		    });
	    }
	    else{
	    	$scope.waiting = false;
	    }
	    
	    $scope.reg_form = {};
	    $scope.form = {};

	    $scope.checking = false;
	    $scope.checked = false;
	    $scope.user = {};
	    $scope.selectedAgeId=0;
	    $scope.selectedEducationId=0;
	    $scope.selectedProfessionId = 0;
	    $scope.selectedStateId = 0;
	    
	    /*********** Get all Lookup values *********/

	    
	    $scope.$watch('selectedIndustries',function(newval,oldval){
	    	console.log('selected industriess',newval);    
	    });
	      
	      $scope.save =  function(){
    		var state=[];
    		var tags = [];
    		for(var i=0;i<$scope.selectedStates.selected.length;i++){
    			state.push($scope.selectedStates.selected[i].stateId);
    			tags.push($scope.states[i].stateName);
    		}
    		var industries=[];
    		for(var i=0;i<$scope.selectedIndustries.selected.length;i++){
    			industries.push($scope.selectedIndustries.selected[i].industryId);
    			tags.push($scope.selectedIndustries.selected[i].industryName);
    		}
    		$scope.waiting = true;
    		
    		if($scope.id !== undefined){
    			$http({
    	              url: '/services/request',
    	              method: 'PUT',
    	              isArray: false,
    	              data: { 
    	            	  "id" : $scope.id,
    	            	  "title" : $scope.requestTitle,
    	                  "description" : $scope.requestDescription,
    	                  "trgtIndustry" : industries,
    	                  "intdAudience" : $scope.selectedAudience,
    	                  "trgtLocation" : state,
    	                  "imgUrl" :$scope.imageUrl
    	                  },
    	              cache : false}).then(function(response){
    	            	  //$window.location.href = '/#/myrequests';
    	            	  $scope.waiting = false;
    	            	  var tagEntity = { "entityId" : $scope.id, "entityType" : "REQUEST", "tags" : tags }
	                      $http({
	                        url: 'services/tag',
	                        method: 'POST',
	                        data: tagEntity
	                      }).then(function(response){
	                        $scope.waiting = false;
	                      },function(error){
	                        console.log('TAG Entity error',error);
	                      });
    	              }, function(response){
    	            	  $window.location.href = '/#/myrequests';
    	              });
    		      
    		} else{
    			$http({
    	              url: '/services/request',
    	              method: 'POST',
    	              isArray: false,
    	              data: { "title" : $scope.requestTitle,
    	                  "description" : $scope.requestDescription,
    	                  "trgtIndustry" : industries,
    	                  "intdAudience" : $scope.selectedAudience,
    	                  "trgtLocation" : state,
    	                  "imgUrl" :$scope.imageUrl,
    	                  },
    	              cache : false}).then(function(response){
    	            	  //$window.location.href = '/#/myrequests';
    	            	  $scope.waiting = false;
    	            	  var tagEntity = { "entityId" : response.data, "entityType" : "REQUEST", "tags" : tags }
	                      $http({
	                        url: 'services/tag',
	                        method: 'POST',
	                        data: tagEntity
	                      }).then(function(response){
	                        $scope.waiting = false;
	                      },function(error){
	                        console.log('TAG Entity error',error);
	                      });
    	              }, function(response){
    	            	  $window.location.href = '/#/myrequests';
    	              });
    		      };
    		}
    	  
	      
	  }]);