(function () {
  'use strict';

  angular
    .module('frontend', ['ngAnimate', 'ngCookies', 'ngTouch', 'ngSanitize', 'ngMessages',
      'ngAria', 'ui.router', 'ui.bootstrap', 'toastr', 'ngMask', 'ngDialog', 'plotly', 'rzModule', "ngFlash"])
    .factory("UtilsFunctionsFactory", UtilsFunctionsFactory);

  /** @ngInject */
  function UtilsFunctionsFactory(Flash) {
    return {
      toDate: function (value) {
        var dateValue = new Date(value);
        var dd = dateValue.getDate();
        var mm = dateValue.getMonth() + 1; //January is 0!

        var yyyy = dateValue.getFullYear();
        if (dd < 10) {
          dd = '0' + dd;
        }
        if (mm < 10) {
          mm = '0' + mm;
        }
        var formattedDate = dd + '.' + mm + '.' + yyyy;
        return formattedDate;
        // moment.lang('ru');
        // return moment(value).format('dddd, MMMM DD YYYY')
      },
      showFlashMessage: function (mesType, text) {
        return Flash.create(mesType, text, 30000, {}, true);
      }
    }


  }
})();
