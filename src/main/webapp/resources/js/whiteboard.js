/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */




function defineComment(content,instructionId,commentId,name,username) {
    window.alert(instructionId);
    var json = JSON.stringify({
        "content": content,
        "instructionId": instructionId,
        "commentId":commentId,
        "name":name,
        "username":username
    });
    drawComment(json);
    console.log("drawImageText");
    sendText(json);

}

function drawComment(image) {
    var json = JSON.parse(image);
    if(json.instructionId.toString()===document.getElementById("modelAttr").innerHTML.toString()) {
        var el=document.getElementById("comments");
        var div=document.createElement("div");
        var username=document.createElement("a");
        var says=document.createElement("span");
        var content=document.createElement("span");
        var like=document.createElement("div");
        var count=document.createElement("span");
        var img=document.createElement("span");
        img.style.cursor="pointer";
        img.className="glyphicon glyphicon-heart";
        img.setAttribute('onclick','addLike('+json.commentId+')');
        count.id="Likes"+json.commentId;
        count.innerText="0";
        like.className="like";
        like.appendChild(count);
        like.appendChild(img);
        content.innerText=json.content;
        says.innerText=" says:";
        username.innerText=json.name;
        username.href="/user/"+json.username;
        div.className="posted-comments";
        div.appendChild(username);
        div.appendChild(says);
        div.appendChild(document.createElement("br"));
        div.appendChild(content);
        div.appendChild(like);
        el.appendChild(div);
    }
}


