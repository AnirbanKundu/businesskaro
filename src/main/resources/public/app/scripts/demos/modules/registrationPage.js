angular
  .module('theme.demos.registration_page', [])
  .controller('RegistrationPageController', ['$rootScope', '$scope', '$timeout' , '$log', '$http', 'LookUpService', 'UserAuthentication', '$route', function($rootScope, $scope, $timeout, $log, $http, LookUpService, UserAuthentication,$route) {
    'use strict';
    $scope.reg_form = {};
    $scope.isSaveClicked = false;
    $scope.formControl = {
      isfirstNameValid:true,
      islastNameValid:true,
      isaboutMeValid:true,
      isprofessionValid:true,
      isageGroupValid:true,
      iseducatonValid:true,
      isexperienceValid:true,
      isstateValid:true,
      iscompanyUrlValid:true,
      isIndustryValid:true,
      isFBUrlValid:true,
      istwitterUrlValid:true,
      islinkedinUrlValid:true   
    };
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
        if(data.data.newUser){
          $scope.newUser = true;
          $rootScope.newUser=true;
          $rootScope.profileCreated=0;
        }
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
    $scope.checkValidation = function(prop,value){
      if($scope.formControl.hasOwnProperty(prop)){
        if(value && value!="0"){
          $scope.formControl[prop] =  true;
        }
        else{
          $scope.formControl[prop] =  false;  
        }        
      }
    }
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
    $scope.$watch('user.summary.firstName',function(newval,oldval){
      $scope.checkValidation('isfirstNameValid', newval);   
    });
    $scope.$watch('user.summary.lastName',function(newval,oldval){
      $scope.checkValidation('islastNameValid', newval);   
    });
    $scope.$watch('user.summary.aboutMe',function(newval,oldval){
      $scope.checkValidation('isaboutMeValid', newval);   
    });
    $scope.$watch('user.details.professionalId',function(newval,oldval){
      $scope.checkValidation('isprofessionValid', newval);   
    });
    $scope.$watch('user.details.ageGroupId',function(newval,oldval){
      $scope.checkValidation('isageGroupValid', newval);   
    });
    $scope.$watch('user.details.educatonId',function(newval,oldval){
      $scope.checkValidation('iseducatonValid', newval);   
    });
    $scope.$watch('user.details.experienceId',function(newval,oldval){
      $scope.checkValidation('isexperienceValid', newval);   
    });
    $scope.$watch('user.details.stateId',function(newval,oldval){
      $scope.checkValidation('isstateValid', newval);   
    });
    $scope.$watch('user.summary.companyUrl',function(newval,oldval){
      if($scope.user.summary.userType!='E'){
        $scope.checkValidation('iscompanyUrlValid', newval);   
      }      
    });
    $scope.$watch('user.details.faceBookUrl',function(newval,oldval){
      if(newval){
        if(!validateURL(newval,"facebook")){
          $scope.checkValidation('isFBUrlValid', null); 
        }else{
          $scope.checkValidation('isFBUrlValid', "fb"); 
        }  

      }else{
        $scope.checkValidation('isFBUrlValid', "fb"); 
      }        
    });
    $scope.$watch('user.details.twiterURL',function(newval,oldval){
      if(newval){
        if(!validateURL(newval,"twitter")){
          $scope.checkValidation('istwitterUrlValid', null);   
        }else{
          $scope.checkValidation('istwitterUrlValid', "tw"); 
        }      
      }else{
        $scope.checkValidation('istwitterUrlValid', "tw"); 
      }
      
    });
    $scope.$watch('user.details.linkedInUrl',function(newval,oldval){
      if(newval){
        if(!validateURL(newval,"linkedin")){
          $scope.checkValidation('islinkedinUrlValid', null);   
        }else{
          $scope.checkValidation('islinkedinUrlValid', "ln"); 
        }      
      }else{
        $scope.checkValidation('islinkedinUrlValid', "ln"); 
      }
    });
    $scope.$watch('formControl',function(newval,oldval){
      if(newval && newval!=oldval){
      }
    },true);
    $scope.$watch('selectedIndustries',function(newval,oldval){      
      if(newval!=null && newval.selected){
        $scope.checkValidation('isIndustryValid', newval.selected[0]);    
      }      
    },true);

    function validateURL(textval,type){
      var urlregex = new RegExp(
            "^(http|https|ftp)\://([a-zA-Z0-9\.\-]+(\:[a-zA-Z0-9\.&amp;%\$\-]+)*@)*((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\-]+\.)*[a-zA-Z0-9\-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(\:[0-9]+)*(/($|[a-zA-Z0-9\.\,\?\'\\\+&amp;%\$#\=~_\-]+))*$");
      if(textval.toUpperCase().indexOf(type)!==-1){
        return urlregex.test(textval);
      }else{
        return false;
      }
    }

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
      var tags = [], isFormValid = true;
      $scope.isSaveClicked = true;
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
          $scope.firstLogin = null;
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
      }      
    }

  }]);