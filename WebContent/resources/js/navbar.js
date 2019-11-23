$(document).ready(function()
{
    $.ajax(
    {
        type: "POST",
        url: "../../LoggedUsername",
        success: function(user)
        {
            let html = "<p class='form-text text-dark text-label' id='loggedUsername'>" + user.fullname + "</p>";

            $("#infoUser").html(html);
        },
        error: function(err)
        {
            alert("Erro: " + err.responseText);
        }
    });

    logout = function()
    {
        text_toast = "Logout realizado com sucesso!";
        $("#text-toast-logout").html(text_toast).addClass("text-white overlay");
        $("#logout_validation").toast('show').addClass("bg-info");

        setTimeout(function(){javascript:Destroy_Session.submit()}, 2000);
    };
});