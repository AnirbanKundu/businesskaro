angular
  .module('theme.demos.myuser', ['ui.bootstrap'])
  .controller('UserManagementController', ['$rootScope','$scope', '$http', '$location','$modal','$log', function ($rootScope, $scope, $http, $location,$modal,$log) {

      $scope.waiting = true;
      $http.get('/manageUsers').success(function (response) {
          $scope.users = response;         
          $scope.waiting = false;
      }).error(function () {
          $scope.users = [];
          $scope.waiting = false;
      });

      /* this code accept user and display on modal */
      
     $scope.editUser=function(user){
       var modalInstance = $modal.open({
            animation: true,
            templateUrl: 'views/edituser.html',
            controller: 'EditUserController',
            scope: $scope,
            resolve: {
              existinguser: function () {
                return user;
              }
            }
       });//end open      
     }//end editUser
}]);


angular
.module('theme.demos.myuser')
.controller('EditUserController',['$rootScope', '$scope','existinguser','$location','$modalInstance', '$http', function ($rootScope, $scope, existinguser,$location,$modalInstance,$http) {
    // $scope.user = {
    //   usrId : existinguser.usrId,
    //   usrName : existinguser.usrName,
    //   usrEmail : existinguser.usrEmail,
    //   usrType: existinguser.usrType 
    // };
    $scope.user = existinguser;
    $scope.saveUser = function(user){
      $http({
        url: '/manageUsers',
        method: 'POST',
        data: user,
        cache : false
      }).then(function(response){           
        $scope.message = 'success';
        $scope.alert = { type: 'success', msg: '<strong>User role</strong> changed successfully.'};
      },function(error){
        $scope.alert = { type: 'alert', msg: '<strong>User role</strong> was not updated. Try again.'};
      });
    }

    $scope.close=function()
    {
       $modalInstance.dismiss('cancel');
    }

}]);




//Email Management

angular
.module('theme.demos.myuser', ['ui.bootstrap'])
.controller('EmailManagementController', ['$rootScope','$scope', '$http', '$location','$modal','$log', function ($rootScope, $scope, $http, $location,$modal,$log) {

    $scope.waiting = false;
    $scope.getEntityEmails = function(){
       $scope.waiting = true;
      $http.get('/manageEmail/'+$scope.selectedEntityType).success(function (response) {
        $scope.emails = response;         
        $scope.waiting = false;
      }).error(function () {
          $scope.emails = [];
          $scope.waiting = false;
      });
    }
   

    /* this code accept user and display on modal */
    
   /*$scope.editUser=function(user){
     var modalInstance = $modal.open({
          animation: true,
          templateUrl: 'views/edituser.html',
          controller: 'EditUserController',
          scope: $scope,
          resolve: {
            existinguser: function () {
              return user;
            }
          }
     });*/
         //end open      
   //}//end editUser
}]);
