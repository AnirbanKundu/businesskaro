angular
  .module('theme.core.search_controller', [])
  .controller('SearchController', ['$rootScope','$scope', '$http', '$route', '$state', '$log', '$timeout', '$location', 'EntityService', 'LookUpService', function($rootScope, $scope, $http, $route, $state, $log, $timeout, $location, EntityService, LookUpService) {
    'use strict';
    var pageSize = 10,count=0;   
    $scope.show = false; 
    $scope.industries = [];
    $scope.selectedIndustries = { "selected": [] };
    $rootScope.searchType = $scope.searchType = $route.current.params.type || 'all';
    $rootScope.keywords = $scope.keywords = $route.current.params.keywords || 'all';
    $scope.industry = $scope.keywords.split(',')[0];
    $scope.state = $scope.keywords.split(',')[1];


    LookUpService.getIndustries().then(function(data){
        $scope.industries = data;
        for(var i=0;i<$scope.industries.length;i++){
          if($scope.industries[i].industryName == $scope.industry){
            $scope.selectedIndustries.selected = $scope.industries[i];
            break;
          }
        }
    },function(error){
        $log.log(error);
    });

    $scope.selectedStates = { "selected": [] };
    LookUpService.getStates().then(function(data){
        $scope.states = data;
        for(var i=0;i<$scope.states.length;i++){
        if($scope.states[i].stateName == $scope.state){
          $scope.selectedStates.selected = $scope.states[i];
          break;
        }
      }
      },function(error){
        $log.log(error);
    });

    $timeout(function(){
      twttr.widgets.load(); 
      FB.init({
        appId      : '1628152447465336',
        status     : true,
        xfbml      : true,
        version    : 'v2.3' // or v2.0, v2.1, v2.0
      });
    },1000);

    function shiftArray(arr){
      var elm = arr.shift();
      if(elm){
        console.log('Current Count is',count++);      
        EntityService.getEntityData({entityType:elm.entityType, entityId:elm.entityId}).then(function(data){
          //elm.data = data;
          //o.entityType == elm.entityType && o.entityId == elm.entityId;
          $timeout(function(){
            var insertedElem = _.findWhere($scope.searchResults,{entityType:elm.entityType,entityId:elm.entityId});
            insertedElem.data = data;
            $log.log('Arr data:', elm);
            console.log('THE SCOPE ARRAY IS:',$scope.searchResults);
          },10);          
          if(arr.length>0){
            shiftArray(arr);
          }       
        },
        function(data){ 
          $log.log('Error');
        });
      }            
    };

    function sliceArrayAndRemove(arr){
      var temp =[], mainArr = angular.fromJson(angular.toJson(arr));
      for(var i=0;i<20;i++){ //=> DO NOT MAKE MORE THAN 4 parallel calls. 
        if(mainArr && mainArr.length>0){
          temp.push(mainArr.shift());
        }        
      }
      return temp;
    }

    function makeAJAXCall(arr){
      console.log('In makeAJAXCall', arr.length);
      shiftArray(arr);
      shiftArray(arr);
      shiftArray(arr);
    }
    
    $scope.searchResults = [];
    $scope.returnPagedData = function(data, pagenumber){
      var searchLength = $scope.searchResults.length;
      var masterLength = $scope.MasterSerachResult.length;
      if(searchLength + pageSize < masterLength){
        return $scope.MasterSerachResult.splice(0,searchLength+pageSize);
      }
      else{
        return $scope.MasterSerachResult.splice(0,masterLength);
      }      
    };

    $scope.searchTags = function(){
      var keywords='',
      industry = $scope.selectedIndustries.selected.industryName;
      var state = $scope.selectedStates.selected.stateName;;

      if(industry){
        keywords = industry+',';
      }
      if(state){
        keywords+= state+',';
      }
      keywords = keywords.substring(0, keywords.lastIndexOf(','));
      $location.path('/search/ALL/'+keywords);
      console.log('Tags selected are:', $scope.selectedTag);
    }

    

    //services/tag/entity?keyword=JAVA&entityType=ALL ; services/tag/entity?keywords ; appdata/tagentity.json
    $http({
        url : 'services/tag/entity?keywords='+$scope.keywords+'&entityType='+$scope.searchType,
        method: 'GET'
      }).then(function(response){    
        $    
        $scope.MasterSerachResult = angular.fromJson(angular.toJson(response.data));
        $scope.searchResults = $scope.returnPagedData($scope.searchResults,0);
        $timeout(function(){
          var arrToMakeCalls =  sliceArrayAndRemove($scope.searchResults);
          makeAJAXCall(arrToMakeCalls);
        },2000);
        
      },function(error){
        console.log('Error in delete');
    });

}])
.filter('industryfilter',['$filter','LookUpService',function datefilter($filter,LookUpService){
  return function(array){ 
    var industryNames = ''
    if(array){
      for(var i=0;i<array.length;i++){
        industryNames += LookUpService.getIndustryName(array[i]) + ','
      }
      industryNames = industryNames.substring(0, industryNames.lastIndexOf(','));
      return industryNames;
    }
  } 
}]);