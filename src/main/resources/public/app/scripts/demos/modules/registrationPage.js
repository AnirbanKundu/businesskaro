angular
  .module('theme.demos.registration_page', [])
  .controller('RegistrationPageController', ['$rootScope', '$scope', '$timeout' , '$log', '$http', 'LookUpService', 'UserAuthentication', '$route', function($rootScope, $scope, $timeout, $log, $http, LookUpService, UserAuthentication,$route) {
    'use strict';
    $scope.reg_form = {};
    $scope.form = {};
    var message = $route.current.params.message;
    if(message && message ==='firstLogin'){
      $scope.firstLogin = message;
      $scope.alert = { type: 'success', msg: '<strong>Welcome to BusinessKaro</strong>. Please enter your personal details.' };
    }
    else if(message && message ==='filldetails'){
      $scope.firstLogin = message;
      $scope.alert = { type: 'warning', msg: 'You need to enter your profile information to connect.' };
    }
    $scope.checking = false;
    $scope.checked = false;
    $scope.user = {};
    $scope.selectedAgeId=0;
    $scope.selectedEducationId=0;
    $scope.selectedProfessionId = 0;
    $scope.selectedStateId = 0;
    $scope.newUser = false;
    $scope.waiting = true;
    /*********** Get all Lookup values *********/
    $scope.selectedIndustries = { "selected": [] };
    $scope.lookingfor = { "selected": [] };
    $scope.industries = [];
    

    LookUpService.getIndustries().then(function(data){
        $scope.industries = data;
    },function(error){
        $log.log(error);
    });

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

    
    UserAuthentication.getUserDetailProfile().then(function(data){
      if(data && data.data.details && data.data.summary){
        //TO DO - SET THE VALUES ACCORDINGLY
        $scope.user.details = data.data.details;
        $scope.user.summary = data.data.summary;
        for(var i=0;i<$scope.user.summary.industrys.length;i++){
          for(var j=0;j<$scope.industries.length;j++){
            if($scope.user.summary.industrys[i] == $scope.industries[j].industryId){
              $scope.selectedIndustries.selected.push($scope.industries[j]);
              break;
            }
          }
        }

        for(var i=0;i<$scope.user.summary.lookinfForSkill.length;i++){
          for(var j=0;j<$scope.industries.length;j++){
            if($scope.user.summary.lookinfForSkill[i] == $scope.industries[j].industryId){
              $scope.lookingfor.selected.push($scope.industries[j]);
              break;
            }
          }
        }
        /*
        for(var i=0;i<$scope.states.length;i++){
          if($scope.states[i].stateName == $scope.state){
            $scope.selectedStates.selected = $scope.states[i];
            break;
          }
        }
        */

        //$scope.selectedIndustries.selected = $scope.user.summary.industrys;
        if(data.data.newUser){
          $scope.newUser = true;
          $rootScope.newUser=true;
          $rootScope.profileCreated=0;
        }
        //$scope.selectedAgeId=0;
        //$scope.selectedEducationId=0;
        //$scope.selectedProfessionId = 0;
        //$scope.selectedStateId = 0;
        //$scope.selectedExperienceId = 0;
        if($scope.user.details.imageUrl){
          var imagePath = $scope.user.details.imageUrl;
          var widgetFileInput = $('.fileinput').fileinput();
          widgetFileInput.addClass('fileinput-exists').removeClass('fileinput-new');
          if(imagePath){  //user_avator)
            widgetFileInput.find('.thumbnail').append('<img src="' +imagePath+ '">');
            $scope.userImageId = imagePath.substring(imagePath.lastIndexOf('/')+1, imagePath.length).split('.')[0];    
            $scope.actualImageName = imagePath.substring(imagePath.lastIndexOf('/')+1, imagePath.length);
            console.log('actualImageName is :',$scope.actualImageName);
          }          
        }
      }
      $scope.waiting = false;
      /*
      else if(data && data.newuser){
        $scope.newUser = true;
        $scope.selectedAgeId=0;
        $scope.selectedEducationId=0;
        $scope.selectedProfessionId = 0;
        $scope.selectedStateId = 0;
        $scope.selectedExperienceId = 0;        
      }
      */
    },function(error){
      if(error.type==='ENTITY_NOT_FOUND'){
        $rootScope.profileCreated=0;
      }
      console.log('Error happened');
    });
    
    //*********************
    //While getting the user info get the image path and then set to it
    $scope.userImageId = "";
    $scope.userImageUrl = "";
    $scope.isUploadCalled=false;
    $scope.actualImageName = "";
    //***********************
    $scope.$watch('selectedIndustries',function(newval,oldval){
    	console.log('selected industriess',newval);    
    });




    /*********** End of all loop ups ***********/    
    
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
        $scope.userImageId = "";
        $scope.userImageUrl = "";
        $scope.user.details.imageUrl = "";
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
            $scope.user.details.imageUrl = response.data.url;
            $scope.userImageId = response.data.publicId;
            $scope.userImageUrl = response.data.url;
            return response.data;
          }, function(response){
              alert("Error loading file... Please try again.");
            return response.data;
          });
    };
    $scope.updateUserType = function(){
      if(user.summary.userType ==="B"){
        
      }
    };

    $scope.saveUserInfo = function(){
      $scope.user.summary.lookinfForSkill = [];
      $scope.user.summary.industrys = [];
      var tags = [];
      for(var i=0; i<$scope.lookingfor.selected.length;i++){
        $scope.user.summary.lookinfForSkill.push($scope.lookingfor.selected[i].industryId);
      }
      for(var i=0;i<$scope.states.length;i++){
        if($scope.states[i].stateId == $scope.user.details.stateId){
          $scope.user.summary.stateName = $scope.states[i].stateName;
          tags.push($scope.states[i].stateName);
          break;
        }        
      }

      for(var i=0;i<$scope.selectedIndustries.selected.length;i++){
        $scope.user.summary.industrys.push($scope.selectedIndustries.selected[i].industryId);
        tags.push($scope.selectedIndustries.selected[i].industryName);
      }
      if($scope.user.summary.userType ==='E'){
        $scope.user.summary.companyUrl = "";
      }
      $scope.waiting = true;
      UserAuthentication.saveUserDetailProfile($scope.user).then(function(data){
        $rootScope.newUser=false;
        $rootScope.profileCreated=1;
        //CALL THE TAGENTRY
        var tagEntity = { "entityId" : data.data.summary.userId, "entityType" : "USER_PROFILE", "tags" : tags }
        $http({
          url: 'services/tag',
          method: 'POST',
          data: tagEntity
        }).then(function(response){
          $scope.waiting = false;
        },function(error){
          console.log('TAG Entity error',error);
        });
      },function(data){
        console.log('error'); 
      });
      /*
      $scope.selectedAgeId=0;
      $scope.selectedEducationId=0;
      $scope.selectedProfessionId = 0;
      $scope.selectedStateId = 0;
      $scope.selectedExperienceId = 0;  

      var userDetail = {
          "ageGroupId": $scope.selectedAgeId, 
          "educatonId": $scope.selectedEducationId , 
          "stateId" : $scope.selectedStateId, 
          "experienceId" : $scope.selectedExperienceId,
          "professionalId" : $scope.selectedProfessionId,
          "faceBookUrl" : $scope.faceBookUrl,
          "linkedInUrl" : $scope.linkedInUrl,
          "twiterURL" : $scope.twiterURL,
          "imageUrl": $scope.imageUrl
      },
      summaryDetail = {
        "firstName": "anirban",
        "lastName": "kundu",
        "stateName": "WB",
        "cityName": "CAL",


      } 
      */
    }

    $scope.checkAvailability = function() {
      if ($scope.reg_form.username.$dirty === false) {
        return;
      }
      $scope.checking = true;
      $timeout(function() {
        $scope.checking = false;
        $scope.checked = true;
      }, 500);
    };
  }]);