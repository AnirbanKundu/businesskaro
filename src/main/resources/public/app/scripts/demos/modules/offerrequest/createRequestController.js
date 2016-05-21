angular
  .module('theme.demos.request', [])
  .controller('CreateRequestController', ['$scope', '$timeout' , '$log', '$http','LookUpService', 'UserAuthentication','$window','$route', function($scope, $timeout, $log, $http,LookUpService,UserAuthentication,$window,$route) {
	    'use strict';
	    
	    $scope.id = $route.current.params.id;
	    console.log("Request COntroller: "+$scope.id);
	    $scope.waiting = true;
	    $scope.isSaveClicked = false;
	    $scope.createDate = new Date();

	    /********** VALIDATION ************/
	    $scope.formControl = {
	      isTitleValid:true,
	      isIndustryValid:true,
	      isIntendedAudienceValid:true,
	      isTargetLocationValid:true
	    };
	    $scope.$watch('formControl',function(newval,oldval){
      if(newval && newval!=oldval){
        //validate(newval,{'title':$scope.offerTitle, 'industry':$scope.selectedIndustries.selected, 'audience':$scope.selectedAudience.selected,'state':$scope.selectedStates.selected})
      }
    },true);

    $scope.checkValidation = function(prop,value){
      if($scope.formControl.hasOwnProperty(prop)){
        if(value){
          $scope.formControl[prop] =  true;
        }
        else{
          $scope.formControl[prop] =  false;  
        }        
      }
    }

    $scope.$watch('requestTitle',function(newval,oldval){
        $scope.checkValidation('isTitleValid', newval);    
    }); 

    $scope.$watch('selectedIndustries',function(newval,oldval){      
      if(newval!=null && newval.selected){
        $scope.checkValidation('isIndustryValid', newval.selected[0]);    
      }      
    },true);
    $scope.$watch('selectedAudience',function(newval,oldval){      
      if(newval!=null && newval){
        $scope.checkValidation('isIntendedAudienceValid', newval);    
      }      
    },true);
    $scope.$watch('selectedStates',function(newval,oldval){      
      if(newval!=null && newval.selected){
        $scope.checkValidation('isTargetLocationValid', newval.selected[0]);    
      }      
    },true);

    $scope.provideColor = function(value){
      if($scope.isSaveClicked){
        if(value){
            return{
                'border': '1px solid green'
            } 
        }else{
            return{
                'border': '1px solid red'
            }
        }
      }        
    }

	    /*********** END VALIDATION ***********/

	    LookUpService.getStates().then(function(data){
	        $scope.states = data;
	      },function(error){
	        $log.log(error);
	      });
	    LookUpService.getQuestions('R').then(function(data){
	        $scope.questions = data;
	        for(var i=0;i<$scope.questions.length;i++){
	        	$scope.questions[i].value = "N";
	        }
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
		          url : '/services/request/detailinedit/'+ $scope.id,
		          method: 'GET'
		        }).then(function(response){
		        	var data = response.data;
		        	$scope.userId = data.userId;
		        	$scope.requestTitle=data.title;
			        $scope.requestDescription=data.description;	
			        $scope.createDate = data.createDate;		        
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
    	            for(var i=0;i<$scope.questions.length;i++){
    	            	for(var j=0;j<data.questionList.length;j++){
    	            		if(data.questionList[j].questionId==$scope.questions[i].questId){
    	            			$scope.questions[i].value = data.questionList[j].response;
    	            		}
    	            	}
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

    	$scope.delete = function(){
    		$http({
    			url: '/services/request/'+$scope.id,
	    	    method: 'DELETE'
	    		}).then(function(){
	    		$http({
		            url: '/services/tag/?entityId='+$scope.id + '&entityType='+ 'REQUEST',
		            method: 'DELETE'
		          }).then(function(){            
		            $window.location.href = '/#/requests';
		          },function(error){
		            console.log('Cannot delete request',error);
		        });    			
    		},function(error){
    			console.log('Cannot delete request',error);
    		});
    	}
	      
	      $scope.save =  function(){
    		var state=[];
    		var tags = [];
    		$scope.isSaveClicked = true;
    		var isFormValid = true;
	        for (var property in $scope.formControl) {
	            if ($scope.formControl.hasOwnProperty(property)) {
	                if($scope.formControl[property] == false){
	                  isFormValid = false;
	                }                
	            }
	        }
	        if(!isFormValid){
	          return;
	        }
	        else{
	        	for(var i=0;i<$scope.selectedStates.selected.length;i++){
	    			state.push($scope.selectedStates.selected[i].stateId);
	    			tags.push($scope.states[i].stateName);
	    		}
	    		var industries=[];
	    		for(var i=0;i<$scope.selectedIndustries.selected.length;i++){
	    			industries.push($scope.selectedIndustries.selected[i].industryId);
	    			tags.push($scope.selectedIndustries.selected[i].industryName);
	    		}
	    		var questions = [];
	    		for(var i=0;i<$scope.questions.length;i++){
	    			var q = {
	    						questionId:$scope.questions[i].questId,
	    						response: $scope.questions[i].value
	    					}
					questions.push(q);
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
	    	                  "imgUrl" :$scope.imageUrl,
	    	                  "questionList":questions,
	    	                  "userId" : $scope.userId,
	    	                  "createDate":$scope.createDate
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
		                        $scope.message = 'success';
                        		$scope.waiting = false;
                         		$scope.alert = { type: 'success', msg: '<strong>Request</strong> updated successfully.'};
		                      },function(error){
		                      	$scope.message = 'error';
		                        $scope.alert = { type: 'danger', msg: '<strong>Request</strong> was not updated. Try again.'};
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
	    	                  "questionList":questions,
	    	                  "createDate":$scope.createDate
	    	                  },
	    	              cache : false}).then(function(response){
	    	            	  //$window.location.href = '/#/myrequests';
	    	            	  $scope.waiting = false;
	    	            	  $scope.id = response.data;
	    	            	  var tagEntity = { "entityId" : response.data, "entityType" : "REQUEST", "tags" : tags }
		                      $http({
		                        url: 'services/tag',
		                        method: 'POST',
		                        data: tagEntity
		                      }).then(function(response){
								$scope.message = 'success';
                          		$scope.waiting = false;
                           		$scope.alert = { type: 'success', msg: '<strong>Request</strong> saved successfully.'};
		                      },function(error){
		                      	$scope.message = 'error';
		                        $scope.alert = { type: 'danger', msg: '<strong>Request</strong> was not saved. Try again.'};
		                      });
	    	              }, function(response){
	    	            	  $window.location.href = '/#/myrequests';
	    	              });
	    		      };
		        }    		
    	}
    	  
	      
	  }]);