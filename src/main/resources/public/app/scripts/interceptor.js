angular.module('theme.core.services')
.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push(['$q','$window','$location', function ($q,$window,$location) {
        return {
            // optional method
            'request': function (config) {
                // do something before request
                if($window.localStorage && $window.localStorage['bk_userInfo']){
                    config.headers['SECURE_TOKEN'] = JSON.parse($window.localStorage['bk_userInfo']).secureToken;
                    config.headers['CLIENT_ID'] = JSON.parse($window.localStorage['bk_userInfo']).clientId;
                }
                return config;
            },
            // optional method
            'requestError': function (rejection) {
                //handle error
                return $q.reject(rejection);
            },
            // optional method
            'response': function (response) {
                // do something on success
                return response;
            },
            // (optional) Redirect user to login page when unauthorized (401)
            // If you want to allow 401's, you can remove this method.
            'responseError': function (rejection) {
                if (rejection.status === 401) {
                    //Handle Errors
                }
                else if (rejection.status === 400){
                    if(rejection.data.type=='USER_AUTH_FAIL'){
                        $location.path('/login');
                        if($window.localStorage && $window.localStorage['bk_userInfo']){
                            var SECURE_TOKEN = JSON.parse($window.localStorage['bk_userInfo']).secureToken;
                            var CLIENT_ID = JSON.parse($window.localStorage['bk_userInfo']).clientId;
                            $.ajax({
                                type:"POST",
                                headers: { 'SECURE_TOKEN': SECURE_TOKEN, 'CLIENT_ID': CLIENT_ID},
                                url: "/services/logout",
                                success: function() {
                                    $window.localStorage.removeItem('bk_userInfo');
                                    $location.path('/login'); 
                                },
                                error : function(){
                                    $window.localStorage.removeItem('bk_userInfo');
                                    $location.path('/login');
                                }
                            });
                        }  
                    }
                    console.log('Error');
                }
                // handle error
                return $q.reject(rejection);
            }
        };
    }]);
}]);

