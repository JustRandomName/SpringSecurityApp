function deleteStep(namber) {
    var el=document.getElementById("StepId"+namber);
    el.remove();
    number--;
    for(var i=namber+1;i<=number;i++)
    {
        var heading=document.getElementById("Heading"+i);
        heading.id="Heading"+(i-1);
        heading.innerText="Step "+(i-1);
        var delite=document.getElementById("DeleteId"+i);
        delite.id="DeleteId"+(i-1);
        delite.removeAttribute("onclick");
        delite.setAttribute('onclick','deleteStep('+(i-1)+')');
        delite.innerText="Delete Step "+(i-1);
        var div=document.getElementById("StepId"+i);
        div.id="StepId"+(i-1);
    }
    el.remove();
}
function addNewStep() {
    var textarea= document.createElement("textarea");
    textarea.className="steps";
    var div= document.createElement("div");
    div.id="StepId"+number;
    var delite= document.createElement("a");
    delite.className = "glyphicon glyphicon-remove";
    delite.setAttribute('onclick','deleteStep('+number+')');
    // delite.innerText="Delete Step "+number;
    delite.id="DeleteId"+number;
    var heading=document.createElement("h3");
    heading.innerText="Step "+number;
    heading.id="Heading"+number;
    heading.className = "step";
    var el=document.getElementById("steps");
    div.appendChild(heading);
    div.appendChild(delite);
    div.appendChild(textarea);
    div.appendChild(document.createElement("br"));
    el.appendChild(div);
    $('textarea.steps').froalaEditor({
        toolbarButtons: ['bold', 'italic', 'underline', 'strikeThrough', 'color', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'indent', 'outdent', '-', 'insertImage', 'insertLink', 'insertFile', 'insertVideo', 'undo', 'redo']
    });
    number++;
}