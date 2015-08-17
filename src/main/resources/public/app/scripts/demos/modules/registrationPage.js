angular
  .module('theme.demos.registration_page', [])
  .controller('RegistrationPageController', ['$scope', '$timeout' , '$log', '$http', 'LookUpService', 'UserAuthentication', function($scope, $timeout, $log, $http, LookUpService, UserAuthentication) {
    'use strict';
    $scope.reg_form = {};
    $scope.form = {};

    $scope.checking = false;
    $scope.checked = false;
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
    
    UserAuthentication.getUserDetailProfile().then(function(data){
      if(data && data.user){
        //TO DO - SET THE VALUES ACCORDINGLY
        $scope.selectedAgeId=0;
        $scope.selectedEducationId=0;
        $scope.selectedProfessionId = 0;
        $scope.selectedStateId = 0;
        $scope.selectedExperienceId = 0;
        if(data.user.ImageUrl){
          var imagePath = data.user.ImageUrl;
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
    },function(error){

    });
    $scope.selectedIndustries = {};
    $scope.lookingfor = {};
    $scope.industries = [];
    $http.get('utilservices/industries').success(function(response) {
      $scope.industries = response;
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
            $scope.userImageId = response.data.publicId;
            $scope.userImageUrl = response.data.url;
            return response.data;
          }, function(response){
              alert("Error loading file... Please try again.");
            return response.data;
          });
    };
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