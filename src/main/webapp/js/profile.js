// var calcProfileURL = "calculateProfile";
// var calcCruglogrammeUrl = "calculateCluglogramme";
//
// window.onload = function () {
//     var paramValue = window.location.href.split("?")[1].split("=")[1];
//     var fileLabel = document.getElementById("mainProfileLabel");
//     fileLabel.innerText = fileLabel.innerText+paramValue;
//     console.log(paramValue);
//     calculateProfile(paramValue);
// };
// function calculateProfile(profileFileName) {
//     var data = {
//         fileName:profileFileName
//     };
//     $.ajax({
//         url: calcProfileURL,
//         data: JSON.stringify(data),
//         type: 'POST',
//         //dataType: "json",
//         contentType: 'application/json', //charset=utf-8,
//         success: function (json, textStatus, jqXHR) {
//             if(textStatus!=200){// todo почему
//                 console.log(json);
//                 var radius = document.getElementById("radius");
//                 var delta = document.getElementById("delta");
//                 var zmin = document.getElementById("zmin");
//                 var step = document.getElementById("step");
//                 var points = document.getElementById("points");
//                 var countLabel = document.getElementById("count");
//
//                 radius.innerText = radius.innerText+json.surfaceRadius;
//                 delta.innerText = delta.innerText+json.emitterYOffset;
//                 zmin.innerText = zmin.innerText+json.initialEmitterHeightPosition;
//                 step.innerText = step.innerText+json.emitterHeightStep;
//                 points.innerText = points.innerText+json.discretePoints;
//                 countLabel.innerText = countLabel.innerText+json.radialProfilesNumber;
//             }
//         },
//         error:function (jqXHR, textStatus, errorThrown) {
//             alert(errorThrown);
//         }
//     })
// }

// function calculateCruglogramm() {
//     var checked;
//     var rButtons = document.getElementsByName('settings');
//     for (var i = 0; i < rButtons.length; i++) {
//         if (rButtons[i].type == "radio" && rButtons[i].checked) {
//             checked = rButtons[i].value;
//         }
//     }
//         var data = {
//             fileName: "BEM_120.DAT",// todo брать его с поля ввода сверху
//             mode:checked
//         };
//         $.ajax({
//             url: calcCruglogrammeUrl,
//             data: JSON.stringify(data),
//             type: 'POST',
//             //dataType: "json",
//             contentType: 'application/json', //charset=utf-8,
//             success: function (json, textStatus, jqXHR) {
//                 if (textStatus != 200) {// todo почему
//                     var heightArray = JSON.parse(json);
//                     console.log(heightArray);
//                     var hList = document.getElementById("cruglogrammeResult");
//                     $(hList).empty();
//                     for(var i = 0;i<heightArray.length;i++){
//                         var li = document.createElement("li");
//                         li.appendChild(document.createTextNode(i+1+": "+heightArray[i]));
//                         hList.appendChild(li);
//                     }
//                 }
//             },
//             error: function (jqXHR, textStatus, errorThrown) {
//                 alert(errorThrown);
//             }
//         })
// }