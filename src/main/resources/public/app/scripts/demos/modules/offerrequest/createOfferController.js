angular
  .module('theme.demos.offer', [])
  .controller('CreateOfferController', ['$scope', '$timeout' , '$log', '$http', 'LookUpService', 'UserAuthentication', '$window', '$route', function($scope, $timeout, $log, $http,LookUpService,UserAuthentication,$window,$route) {
    'use strict';
    
    $scope.id= $route.current.params.id;
    console.log("Offer COntroller: "+$scope.id);
    $scope.waiting = true;
    $scope.isSaveClicked = false;
        $scope.checking = false;
    $scope.checked = false;
    $scope.user = {};
    $scope.selectedAgeId=0;
    $scope.selectedEducationId=0;
    $scope.selectedProfessionId = 0;
    $scope.selectedStateId = 0;
    
    /*********** Get all Lookup values *********/
    LookUpService.getStates().then(function(data){
        $scope.states = data;
      },function(error){
        $log.log(error);
      });
      LookUpService.getQuestions('O').then(function(data){
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
    $scope.lookingfor = { "selected": [] };
    $scope.selectedAudience = { "selected": [] };
    $scope.industries = [];
    
    $http.get('utilservices/industries').success(function(response) {
      $scope.industries = response;
    });
    
    if($scope.id !== undefined){    	
    	$timeout(function(){
    		$http({
    	          url : '/services/offer/detailinedit/'+ $scope.id,
    	          method: 'GET'
    	        }).then(function(response){
    	        	var data = response.data;
    	          $scope.offerTitle=data.title;
                $scope.userId = data.userId;
    	          $scope.offerDescription=data.description;
    	          if(data.imgUrl){
    		          var imagePath = data.imgUrl;
    		          var widgetFileInput = $('.fileinput').fileinput();
    		          widgetFileInput.addClass('fileinput-exists').removeClass('fileinput-new');
    		          if(imagePath){ 
    		            widgetFileInput.find('.thumbnail').append('<img src="' +imagePath+ '">');
    		            $scope.userImageId = imagePath.substring(imagePath.lastIndexOf('/')+1, imagePath.length).split('.')[0];    
    		            $scope.actualImageName = imagePath.substring(imagePath.lastIndexOf('/')+1, imagePath.length);
    		            console.log('actualImageName is :',$scope.actualImageName);
    		          }          
    		        }
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
    	          for(var i=0;i<$scope.intendedAudience.length;i++){
    	        	  if($scope.intendedAudience[i].targAudId === data.intdAudience){
    	        		  $scope.selectedAudience.selected.push($scope.intendedAudience[i]);
    	        	  }
    	          }
                $scope.waiting = false;
    	          
    	          
    	        },function(error){
    	          console.log('Error in pulling the offer data');
                $scope.waiting = false;
    	        })
    	},1000);    	
    }
    else{
      $scope.waiting = false;
    }
    
    //$scope.reg_form = {};
    //$scope.form = {};
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

    $scope.$watch('offerTitle',function(newval,oldval){
        $scope.checkValidation('isTitleValid', newval);    
    }); 

    $scope.$watch('selectedIndustries',function(newval,oldval){      
      if(newval!=null && newval.selected){
        $scope.checkValidation('isIndustryValid', newval.selected[0]);    
      }      
    },true);
    $scope.$watch('selectedAudience',function(newval,oldval){      
      if(newval!=null && newval.selected){
        $scope.checkValidation('isIntendedAudienceValid', newval.selected[0]);    
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


    
    $('.fileinput').bind('change.bs.fileinput', function(file){ 
        if(file && file.target.value!==undefined){
          var fileIndex = file.target.value.lastIndexOf('\\'),
          fileName = file.target.value.substring(file.target.value.lastIndexOf('\\')+1, file.target.value.length);
          if(fileIndex > -1){
            if(fileName!=$scope.actualImageName){
              if($scope.actualImageName!==""){
                $scope.actualImageName = fileName;
                //Now Delete & Create
                $scope.deleteFile();
                $timeout(function(){
                  $scope.createFile();
                },100);
              }
              else{
                $scope.actualImageName = fileName;
                $scope.createFile();
              }            
            }         
          }
          else{
              $scope.actualImageName = fileName;
          }

        }
        //$scope.actualImageName = file.target.value
        console.log('change.bs.fileinput was called');
        //$scope.createFile();
      });

      $('.fileinput').bind('clear.bs.fileinput', function(event,file){ 
        console.log('clear.bs.fileinput was called');
        $scope.deleteFile();
      });
      $('.fileinput').bind('reset.bs.fileinput', function(event,file){ 
        console.log('reset.bs.fileinput was called'); 
        //$scope.createFile(); 
      });
      $scope.deleteFile = function(){
        $http({
          url : 'services/delete/'+ $scope.userImageId +'/image',
          method: 'GET'
        }).then(function(data){
          $scope.ImageId = "";
          $scope.ImageUrl = "";
          $scope.imageUrl = "";
        },function(error){
          console.log('Error in delete');
        })
      };
      $scope.createFile = function() {
        var documentInput = angular.element("#documentInput");
        var file = documentInput[0].files[0];
          var formData = new FormData();
          formData.append('file', file);
          $http({
            url: '/services/upload/',
            method: 'POST',
            headers: { 'Content-Type' : undefined},
            transformRequest: function(data) { return data; },
            data: formData,
            cache : false
          }).then(function(response){
              $scope.imageUrl = response.data.url;
              $scope.ImageId = response.data.publicId;
              $scope.ImageUrl = response.data.url;
              return response.data;
            }, function(response){
                alert("Error loading file... Please try again.");
              return response.data;
            });
      };

      $scope.delete = function(){
        $http({
          url: '/services/offer/'+$scope.id,
            method: 'DELETE'
        }).then(function(){
          $window.location.href = '/#/myoffers'; 
        },function(error){
          console.log('Cannot delete request',error);
        });
      };
      
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
          $scope.waiting = true;
        for(var i=0;i<$scope.selectedStates.selected.length;i++){
          state.push($scope.selectedStates.selected[i].stateId);
          tags.push($scope.states[i].stateName);
        }
        var industries=[];
        for(var i=0;i<$scope.selectedIndustries.selected.length;i++){
          industries.push($scope.selectedIndustries.selected[i].industryId);
          tags.push($scope.selectedIndustries.selected[i].industryName);
        }
        var intAudience = [];
        for(var i=0;i<$scope.selectedAudience.selected.length;i++){
          intAudience.push($scope.selectedAudience.selected[i].targAudId);
        }
        var questions = [];
        for(var i=0;i<$scope.questions.length;i++){
          var q = {
                questionId:$scope.questions[i].questId,
                response: $scope.questions[i].value
              }
          questions.push(q);
        }
        if($scope.id !== undefined){
          $http({
                    url: '/services/offer',
                    method: 'PUT',
                    isArray: false,
                    data: { 
                      "id" : $scope.id,
                      "title" : $scope.offerTitle,
                        "description" : $scope.offerDescription,
                        "trgtIndustry" : industries,
                        "intdAudience" : intAudience[0],
                        "trgtLocation" : state,
                        "imgUrl" :$scope.imageUrl,
                        "questionList":questions,
                        "userId" : $scope.userId
                        },
                    cache : false}).then(function(response){
                      //$window.location.href = '/#/myoffers';
                      $scope.waiting = false;
                      var tagEntity = { "entityId" : $scope.id, "entityType" : "OFFER", "tags" : tags }
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
                      //$window.location.href = '/#/myoffers';
                    });
          
        } else{
          $http({
                    url: '/services/offer',
                    method: 'POST',
                    isArray: false,
                    data: { "title" : $scope.offerTitle,
                        "description" : $scope.offerDescription,
                        "trgtIndustry" : industries,
                        "intdAudience" : intAudience[0],
                        "trgtLocation" : state,
                        "imgUrl" :$scope.imageUrl,
                        "questionList":questions
                        
                        },
                    cache : false}).then(function(response){
                      $scope.waiting = false;
                      $scope.id = response.data;
                      var tagEntity = { "entityId" : response.data, "entityType" : "OFFER", "tags" : tags }
                      $http({
                        url: 'services/tag',
                        method: 'POST',
                        data: tagEntity
                      }).then(function(response){
                        $scope.waiting = false;
                      },function(error){
                        console.log('TAG Entity error',error);
                      });
                      //$window.location.href = '/#/myoffers';
                    }, function(response){
                      //$window.location.href = '/#/myoffers';
                    });
        }
        }
        
    	  
      };
  }]);