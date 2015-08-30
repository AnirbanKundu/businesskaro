angular.module('theme.core.services')
  .factory("EntityService", ['$http','$q',function($http, $q) {
  'use strict';

  var _getEntityData = function(options){
    var deferred = $q.defer(), data = {};
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
        "imageUrl": "http://res.cloudinary.com/difyxhuza/image/upload/t_media_lib_thumb/v1440066492/voxfgxb18hlslx0t6j2a.png",
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

    /*$http({
        url: options.url,
        method: 'GET'
      }).then(function(response){    
        var 
        deferred.resolve(ageGroup);
      },function(error){
        deferred.reject(error);
      });   
    */
      return deferred.promise;
    };
  return {
    getEntityData: _getEntityData
  };
}]);