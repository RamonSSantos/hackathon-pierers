loginValidation = function()
{
    let text_toast = "";

    $.ajax(
    {
        type: "POST",
        url: "LoginServlet",
        data: $("#form_login").serialize(),
        success: function(msg)
        {
            $("#login_validation").removeClass("bg-danger");

            if(msg == 'SUCCESS')
            {
                text_toast = "Seja bem-vindo!";
                $("#text-toast-login").html(text_toast).addClass("text-white");
                $("#login_validation").toast('show').addClass("bg-info");

                setTimeout(function(){location.href="resources/home/home.html"}, 2000); 
            } else
            {
                text_toast = "Usuário ou senha incorretos!";
                $("#text-toast-login").html(text_toast).addClass("text-white");
                $("#login_validation").toast('show').addClass("bg-danger");
            }
        },
    });
};