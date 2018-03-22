var url = "save";
var getStstUrl = "calculate";

function onCalculateProfile() {
    document.location.href = "http://localhost:8081/krugstat/Profile?filename=BEM_120.DAT";
}

function onSubmit(form) {
    console.log("submitting files....");
    console.log(form);
    var fileNamesList = document.getElementById("fileNamesList");
    var files = form.children.item(0).files;
    console.log(files);
    $(fileNamesList).empty();
    for(var i = 0;i<files.length;i++){
        var li = document.createElement("li");
        li.appendChild(document.createTextNode(i+1+": "+files[i].name));
        fileNamesList.appendChild(li);
    }
    form.submit();

}
function sendToServer() {
    var data = new FormData();
    var formFiles = document.getElementById("my_file");
    var files = formFiles.files;
    debugger;
    // for (var i = 0;i<files.length;i++){
    //     data.append('file-'+i, files[i]);
    // }
    $.ajax({
        url: 'save',
        data: files,
        cache: false,
        contentType: false,
        processData: false,
        type: 'POST',
        success: function(data){
            alert('Файл успешно отправлен на сервер');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });

}

function UploadFile() {
    var files = document.getElementById("my_file").files;//массив файлов тут
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.send(files);
}

function calculateStatistics() {
    $.ajax({
        url: getStstUrl,
        data: "",
        type: 'POST',
        //dataType: "json",
        contentType: 'application/json', //charset=utf-8,
        success: function (data, textStatus, jqXHR) {
            if (jqXHR.status == 200) {
                console.log(data);
                var sko = document.getElementById("sko");
                var psist = document.getElementById("psist");
                var psum = document.getElementById("psum");
                var dov = document.getElementById("dov");
                var PinPoint = document.getElementById("PinPoint");
                var opt = document.getElementById("opt");
                var kxLabel = document.getElementById("kxSqare");
                var mediumLabel = document.getElementById("mediumLabel");

                sko.innerText = sko.innerText+data.sko;
                psist.innerText = psist.innerText+data.syst;
                psum.innerText = psum.innerText+data.psum;
                dov.innerText = dov.innerText+data.dov;
                PinPoint.innerText = PinPoint.innerText+data.point;
                opt.innerText = opt.innerText+data.closest;
                mediumLabel.innerText = mediumLabel.innerText+data.mediumValue;
                var sqareValue = data.levelTovalue;
                if(sqareValue!=-1){
                    kxLabel.innerText = kxLabel.innerText+"Гипотеза о нормальном распределении верна, с уровнем значимости "+data.levelTovalue;
                }
                else {kxLabel.innerText = kxLabel.innerText+"Гипотеза о нормальном распределении не верна";}

                var amplitudes = data.amplitudes;
                var xLabelData = 1;
                var xLabelValues =[];
                for(var i = 0;i<amplitudes.length;i++){
                    xLabelValues.push(''+xLabelData);
                    xLabelData++;
                }
                var layout = {
                    title: 'Гистограмма амплитуд',
                    showlegend: true,
                    xaxis: {
                        tickangle: -45
                    },
                    yaxis: {
                        zeroline: false,
                        gridwidth: 2
                    },
                    bargap :0.05

                };
                var graphData = [
                    {
                        x: xLabelValues,
                        y:amplitudes,
                        type: 'bar',
                        name:'Амплитуды'
                    }
                ];
                Plotly.newPlot('amplitudesHistogram', graphData,layout);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(errorThrown);
        }

    });
}