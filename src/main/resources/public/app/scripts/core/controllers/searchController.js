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
          if(arr.length>0){
            shiftArray(arr);
          }
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
      if(masterLength > pageSize){
        return $scope.MasterSerachResult.splice(0,pageSize);
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

    $scope.loadMoreData = function(){
      var searchData = $scope.returnPagedData($scope.searchResults,$scope.searchResults.length);
      for(var i=0;i<searchData.length;i++){
        $scope.searchResults.push(searchData[i]);
      }
      $timeout(function(){
          //var arrToMakeCalls =  sliceArrayAndRemove($scope.searchResults);
          makeAJAXCall(searchData);
      },50);
    }
    
    $scope.searchType = $scope.searchType.toUpperCase();
    $scope.isSearchButtonVisible = false;
    //services/tag/entity?keyword=JAVA&entityType=ALL ; services/tag/entity?keywords ; appdata/tagentity.json
    $http({
        url : 'services/tag/entity?keywords='+$scope.keywords+'&entityType='+$scope.searchType,
        method: 'GET'
      }).then(function(response){   
        //var data = _.sortBy(stooges, 'name');   
        $scope.MasterSerachResult = angular.fromJson(angular.toJson(response.data.sort(function(a, b) {
            return b.createdDate - a.createdDate
        })));
        $scope.searchResults = $scope.returnPagedData($scope.searchResults,0);
        $scope.isSearchButtonVisible = ($scope.searchResults.length < $scope.MasterSerachResult); 
        $timeout(function(){
          var arrToMakeCalls =  sliceArrayAndRemove($scope.searchResults);
          makeAJAXCall(arrToMakeCalls);
        },50);        
      },function(error){
        console.log('Error in delete');
    });

    $scope.showDetails=function(result,$event){      
      $event.preventDefault();
      var entityType;
      if(result.entityType==='USER_PROFILE'){
        entityType='USER'
      }else if(result.entityType=='OFFER'){
        entityType='OFFER'
      }else if(result.entityType=='REQUEST'){
        entityType='REQUEST'
      }else{
        entityType= 'GOVT_POLICY'
      }
      $location.path('/entitydetail/'+entityType+'/'+ result.entityId);

    };

}])
.filter('industryfilter',['$filter','LookUpService',function datefilter($filter,LookUpService){
  return function(array,type){ 
    var industryNames = ''
    if(array){
      for(var i=0;i<array.length;i++){
        industryNames += LookUpService.getIndustryName(array[i]) + ','
      }
      industryNames = industryNames.substring(0, industryNames.lastIndexOf(','));
      if(type){
        industryNames = industryNames.length > 65 ? industryNames.substring(0,65) + '...' : industryNames;
      }
      return industryNames;
    }
  } 
}])
.filter('statefilter',['$filter','LookUpService',function datefilter($filter,LookUpService){
  return function(array,type){ 
    var stateNames = ''
    if(array){
      for(var i=0;i<array.length;i++){
        stateNames += LookUpService.getStateName(array[i]) + ','
      }
      stateNames = stateNames.substring(0, stateNames.lastIndexOf(','));
      if(type){
        stateNames = stateNames.length > 65 ? stateNames.substring(0,65) + '...' : stateNames;
      }
      return stateNames;
    }
  } 
}])
.filter('maxlengthfilter',['$filter',function statefilter(){
  return function(value,length){ 
    var stateNames = ''
    if(value){
      if(value.length<=length){
        return value;
      }else{
        return value.substring(0,length) + '...'
      }
    }
  } 
}])
.filter('educationfilter',['$filter','LookUpService',function datefilter($filter,LookUpService){
  return function(array){ 
    var educationNames = '', Arr = [];
    
    if(array){
      if(array.constructor !== Array){
        Arr.push(array)
      }
      for(var i=0;i<Arr.length;i++){
        educationNames += LookUpService.getEducationName(Arr[i]) + ','
      }
      educationNames = educationNames.substring(0, educationNames.lastIndexOf(','));
      return educationNames;
    }
  } 
}])
.filter('ageGroupfilter',['$filter','LookUpService',function datefilter($filter,LookUpService){
  return function(array){ 
    var ageGroupNames = '', Arr = [];
    if(array){
      if(array.constructor !== Array){
        Arr.push(array)
      }  
      for(var i=0;i<Arr.length;i++){
        ageGroupNames += LookUpService.getAgeGroupName(Arr[i]) + ','
      }
      ageGroupNames = ageGroupNames.substring(0, ageGroupNames.lastIndexOf(','));
      return ageGroupNames;
    }
  } 
}])
.filter('professionfilter',['$filter','LookUpService',function datefilter($filter,LookUpService){
  return function(array){ 
    var professionNames = '', Arr = [];
    if(array){
      if(array.constructor !== Array){
        Arr.push(array)
      }
      for(var i=0;i<Arr.length;i++){
        professionNames += LookUpService.getProfessionName(Arr[i]) + ','
      }
      professionNames = professionNames.substring(0, professionNames.lastIndexOf(','));
      return professionNames;
    }
  } 
}]) 
.filter('audiencefilter',['$filter',function audiencefilter($filter){
  return function(value){ 
    var professionNames = '';
    if(value){
      if(value==1){
        return '10-100';
      }else if(value==2){
        return '101-1000';
      }else{
        return '1001-10000';
      }
    }
  } 
}])
.filter('userTypefilter',['$filter',function datefilter($filter){
  return function(value){ 
    var userType='';
    if(value){
      if(value==='E'){
        return 'Entrepreneur';
      }
      else if(value==='P'){
        return 'Provider';
      }
      else{
        return 'Entrepreneur & Provider';
      }
    }
  } 
}]);