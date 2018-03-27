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
      var Xpoints = [];
      Xpoints.push({
        x:0,
        y:canvas.height/2
      });
      for (var i = 0;i<canvas.width;i++){
        if (i%3===0){
          Xpoints.push({
            x:i,
            y:canvas.height/2
          })
        }
      }

      var Ypoints = [];
      Ypoints.push({
        x:canvas.width/2,
        y:0
      });
      for (var j = 0;i<canvas.height;j++){
        if (j%3===0){
          Ypoints.push({
            x:canvas.width/2,
            y:j
          })
        }
      }
      //draw axis
      ctx.beginPath();
      for(var k = 1; k<Xpoints.length; k++)
      {
        if((k%2)!==0){
          ctx.moveTo(Xpoints[k-1].x,Xpoints[k-1].y);
          ctx.lineTo(Xpoints[k].x,Xpoints[k].y);
        }
      }
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
