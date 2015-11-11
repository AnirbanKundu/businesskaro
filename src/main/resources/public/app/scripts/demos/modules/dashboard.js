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
    $scope.selectedIndustries = { "selected": [] };
    $scope.selectedStates = { "selected": [] };
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
      industry = $scope.selectedIndustries.selected.industryName,
      state = $scope.selectedStates.selected.stateName;

      if(industry){
        keywords = industry+',';
      }
      if(state){
        keywords += state + ',';
      }      
      keywords = keywords.substring(0, keywords.lastIndexOf(','));
      $timeout(function(){
        $location.path('/search/ALL/'+keywords);
      },100)
      
      //console.log('Tags selected are:', $scope.selectedTag);
    }
    
    $scope.contactusForm={};

    $scope.resetValidationForm = function($event){
      $event.preventDefault();
      $scope.contactusForm = {};
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
    $scope.industries = [];
    $http.get('utilservices/industries').success(function(response) {
      $scope.industries = response;
    });

    //CarouselController
    $scope.myInterval = 5000;
    var slides = $scope.slides = [];
    var images = ['crusal1.jpg', 'crusal2.jpg', 'crusal3.jpg', 'crusal4.jpg', 'crusal5.jpg'];
    var title = ['First', 'Second', 'Third','Fourth','Fifth'];  
    $scope.addSlide = function() {
      slides.push({
        image: '../../assets/img/' + images[slides.length],
        text: title[slides.length]
      });
    };
    for (var i = 0; i < 5; i++) {
      $scope.addSlide();
    }
    
  }]);