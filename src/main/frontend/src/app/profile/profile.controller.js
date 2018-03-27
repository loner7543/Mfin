(function () {
  angular
    .module('frontend')
    .controller('ProfileController', ProfileController);

  /** @ngInject */
  function ProfileController($scope, $http,UtilsFunctionsFactory,files) {
    $scope.priceSlider = 150;
    $scope.filenames=files.data;
    var vm = this;

    $scope.profileParams = {
      fileName: "",
      mode:""
    };

    $scope.calcProfile=function () {
      console.log($scope.profileParams.fileName)
      $http({
        method: "POST",
        url: "http://localhost:8080/krugstat/rest/calculateProfile",
        params:$scope.profileParams
      }).then(function (resp) {
          console.log("Профиль", resp);
          $scope.profile=resp.data;
        },
        function (result) {
          UtilsFunctionsFactory.
          console.error(result, result.data);
        });
    };

    $scope.calculateCruglogramme = function () {
      console.log($scope.profileParams.mode);
      $http({
        method: "POST",
        url: "http://localhost:8080/krugstat/rest/calculateCluglogramme",
        params:$scope.profileParams
      }).then(function (resp) {
          console.log("высоты круглограммы", resp);
          $scope.cruglogrammeHeights=resp.data;
          $scope.drawCircle();
        },
        function (result) {
          UtilsFunctionsFactory.showFlashMessage('danger',"Высоты не расчитаны");
        });
    };

    $scope.uploadCruglogramme = function () {
    };

    $scope.drawCircle = function () {
      var canvas = document.getElementById("circleCanvas");
      var ctx = canvas.getContext("2d");
      debugger;
      //рисует пунктиром ось ОХ
      ctx.beginPath();
      ctx.setLineDash([5,15]);
      ctx.moveTo(0,canvas.height/2);
      ctx.lineTo(canvas.width,canvas.height/2);

      //Рисует пунктиром ось ОY
      ctx.moveTo(canvas.width/2,0);
      ctx.lineTo(canvas.width/2,canvas.height);
      ctx.stroke();
      console.log("Done!");


      // $http({
      //   method: "POST",
      //   url: "http://localhost:8080/krugstat/rest/getCirclePoints",
      //   params:{
      //     radius:1
      //   }
      // }).then(function (resp) {
      //     console.log("Точки", resp);
      //     $scope.cruglogrammeHeights=resp.data;
      //
      //   },
      //   function (result) {
      //     console.error(result, result.data);
      //   });
    };
  }
})();
