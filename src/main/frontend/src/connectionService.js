(function () {
  'use strict';

  angular.module('frontend')
    .factory('connectionService', function ($location) {
      var connectionService = {
        getUrl: function () {
          //return $location.protocol() + '://' + $location.host() +':'+ $location.port() + '/krugstat/rest';
          return 'http://localhost:8080/krugstat/rest';
        }
      };

      return connectionService;
    });
})();
