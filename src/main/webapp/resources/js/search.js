function searchInstructions() {
    console.log("PILLLLLLLLLLL");
    var el=document.getElementById("search");
    window.alert(el.value);
    window.location.replace("/searchInstructions?search="+el.value);
}
