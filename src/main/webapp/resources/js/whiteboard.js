/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */




function defineComment(content,instructionId,commentId) {
window.alert(instructionId);
    var json = JSON.stringify({
        "content": content,
        "instructionId": instructionId,
        "commentId":commentId
    });
    drawComment(json);
    console.log("drawImageText");
    sendText(json);

}

function drawComment(image) {
    var json = JSON.parse(image);
    if(json.instructionId.toString()===document.getElementById("modelAttr").innerHTML.toString()) {
        var el = document.getElementById("comments");
        var heading = document.createElement("h3");
        var button=document.createElement("button");
        var p=document.createElement("p");
        p.setAttribute("id","Likes"+json.commentId);
        p.innerText=0;
        button.innerText="Like";
        button.setAttribute("onclick","addLike("+json.commentId+")");
        heading.innerText = json.content;
        el.appendChild(heading);
        el.appendChild(p);
        el.appendChild(button);
    }
}


