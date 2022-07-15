$(function(){
    $("#verifyCodeBtn").click(getVerifyCode);
});

function getVerifyCode() {
    var email = $("#your-email").val();

    if(!email) {
        alert("请先填写您的邮箱！");
        return false;
    }

    $.get(
        CONTEXT_PATH + "/forgetPassword",
        {"email" : email, "code" : code, "newPassword" : newPassword},
        function(data) {

        }
    );
}