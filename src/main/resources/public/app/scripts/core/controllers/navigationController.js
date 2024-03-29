angular
  .module('theme.core.navigation_controller', ['theme.core.services'])
  .controller('NavigationController', ['$scope', '$location', '$timeout', function($scope, $location, $timeout) {
    'use strict';
    $scope.menu = [{
      label: 'Overview',
      iconClasses: '',
      separator: true
    }, {
      label: 'Dashboard',
      iconClasses: 'glyphicon glyphicon-home',
      url: '#/'
    },
    {
      label: 'Policy',
      iconClasses: 'glyphicon glyphicon-file',
      url:'#/search/GOVT_POLICY/IT%20Software,West%20Bengal'
    },
    /*
    {
      label: 'Business News',
      iconClasses: 'fa fa-newspaper-o',
      url: '#/businessnews'
    },
    */
    {
      label: 'Filter',
      iconClasses: '',
      separator: true
    }, {
      label: 'Search Users',
      iconClasses: 'fa fa-users',
      url: '#/search/user_profile/IT%20Software,West%20Bengal'
    }, /*{
      label: 'Search Entrepreneur',
      iconClasses: 'fa fa-users',
      url: '#/search/Entrepreneur/IT%20Software,West%20Bengal'
    },*/ {
      label: 'Search Offers',
      iconClasses: 'glyphicon glyphicon-search',
      url: '#/search/Offer/IT%20Software,West%20Bengal'
    }, {
      label: 'Search Request',
      iconClasses: 'glyphicon glyphicon-search',
      url: '#/search/Request/IT%20Software,West%20Bengal'
    }, {
      label: 'Create',
      iconClasses: 'fa fa-home',
      separator: true
    }, {
      label: 'Create Offer',
      iconClasses: 'glyphicon glyphicon-random',
      url: '#/createoffer'
    }, {
      label: 'Create Request',
      iconClasses: 'glyphicon glyphicon-cog',
      url: '#/createrequest'
    },
    {
      label: 'Resources',
      iconClasses: 'fa fa-home',
      separator: true
    },
    {
      label: 'Knowledge Base',
      iconClasses: 'glyphicon glyphicon-tasks',
      html: '<span class="label label-danger">TBD</span>'
    },
    {
      label: 'Trainings',
      iconClasses: 'glyphicon glyphicon-share',
      html: '<span class="label label-danger">TBD</span>'
    }
    ];


    var setParent = function(children, parent) {
      angular.forEach(children, function(child) {
        child.parent = parent;
        if (child.children !== undefined) {
          setParent(child.children, child);
        }
      });
    };

    $scope.findItemByUrl = function(children, url) {
      for (var i = 0, length = children.length; i < length; i++) {
        if (children[i].url && children[i].url.replace('#', '') === url) {
          return children[i];
        }
        if (children[i].children !== undefined) {
          var item = $scope.findItemByUrl(children[i].children, url);
          if (item) {
            return item;
          }
        }
      }
    };

    setParent($scope.menu, null);

    $scope.openItems = []; $scope.selectedItems = []; $scope.selectedFromNavMenu = false;

    $scope.select = function(item) {
      // close open nodes
      if (item.open) {
        item.open = false;
        return;
      }
      for (var i = $scope.openItems.length - 1; i >= 0; i--) {
        $scope.openItems[i].open = false;
      }
      $scope.openItems = [];
      var parentRef = item;
      while (parentRef !== null) {
        parentRef.open = true;
        $scope.openItems.push(parentRef);
        parentRef = parentRef.parent;
      }

      // handle leaf nodes
      if (!item.children || (item.children && item.children.length < 1)) {
        $scope.selectedFromNavMenu = true;
        for (var j = $scope.selectedItems.length - 1; j >= 0; j--) {
          $scope.selectedItems[j].selected = false;
        }
        $scope.selectedItems = [];
        parentRef = item;
        while (parentRef !== null) {
          parentRef.selected = true;
          $scope.selectedItems.push(parentRef);
          parentRef = parentRef.parent;
        }
      }
    };

    $scope.highlightedItems = [];
    var highlight = function(item) {
      var parentRef = item;
      while (parentRef !== null) {
        if (parentRef.selected) {
          parentRef = null;
          continue;
        }
        parentRef.selected = true;
        $scope.highlightedItems.push(parentRef);
        parentRef = parentRef.parent;
      }
    };

    var highlightItems = function(children, query) {
      angular.forEach(children, function(child) {
        if (child.label.toLowerCase().indexOf(query) > -1) {
          highlight(child);
        }
        if (child.children !== undefined) {
          highlightItems(child.children, query);
        }
      });
    };

    // $scope.searchQuery = '';
    $scope.$watch('searchQuery', function(newVal, oldVal) {
      var currentPath = '#' + $location.path();
      if (newVal === '') {
        for (var i = $scope.highlightedItems.length - 1; i >= 0; i--) {
          if ($scope.selectedItems.indexOf($scope.highlightedItems[i]) < 0) {
            if ($scope.highlightedItems[i] && $scope.highlightedItems[i] !== currentPath) {
              $scope.highlightedItems[i].selected = false;
            }
          }
        }
        $scope.highlightedItems = [];
      } else
      if (newVal !== oldVal) {
        for (var j = $scope.highlightedItems.length - 1; j >= 0; j--) {
          if ($scope.selectedItems.indexOf($scope.highlightedItems[j]) < 0) {
            $scope.highlightedItems[j].selected = false;
          }
        }
        $scope.highlightedItems = [];
        highlightItems($scope.menu, newVal.toLowerCase());
      }
    });

    $scope.$on('$routeChangeSuccess', function() {
      if ($scope.selectedFromNavMenu === false) {
        var item = $scope.findItemByUrl($scope.menu, $location.path());
        if (item) {
          $timeout(function() {
            $scope.select(item);
          });
        }
      }
      $scope.selectedFromNavMenu = false;
      $scope.searchQuery = '';
    });
  }]);