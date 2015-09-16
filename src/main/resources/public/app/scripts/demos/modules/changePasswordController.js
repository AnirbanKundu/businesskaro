angular
  .module('theme.demos.chngpwd', [])
  .controller('ChangePasswordController', ['$scope', '$http', '$window', '$location', function($scope,$http,$window,$location){

  	  $scope.chngPassword = function(){
  		  console.log('In Change Password');
  		  console.log($scope.oldPassword);
  		  console.log($scope.newPassword);
  		  console.log($scope.cNewpassword);
  		$http({
            url: 'services/changePassword',
            method: 'POST',
            data: {
          	  "email":$scope.email,
          	  "password":$scope.oldPassword,
          	  "newPassword":$scope.newPassword
            }
          }).then(function(response){
        	  $window.location.href = '/#/login';
          });
  	  }
  }]);