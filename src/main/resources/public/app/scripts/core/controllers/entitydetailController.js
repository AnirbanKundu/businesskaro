angular
  .module('theme.core.entitydetail_controller', ['theme.core.search_controller'])
  .controller('EntityDetailController', ['$scope', '$http', '$route', '$state', '$log', '$timeout', 'EntityService','LookUpService', function($scope, $http, $route, $state, $log, $timeout, EntityService,LookUpService) {
    'use strict';

    $scope.entityType = $route.current.params.type.toUpperCase();
    $scope.entityId = $route.current.params.entityId;
    $scope.entityResult = {};
    $scope.userDetail = {};
    LookUpService.getStates().then(function(data){

    },function(error){
        $log.log(error);
    });
    LookUpService.getIndustries().then(function(data){
    },function(error){
        $log.log(error);
    });
    LookUpService.getAgeGroup().then(function(data){ 
    },function(error){
      $log.log(error); 
    });
    LookUpService.getEducations().then(function(data){
    },function(error){
      $log.log(error);
    });
    LookUpService.getProfession().then(function(data){
    },function(error){
      $log.log(error);
    });
    EntityService.getEntityDetailData({'entityType':$scope.entityType,'entityId':$scope.entityId}).then(function(data){
    	$scope.entityResult = data;
    	console.log('Data in entity detail:',$scope.entityResult);
    	$scope.connectMessage='';
    	//Get Related Entity details
    	if($scope.entityType!='GOVT_POLICY'){
    		if($scope.entityType=='USER'){
    			$http({
		          url : '/services/offer/user/'+ $scope.entityResult.summary.userId,
		          method: 'GET'
		        }).then(function(response){
		        	$scope.offers = response.data;
		        },function(error){

		        });
		        $http({
		          url : '/services/request/user/'+ $scope.entityResult.summary.userId,
		          method: 'GET'
		        }).then(function(response){
		        	$scope.requests = response.data;
		        },function(error){

		        });
    		}
    		else{
    			//GET USER Details
          var questionType='';
          $scope.questions = [];
          if($scope.entityType=='OFFER'){
            questionType='O';
          }
          else{
            questionType='R';
          }
          LookUpService.getQuestions(questionType).then(function(data){
              for(var i=0;i<data.length;i++){
                for(var j=0;j<$scope.entityResult.questionList.length;i++){
                  if(data[i].questId==$scope.entityResult.questionList[j].questionId){
                    var q = {
                      questId:data[i].questId,
                      questTxt:data[i].questTxt,
                      response : $scope.entityResult.questionList[j].response
                    };
                    $scope.questions.push(q);
                    break;
                  }
                }
              }
            },function(error){
              console.log('In questions http');
              $log.log(error);
          });
    			EntityService.getRelatedUserDetail({'userId':$scope.entityResult.userId}).then(function(data){
    				$scope.userDetail = data;
    			},function(error){
    				console.log('Error now',error);
    			});
    		}
    	}

    },function(error){
    	console.log('Error now');
    });

    $timeout(function(){
      FB.init({
        appId      : '1628152447465336',
        status     : true,
        xfbml      : true,
        version    : 'v2.3' // or v2.0, v2.1, v2.0
      });
    },1000);

    $scope.connect = function(connectMessage){
    	$http({
              url: '/services/communicate',
              method: 'POST',
              isArray: false,
              data: { "toId" : $scope.entityResult.userId || $scope.entityResult.summary.userId,
                  "message" : connectMessage,
                  "entityType" : $scope.entityType,
                  "entityId" : $scope.entityId
                  },
              cache : false
        }).then(function(data){

        },function(error){

        });
    }

  }]);