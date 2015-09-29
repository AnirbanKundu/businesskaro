angular
  .module('theme.demos.chngpwd', [])
  .controller('ChangePasswordController', ['$scope', '$http', '$window', '$location', function($scope,$http,$window,$location){
      $scope.message = '';
      $scope.alert = { type: 'success', msg: '<strong>Password</strong> changed successfully.'};

  	  $scope.chngPassword = function(){
  		  console.log('In Change Password');
  		  console.log($scope.oldPassword);
  		  console.log($scope.newPassword);
  		  console.log($scope.cNewpassword);
        if($scope.newPassword!==$scope.cNewpassword){
          $scope.message = 'error';
          $scope.alert = { type: 'danger', msg: '<strong>Passwords</strong> do not match.'};
          return false;
        }

  		$http({
            url: 'services/changePassword',
            method: 'POST',
            data: {
          	  "email":$scope.email,
          	  "password":$scope.oldPassword,
          	  "newPassword":$scope.newPassword
            }
          }).then(function(response){
        	  //$window.location.href = '/#/login';
            $scope.message = 'success';
            $scope.alert = { type: 'success', msg: '<strong>Password</strong> changed successfully.'};

          },function(error){
            $scope.alert = { type: 'alert', msg: '<strong>Password</strong> was not updated. Try again.'};
          });
  	  }
  }]);