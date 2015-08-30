/* global angular, _ */
'use strict';

/**
 * This Service deals with setting/getting application state
 * by getting and setting URL query parameters.
 */
angular.module('StateService', [])
  .service('$state', function($rootScope, $location) {

    // For use inside functions
    var self = this;

    /**
     * Given a URL string, this function will return a
     * deserialized object containing the query parameters.
     * NOTE: All values are ARRAYS OF STRINGS in our state service,
     *       and multiple values separated by commas in the URL.
     * @param  {string} url The URL
     * @return {object}     The deserialized query paramters.
     */
    var getStateFromUrl = function(url) {
      var state = {};
      var query = url.split('?')[1];
      if(query){
        var vars = query.split('&');
        for (var i=0;i<vars.length;i++) {
          var pair = vars[i].split('=');
          pair[0] = decodeURIComponent(pair[0]); // key
          pair[1] = decodeURIComponent(pair[1]); // value
          // All values are arrays in our state service.
          pair[1] = pair[1].split(',');
          // If first entry with this name
          if (typeof state[pair[0]] === 'undefined') {
            state[pair[0]] = pair[1];
            // If second entry with this name
          } else if (typeof state[pair[0]] === 'string') {
            state[pair[0]] = _.unique( _.union( state[pair[0]], pair[1] ) );
          }
        }
      }
      return state;
    };

    /**
     * Broadcast a 'StateChanged' message with the new and old states
     * downward from $rootScope any time the URL changes.
     * @return {event, newState, oldState} The event and new and old states
     */
    $rootScope.$on('$locationChangeSuccess', function(event, newUrl, oldUrl){
      $rootScope.$broadcast('StateChanged', getStateFromUrl(newUrl), getStateFromUrl(oldUrl));
    });

    /**
     * Given a query parameter, this function will return the values
     * as an array of strings. If the parameter is not set, the function
     * will return an empty array.
     * @param  {string} param   The name of the query parameter
     * @return {array}          An array of zero or more strings
     */
    this.get = function(param){
      var q = $location.search()[param] || [];
      if(typeof(q) === 'string'){
        q = q.split(',');
      }
      q = _.unique(q);
      return q;
    };

    /**
     * Given a query parameter, a value and an action ('add', 'remove' or 'replace')
     * this function will update the URL to add/remove/replace the value to/from/in the query parameter.
     * If the action is 'add' and the query parameter doesn't exist, it will be added.
     * If the action is 'replace', the current value will be replaced. If the query parameter
     * doesn't exist, it will be added.
     * If the action is 'remove' and the query parameter is empty after the value is removed,
     * the query paramter will be removed.
     * @param {string} param  The name of the query parameter.
     * @param {string} value  The value to be added/removed/replaced
     * @param {string} action The action, either 'add' or 'remove'
     * @return {object}       The State object
     *
     */
    this.set = function(param, value, action){
      if(value){
        var q = $location.search()[param] || [];
        //  Note the trick here: when a page is first loaded, the tags parameter
        //  is a string (or undefined). We want an array, so we can push and pop elements.
        //  So if it's a comma-separated string, turn it into an array.
        if(typeof(q) === 'string'){
          q = q.split(',');
        }
        _.each(q, function(v){
          v = v.toString();
        });
        q = _.unique(q);
        switch(action){
        case 'add':
          if(_.indexOf(q, value) === -1){
            q.push(value);
            // Note: arrays in query params are comma-separated strings,
            // so join() our array before setting it.
            q = q.join();
            $location.search(param,q);
          }
          break;
        case 'replace':
          // Note: arrays in query params are comma-separated strings,
          // so join() our array before setting it.
          if(typeof(value) === 'object'){
            value = value.join();
          }
          $location.search(param, value.toString());
          break;
        case 'remove':
          if(_.indexOf(q, value) !== -1){
            q = _.without(q, value);
            if(q.length > 0){
              // console.log('remove',q,'from',param);
              $location.search(param,q);
            } else {
              // console.log(param,'is empty, removing');
              $location.search(param, null);
            }
          }
          break;
        }
      } else {
        // console.log('State.set error:', param,value,action);
      }

      return this; // support chaining
    };

    /**
     * Given an array of zero or more strings, this function will
     * remove query parameters matching those strings from the URL.
     * @param  {array} params An array of strings
     * @return {object}       The State object
     */
    this.unset = function(params){
      // console.log('unset', params);
      _.each(params, function(param){
        $location.search(param, null);
      });
      return this; // support chaining
    };

    /**
     * Given a query parameter and an value, this function returns true
     * if the current $location has that value for that query parameter,
     * otherwise it returns false.
     *
     * TODO: If no value is provided, it returns true if any value is set
     * for that query parameter.
     *
     * NOTE: This assumes that arrays are represented in query values as
     * comma-separated lists, like this:
     *
     * ?foo=bar,baz,wibble
     *
     * In that case, isset('foo','baz') would return true.
     *
     * @param  {string} param The name of the query parameter
     * @param  {string} value (optional) The value of the query parameter
     * @return {bool}
     */
    this.isset = function(param, value){
      var q = $location.search()[param] || [];
      if(typeof(q) === 'string'){
        q = q.split(',');
      }
      // If the user has provided a 'value', check to see if that param
      // is set to that value.
      if(value){
        if(_.indexOf(q, value) !== -1){
          return true;
        } else {
          return false;
        }
      } else {
        // Otherwise, check to see if that param is set at all.
        var keys = _.keys($location.search());
        if(_.contains(keys,param)){
          return true;
        } else {
          return false;
        }
      }

    };

  });
