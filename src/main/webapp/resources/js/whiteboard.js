/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */




function defineComment(content,instructionId,ownerId) {
window.alert(instructionId);
    var json = JSON.stringify({
        "content": content,
        "instructionId": instructionId,
        "ownerId":ownerId
    });
    drawComment(json);
    console.log("drawImageText");
    sendText(json);

}

function drawComment(image) {//TODO:comparing instructions
    var json = JSON.parse(image);
    if(json.instructionId.toString()===document.getElementById("modelAttr").innerHTML.toString()) {
        var el = document.getElementById("comments");
        var heading = document.createElement("h3");
        heading.innerText = json.content;
        el.appendChild(heading);
    }
}


