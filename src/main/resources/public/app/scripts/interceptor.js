angular.module('themesApp')
.config(['$httpProvider', function ($httpProvider) {
    //$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    /*
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
    $httpProvider.defaults.headers.common['Accept'] = 'application/json';
    $httpProvider.defaults.headers.common['Content-Type'] = 'application/json';
    */
    /*
     * Application http interceptor configuration
     * If you are using Siteminder, this interceptor can be used to capture the session timeout on an AJAX request.
     * You can implement your conditions in this interceptor according to your own requirement.
     */
    $httpProvider.interceptors.push(['$q', function ($q) {
        return {
            // optional method
            'request': function (config) {
                // do something before request
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
                    LogoutService.hardLogout();
                }
                // handle error
                return $q.reject(rejection);
            }
        };
    }]);
}]);

