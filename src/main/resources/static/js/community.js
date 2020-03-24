function post() {
   var questionId = $("#question_id").val();
   var content = $("#comment_content").val();
   if(!content){
       alert("不能回复空内容");
       return;
   }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success: function (response) {
            if(response.code == 200){
                window.location.reload();
                $("#comment_DeleteSection").hide();
            }else {
                 if(response.code == 2003){
                     var isAccept =  confirm(response.message);
                     if(isAccept){
                         window.open("https://github.com/login/oauth/authorize?client_id=7fc0d3e8eec7dccf2a69&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                         window.localStorage.setItem("closable",true);
                     }
                 }else {
                     alert(response.message);
                 }
            }
        },
        dataType: "json"
    });
}