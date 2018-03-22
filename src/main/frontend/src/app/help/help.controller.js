(function () {

  angular
    .module('frontend')
    .controller('HelpController', HelppController);

  /** @ngInject */
  function HelppController($scope, $http) {
    var vm = this;
    $scope.amountDiscount="";
    $scope.sendRequest = function () {
      var promise = $http.get("../../data/discounts.json");
      promise.then(fulfilled, rejected)
    };

    function fulfilled(resp) {
      console.log(resp.data);
    }

    function rejected(errror) {
      console.log(errror)
    }
  }
})();
