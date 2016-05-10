angular.module('theme.demos.dashboard', [
    'angular-skycons',
    //'theme.demos.forms',
    //'theme.demos.tasks',
    'theme.core.services'
  ])
  .controller('DashboardController', ['$scope', '$timeout', '$log', '$window', '$http' , '$location', '$theme', '$state', 'LookUpService',function($scope, $timeout, $log, $window,$http, $location,$theme, $state, LookUpService) {
    'use strict';
    var moment = $window.moment;
    var _ = $window._;
    $theme.set('leftbarCollapsed', true);
    //$theme.set('layoutHorizontal', true);
    $scope.$on('$destroy', function() {
      $theme.set('leftbarCollapsed', false);
    });
    $scope.selectedTag = { "selected": [] };
    $scope.selectedValues = undefined;
    //$scope.selectedIndustries = { "selected": [] };
    //$scope.selectedStates = { "selected": [] };
    $scope.industries = [];
    $scope.selectedIndustry = "SELECT";
    $scope.selectedState = "SELECT";
    LookUpService.getIndustries().then(function(data){
        $scope.industries = data;
    },function(error){
        $log.log(error);
    });
    LookUpService.getStates().then(function(data){
        $scope.states = data;
      },function(error){
        $log.log(error);
      });

    $scope.tags = [];
    /*
    $scope.getTags = function(val) {
      if(val){
        $http.get('services/tag/names?keyword').success(function(data) {
          $scope.tags = data;
        });
      }      
    };
    */
    $scope.tagTransform = function(newTag){
      var item = {
        tagId: -1,
        name : newTag.toLowerCase()
      };
      return item;
    };

    $timeout(function(){
      twttr.widgets.load(); 
      FB.init({
        appId      : '1628152447465336',
        status     : true,
        xfbml      : true,
        version    : 'v2.3' // or v2.0, v2.1, v2.0
      });
    },1000);

    $scope.searchTags = function(){
      var keywords='',
      industry = $scope.selectedIndustry,//$scope.selectedIndustries.selected.industryName,
      state = $scope.selectedState;//$scope.selectedStates.selected.stateName;

      if(industry){
        keywords = industry+',';
      }
      if(state){
        keywords += state + ',';
      }      
      keywords = keywords.substring(0, keywords.lastIndexOf(','));

      var validForm = true;
      if($scope.selectedIndustry==='SELECT'|| $scope.selectedState==='SELECT'){
        validForm  = false;
      }
      if(validForm){
        $timeout(function(){
          $location.path('/search/ALL/'+keywords);
        },100)
      }
    }
    
    $scope.contactusForm={};    
    $scope.resetValidationForm = function() {      
      $scope.contactusForm.contacterName = '';
      $scope.contactusForm.email = '';
      $scope.contactusForm.mobile = '';
      $scope.contactusForm.subject = '';
      $scope.contactusForm.message = '';
      };
    
    
    
    
    
    
    
    
    $scope.contactus = function($event){      
      $event.preventDefault();
      $http({
            url: 'services/contactus',
            method:'POST',
            data:{
              "name":$scope.contactusForm.contacterName,
              "email":$scope.contactusForm.email,
              "mobileNo":$scope.contactusForm.mobile,
              "subject":$scope.contactusForm.subject,
              "message":$scope.contactusForm.message
            }
          }).then(function(response){
            $scope.message = 'success';
            $scope.alert = { type: 'success', msg: 'Email sent succesfully. You would be contacted shortly.'};
            $scope.contactusForm = {};
          },function(error){
            $scope.alert = { type: 'alert', msg: 'Email was not sent. Try again.'};
          });
    }

    $scope.refreshTags = function(value) {
     if(value=="" || value==undefined ){
       return;
     }  
      var params = {keyword: value};
      return $http.get(
        'services/tag/names?',
        {params: params}
      ).then(function(response) {
        $scope.tags = response.data;
      });
    };

    //CarouselController
    $scope.myInterval = 5000;
    var slides = $scope.slides = [];
    var images = ['01.jpg', '02.jpg', '03.jpg', '04.jpg', '05.jpg'];
    var title = ['', '', '','',''];  
    $scope.addSlide = function() {
      slides.push({
        image: '../../assets/img/' + images[slides.length],
        text: title[slides.length]
      });
    };
    for (var i = 0; i < 5; i++) {
      $scope.addSlide();
    }
    
  //isFeatured policies--written by nagendra
    $http.get('/services/getAllIsFeaturedPolicies').success(function(response) {
        $scope.policies = response;         
        $scope.waiting = false;
    }).error(function () {
        $scope.policies = [];
        $scope.waiting = false;
    });   
    
    
    
    //public featured news
    //written by nagendra
   /* $scope.showDetails=function(id){         
        $location.path('/public/'+'/featurednews/'+id);
      };
    */
    $scope.linkClicked = function(id){
        // your code here
        $location.path('/public/featurednews/'+id);
    };
  }]);