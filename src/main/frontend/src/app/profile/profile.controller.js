(function () {
  angular
    .module('frontend')
    .controller('ProfileController', ProfileController);

  /** @ngInject */
  function ProfileController($scope, $http,UtilsFunctionsFactory,files, connectionService) {
    $scope.deflectionSlider = 100;
    $scope.sliderOptions = {
      floor: 0,
      step:1,
      id:"scaleSlider"
      // onChange:function (value) {
      //   console.log(value);
      // }
    };
    $scope.filenames=files.data;
    var vm = this;

    $scope.profileParams = {
      fileName: "",
      mode:""
    };

    $scope.deflectionArray = new Array(360);
    $scope.scale=0.0;

    $scope.calcProfile=function () {
      console.log($scope.profileParams.fileName)
      $http({
        method: "POST",
        url: connectionService.getUrl()+"/calculateProfile",
        params:$scope.profileParams
      }).then(function (resp) {
          console.log("Профиль", resp);
          $scope.profile=resp.data;
        },
        function (result) {
          UtilsFunctionsFactory.showFlashMessage('danger',"Профиль не расчитан");
          console.error(result, result.data);
        });
    };

    $scope.calculateCruglogramme = function () {
      console.log($scope.profileParams.mode);
      $http({
        method: "POST",
        url: connectionService.getUrl()+"/calculateCluglogramme",
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
      drawAxis(ctx,canvas);

      //окружность
      var minSize = 0;
      if (canvas.height>canvas.width){
        minSize = canvas.width-1;
      }
      else minSize = canvas.height-1;

      ctx.beginPath();
      ctx.setLineDash([]);
      var radius = scalling(minSize/2,canvas);
      ctx.arc(canvas.width/2,canvas.height/2,radius,0,360);
      ctx.stroke();

      ctx.beginPath();
      drawDeflection(canvas,ctx,$scope.deflectionArray);
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

     function scalling (radius,canvas) {
      var maxHeight = 0;
      var scale=canvas.width/4;
      for (var i = 0;i<$scope.cruglogrammeHeights.length;i++){
        $scope.deflectionArray.push($scope.cruglogrammeHeights[i]*scale);
        if (maxHeight< $scope.deflectionArray[i]){
          maxHeight=$scope.deflectionArray[i];
        }
      }
      return (radius-Math.round(maxHeight))-2;
     }

     function drawDeflection(context,canvas,deflectionArray) {

     }

     function drawAxis(ctx,canvas) {
       ctx.beginPath();
       ctx.setLineDash([5,15]);
       ctx.moveTo(0,canvas.height/2);
       ctx.lineTo(canvas.width,canvas.height/2);

       //Рисует пунктиром ось ОY
       ctx.moveTo(canvas.width/2,0);
       ctx.lineTo(canvas.width/2,canvas.height);
       ctx.stroke();
     }
  }
})();
