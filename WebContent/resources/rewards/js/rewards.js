$(document).ready(function()
{
    showMyScore = function()
    {
        let theadTable = "<div class='table-responsive'>";
        theadTable += "<table class='table table-bordered table-striped mt-2'>";
        theadTable += "<caption>Minha pontuação</caption>";
        theadTable += "<thead class='thead-dark'>";
        theadTable += "<tr class='d-flex text-center subtitle'>";
        theadTable += "<th class='col-4'>Nome completo</th><th class='col-3'>Unidade</th><th class='col-2'>Departamento</th><th class='col-1' class='tooltip' data-toggle='tooltip' data-placement='top' title='Pontos atuais'>PA</th><th class='col-1' class='tooltip' data-toggle='tooltip' data-placement='top' title='Pontos totais'>PT</th><th class='col-1'>Ações</th></tr></thead>";
        theadTable += "</table>";
        theadTable += "</div>";

        $("#thead_Table").html(theadTable);

        let html = "<div class='table-responsive'>";
        html += "<table id='myRewardsTable' class='table table-bordered table-striped mt-2'>";

        $.ajax(
        {
            type: "POST",
            url: "../../LoggedUsername",
            success: function(user)
            {
                var cfg = 
                {
                    type: "GET",
                    url: "../../rest/userRest/searchUserById/" + user.id,
                    success: function(userScore)
                    {
                        html += "<tbody class='table-hover'><tr class='d-flex text-center text-small table-light'>" + 
                                "<td class='col-4 text-uppercase'>" + userScore.fullname + "</td>" + 
                                "<td class='col-3 text-uppercase'>" + userScore.nameCompanyUnit + "</td>" + 
                                "<td class='col-2 text-uppercase'>" + userScore.nameDepartment + "</td>" + 
                                "<td class='col-1'>" + userScore.currentScore + "</td>" + 
                                "<td class='col-1'>" + userScore.totalScore + "</td>";

                        html += "<td class='col-1 cursor-pointer'><a onclick='viewUser("+ userScore.id + ")'><i class='fas fa-eye'></i></a></td>" +  
                                "</tr></tbody>";

                        html += "</table>";
                        html += "</div>";

                        $("#myScoreList").html(html);

                        showMyRewards(userScore.id, userScore.currentScore);
                    },
                    error: function(err)
                    {
                        alert("Erro ao consultar os prêmios!" + err.responseText);
                    }
                };
                ajax.post(cfg);
            },
            error: function(err)
            {
                alert("Erro: " + err.responseText);
            }
        });
    };

    showMyScore();

    showMyRewards = function(idUser, currentScoreUser)
    {
        let theadTable = "<div class='table-responsive mt-2'>";
        theadTable += "<table class='table table-bordered table-striped mt-2'>";
        theadTable += "<caption>Prêmios</caption>";
        theadTable += "<thead class='thead-dark'>";
        theadTable += "<tr class='d-flex text-center subtitle'>";
        theadTable += "<th class='col-1'>ID</th><th class='col-3'>Ilustração</th><th class='col-4'>Descrição</th><th class='col-1'>Pontos</th><th class='col-2'>Ações</th><th class='col-1' class='tooltip' data-toggle='tooltip' data-placement='top' title='Quantidade recuperada'>QR</th></tr></thead>";
        theadTable += "</table>";
        theadTable += "</div>";

        $("#thead_TableRewards").html(theadTable);

        let html = "<div class='table-responsive'>";
        html += "<table id='myRewardsTable' class='table table-bordered table-striped mt-2 mb-5'>";

        var cfg = 
        {
            type: "GET",
            url: "../../rest/rewardRest/searchRewards/" + idUser,
            success: function(rewardList)
            {
                for(i = 0; i < rewardList.length; i++)
                {
                    let imgPath = "";
                    if(rewardList[i].score == 100)
                    {
                        imgPath = "../img/squeeze.png";
                    } else if(rewardList[i].score == 160)
                    {
                        imgPath = "../img/valeCompras.png";
                    } else if(rewardList[i].score == 200)
                    {
                        imgPath = "../img/camiseta.png";
                    } else if(rewardList[i].score == 300)
                    {
                        imgPath = "../img/jaqueta.png";
                    } else if(rewardList[i].score == 600)
                    {
                        imgPath = "../img/jbl.png";
                    }

                    html += "<tbody class='table-hover'><tr class='d-flex text-center text-small table-light'>" + 
                            "<td class='col-1'>" + rewardList[i].id + "</td>" + 
                            "<td class='col-3'><img src='" + imgPath + "' /></td>" + 
                            "<td class='col-4 text-uppercase'>" + rewardList[i].description + "</td>" + 
                            "<td class='col-1'>" + rewardList[i].score + "</td>";

                    html += "<td class='col-2'>";

                    if(currentScoreUser < rewardList[i].score)
                    {
                        html += "<button type='button' class='btn btn-outline-info tooltip-inner disabled' data-toggle='tooltip' data-placement='top' title='Pontos insuficientes para resgatar!'>Resgatar <i class='fas fa-gift'></i></button>";
                    } else
                    {
                        html += "<button type='button' class='btn btn-info' onclick='getReward(" + idUser + ", " + rewardList[i].id + ", " + rewardList[i].score + ")' name='btnGetReward' id='btnGetReward'>" + 
                                "Resgatar <i class='fas fa-gift'></i></button>";
                    }

                    html += "</td>" +  
                            "<td class='col-1'>" + rewardList[i].quantityReceived + "</td>" + 
                            "</tr></tbody>";
                }

                html += "</table>";
                html += "</div>";
                $("#myRewardsList").html(html);
            },
            error: function(err)
            {
                alert("Erro ao consultar os prêmios!" + err.responseText);
            }
        };
        ajax.post(cfg);
    };

    getReward = function(idUser, idReward, scoreReward)
    {
        let btnGetReward = $("#btnGetReward");
        btnGetReward.click(function()
        {
            btnGetReward.html("Aguarde...");
            btnGetReward.prop("disabled", true);
        });

        var cfg = 
        {
            title: "Tem certeza que deseja retirar o prêmio?",
            height: "auto",
            width: 400,
            modal: true,
            buttons: 
            {
                "Sim": function()
                {
                    btnGetReward.prop("disabled", false);

                    var cfg = 
                    {
                        type: "GET",
                        url: "../../rest/rewardRest/getReward/idUser/" + idUser + "/idReward/" + idReward,
                        success: function(result)
                        {
                            var msg = 
                            {
                                title: "Retirada de prêmio",
                                height: "auto",
                                width: "auto",
                                modal: true,
                                buttons: 
                                {
                                    "OK": function()
                                    {
                                        $(this).dialog("close");
                                    }
                                }
                            };

                            $("#msg").html(result);
                            $("#msg").dialog(msg);

                            showMyScore();
                        },
                        error: function(err)
                        {
                            alert("Erro ao consultar os prêmios!" + err.responseText);
                        }
                    };
                    ajax.post(cfg);
                },
                "Não": function()
                {
                    btnGetReward.prop("disabled", false);

                    $(this).dialog("close");

                    showMyScore();
                }
            }
        };
        
        $("#msg").dialog(cfg);
        $(this).dialog("close");
    };
});