angular
  .module('theme.core.entitydetail_controller', ['theme.core.search_controller'])
  .controller('EntityDetailController', ['$scope', '$http', '$route', '$state', '$timeout', '$log', 'EntityService','LookUpService', function($scope, $http, $route, $state, $timeout, $log, EntityService,LookUpService) {
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
      $scope.relatedTags=[];


    	console.log('Data in entity detail:',$scope.entityResult);
    	$scope.connectMessage='';
    	//Get Related Entity details
    	if($scope.entityType!='GOVT_POLICY'){
    		if($scope.entityType=='USER'){
          $timeout(function(){
            for(var i=0;i<$scope.entityResult.summary.industrys.length;i++){
              var industryName = LookUpService.getIndustryName($scope.entityResult.summary.industrys[i]);
              var t = {
                name: industryName,
                url : '#/search/ALL' + '/' + industryName
              }
              $scope.relatedTags.push(t);
            }
            var s = {
              name: $scope.entityResult.summary.stateName,
              url : '#/search/ALL' + '/' + ',' + $scope.entityResult.summary.stateName
            }
            $scope.relatedTags.push(s);
          },500);
    			$http({
		          url : '/services/offer/',
		          method: 'GET'
		        }).then(function(response){
		        	$scope.offers = response.data;
		        },function(error){

		        });
		        $http({
		          url : '/services/request/',
		          method: 'GET'
		        }).then(function(response){
		        	$scope.requests = response.data;
		        },function(error){

		        });
    		}
    		else{
    			
          $timeout(function(){
            for(var i=0;i<$scope.entityResult.summary.industrys.length;i++){
              var industryName = LookUpService.getIndustryName($scope.entityResult.summary.industrys[i]);
              var t = {
                name: industryName,
                url : '#/search/ALL' + '/' + industryName
              }
              $scope.relatedTags.push(t);
            }
            var s = {
              name: $scope.entityResult.summary.stateName,
              url : '#/search/ALL' + '/' + ',' + $scope.entityResult.summary.stateName
            }
            $scope.relatedTags.push(s);
          },500);

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
      else{
        $timeout(function(){
            for(var i=0;i<$scope.entityResult.industrys.length;i++){
              var industryName = LookUpService.getIndustryName($scope.entityResult.industrys[i]);
              var t = {
                name: industryName,
                url : '#/search/ALL' + '/' + industryName
              }
              $scope.relatedTags.push(t);
            }
            for(var i=0;i<$scope.entityResult.states.length;i++){
              var stateName = LookUpService.getIndustryName($scope.entityResult.states[i]);
              var s = {
                name: stateName,
                url : '#/search/ALL' + '/' + ',' + stateName
              }
              $scope.relatedTags.push(s);
            }
        },500);
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
              data: { "toId" : 52,//$scope.entityResult.userId || $scope.entityResult.summary.userId,
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