angular.module('theme.core.services')
  .factory("EntityService", ['$http','$q',function($http, $q) {
  'use strict';

  var _getEntityData = function(options){
    var deferred = $q.defer(), data = {},url='';
    if(options.entityType==='USER_PROFILE'){
      url = 'services/userProfile/summary?userId='+options.entityId;
    }
    else if(options.entityType==='OFFER'){
      url = '/services/offer/summary/'+options.entityId;
    }
    else if(options.entityType==='REQUEST'){
      url = '/services/request/summary/'+options.entityId;
    }
    else{
      //NEWS
    }
    /*
    if(options.url ==='user'){
      data = {
        "userId": 42,
        "firstName": "Anirban",
        "lastName": "Kundu",
        "stateName": "West Bengal",
        "cityName": "Calcutta",
        "aboutMe": "software devloper",
        "userType": "P",
        "companyUrl": "",
        "imageUrl": "http://res.cloudinary.com/difyxhuza/image/upload/v1441264302/application_images/bk_banner_background.jpg",
        "userSkills": [
          1
        ],
        "industrys": [
          2,
          3
        ],
        "lookinfForSkill": [
          0,
          1
        ],
        "offeredServices": null,
        "updatedDate": "2015-08-24",
        "createdDate": "2015-08-24"
      }
    };

    deferred.resolve(data);
    */

    $http({
        url: url,
        method: 'GET'
      }).then(function(response){    
        var data = response.data
        deferred.resolve(data);
      },function(error){
        deferred.reject(error);
      });   
    
      return deferred.promise;
    };
  return {
    getEntityData: _getEntityData
  };
}]);