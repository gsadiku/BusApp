/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


let busLine = {
    lineArray: null,
    init:async function(value){
        this.clear();
        $("#simple_list_data").append(`<div><div><span></span>Loading...<span></span></div></div>`);
        await $.ajax({
            type:"GET",
            contentType: "application/json",
            url:"/api/linedata?top=" + value,
            success: function (data, textStatus, jqXHR) {
               busLine.lineArray = data.value;
               busLine.clear();
               let counter = 0;
               for(let line of busLine.lineArray){
                   $("#simple_list_data").append(`
                    <div id="${counter}" onclick="busLine.click(this,event)" data-populated="false">
                        <div><span>Bus Line: ${line.lineNumber}</span><span>Total Stops: ${line.stopAreaList.length}</span></div>
                    </div>
                    `);
                    counter++;
               }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $("#simple_list_data").append(`<div><div>Failed to fetch data!</div></div>`);
            }
        });
    },
    click:function(object, event){
        let index = Number.parseInt($(object).attr("id"));
        stopAreaArray = busLine.lineArray[index].stopAreaList;
        if($(object).attr("data-populated") === "false"){
            for(let stop of stopAreaArray){
                $(object).append(`
                    <div class="stopArea"><span>${stop.stopPointName}</span><span>Stop Point: ${stop.stopPointNumber}</span></div>
                `);
            }
            $(object).attr("data-populated","true");
        }
        else{
            $(object).children().not(":first-child").remove();
            $(object).attr("data-populated","false");
        }  
    },
    clear:function(){
        $("#simple_list_data").empty();
    },
    go:function(){
        let inputValue = Number.parseInt($("#filter_input").val());
        if(isNaN(inputValue)){
            return;
        }
        busLine.init(inputValue);
    }
};