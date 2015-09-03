angular
  .module('theme.core.search_controller', [])
  .controller('SearchController', ['$scope', '$http', '$route', '$state', '$log', '$timeout', 'EntityService', function($scope, $http, $route, $state, $log, $timeout, EntityService) {
    'use strict';
    var pageSize = 10,count=0;    
    function shiftArray(arr){
      var elm = arr.shift();
      if(elm){
        console.log('Current Count is',count++);      
        EntityService.getEntityData({url:'user'}).then(function(data){
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
      for(var i=0;i<4;i++){ //=> DO NOT MAKE MORE THAN 4 parallel calls. 
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

    $scope.searchType = $route.current.params.type || 'all';
    $scope.industry = $state.get('industry') || 'all';
    $scope.state = $state.get('state') || 'all';
    console.log('searchType is:',$scope.searchType);
    console.log('industry is:',$scope.industry);
    //services/tag/entity?keyword=JAVA&entityType=ALL
    $http({
        url : 'appdata/tagentity.json',
        method: 'GET'
      }).then(function(response){        
        $scope.MasterSerachResult = angular.fromJson(angular.toJson(response.data));
        $scope.searchResults = $scope.returnPagedData($scope.searchResults,0);
        $timeout(function(){
          var arrToMakeCalls =  sliceArrayAndRemove($scope.searchResults);
          makeAJAXCall(arrToMakeCalls);
        },2000);
        
      },function(error){
        console.log('Error in delete');
    });

}]);