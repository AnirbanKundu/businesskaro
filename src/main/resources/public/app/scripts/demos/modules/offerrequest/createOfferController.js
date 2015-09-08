angular
  .module('theme.demos.offer', [])
  .controller('CreateOfferController', ['$scope', '$timeout' , '$log', '$http', 'LookUpService', 'UserAuthentication', '$window', function($scope, $timeout, $log, $http,LookUpService,UserAuthentication,$window) {
    'use strict';
    

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

    
    LookUpService.getAgeGroup().then(function(data){ 
      $scope.ageGroup = data;
    },function(error){
      $log.log(error); 
    });
    LookUpService.getEducations().then(function(data){
      $scope.educations = data;
    },function(error){
      $log.log(error);
    });
    LookUpService.getProfession().then(function(data){
      $scope.professions = data;
    },function(error){
      $log.log(error);
    });
    LookUpService.getStates().then(function(data){
        $scope.states = data;
      },function(error){
        $log.log(error);
      });
    LookUpService.getQuestions('O').then(function(data){
        $scope.questions = data;
      },function(error){
        $log.log(error);
      });
    
    $scope.selectedIndustries = { "selected": [] };
    $scope.selectedStates = { "selected": [] };
    $scope.lookingfor = { "selected": [] };
    $scope.industries = [];
    $http.get('utilservices/industries').success(function(response) {
      $scope.industries = response;
    });
    
    $scope.$watch('selectedIndustries',function(newval,oldval){
    	console.log('selected industriess',newval);    
    });
    
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
      
      $scope.save =  function(){
    		var state=[];
    		for(var i=0;i<$scope.selectedStates.selected.length;i++){
    			state.push($scope.selectedStates.selected[i].stateId);
    		}
    		var industries=[];
    		for(var i=0;i<$scope.selectedIndustries.selected.length;i++){
    			industries.push($scope.selectedIndustries.selected[i].industryId);
    		}
    	  $http({
              url: '/services/offer',
              method: 'POST',
              isArray: false,
              data: { "title" : $scope.offerTitle,
                  "compName" : $scope.offerCompanyname,
                  "description" : $scope.offerDescription,
                  "trgtIndustry" : industries,
                  "intdAudience" : $scope.profId,
                  "trgtLocation" : state,
                  "imgUrl" :$scope.imageUrl
                  },
              cache : false}).then(function(response){
            	  $window.location.href = '/#/myoffers';
              }, function(response){
            	  $window.location.href = '/#/myoffers';
              });
      };
  }]);