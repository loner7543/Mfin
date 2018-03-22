(function () {
  'use strict';

  angular
    .module('frontend')
    .config(routerConfig);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider) {
     $stateProvider
       .state('statistics', {
         url: '/statistics',
         views: {
           'index': {
             templateUrl: 'app/statistics/statistics.html',
             controller: 'StatisticsController',
             controllerAs: 'itemCtrl'
           }
         },
         resolve: {
         }
       }) .state('help', {
       url: '/help',
       views: {
         'index': {
           templateUrl: 'app/help/help.html',
           controller: 'HelpController',
           controllerAs: 'helpCtrl'
         }
       },
       resolve: {
       }
     })
       .state('login', {
         url: '/login',
         views: {
           'index': {
             templateUrl: 'app/login/login.html',
             controller: 'LoginController',
             controllerAs: 'loginCtrl'
           }
         },
         resolve: {
         }
       })
       .state('profile', {
       url: '/profile',
       views: {
         'index': {
           templateUrl: 'app/profile/profile.html',
           controller: 'ProfileController',
           controllerAs: 'profileCtrl'
         }
       },
       resolve: {
         files:function ($http) {
         return $http({
           method: "GET",
           url: "http://localhost:8080/krugstat/rest/getFileNames",
           params:{}
         });
       }}
     });


    $urlRouterProvider.otherwise(function ($injector, $location) {
      console.log($injector, $location);
      return "/login";
    });
  }

})();
