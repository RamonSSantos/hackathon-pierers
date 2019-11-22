$(document).ready(function()
{
    showMyScore = function()
    {
        let theadTable = "<div class='table-responsive'>";
        theadTable += "<table class='table table-bordered table-striped mt-2'>";
        theadTable += "<caption>Minha pontuação</caption>";
        theadTable += "<thead class='thead-dark'>";
        theadTable += "<tr class='d-flex text-center subtitle'>";
        theadTable += "<th class='col-4'>Nome completo</th><th class='col-3'>Unidade</th><th class='col-2'>Departamento</th><th class='col-1'>PA</th><th class='col-1'>PT</th><th class='col-1'>Ações</th></tr></thead>";
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

                        showMyRewards(userScore.currentScore);
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

    showMyRewards = function(currentScoreUser)
    {
        console.log(currentScoreUser);

        let theadTable = "<div class='table-responsive mt-2'>";
        theadTable += "<table class='table table-bordered table-striped mt-2'>";
        theadTable += "<caption>Prêmios</caption>";
        theadTable += "<thead class='thead-dark'>";
        theadTable += "<tr class='d-flex text-center subtitle'>";
        theadTable += "<th class='col-1'>ID</th><th class='col-3'>Ilustração</th><th class='col-4'>Descrição</th><th class='col-2'>Pontos</th><th class='col-2'>Ações</th></tr></thead>";
        theadTable += "</table>";
        theadTable += "</div>";

        $("#thead_TableRewards").html(theadTable);

        let html = "<div class='table-responsive'>";
        html += "<table id='myRewardsTable' class='table table-bordered table-striped mt-2 mb-5'>";

        var cfg = 
        {
            type: "GET",
            url: "../../rest/rewardRest/searchRewards",
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
                            "<td class='col-2'>" + rewardList[i].score + "</td>";

                    html += "<td class='col-2'>";

                    if(currentScoreUser < rewardList[i].score)
                    {
                        html += "<button type='button' class='btn btn-outline-info disabled' onclick='getReward(" + rewardList[i].id + ")'>Resgatar <i class='fas fa-gift'></i></button>";
                    } else
                    {
                        html += "<button type='button' class='btn btn-info' onclick='getReward(" + rewardList[i].id + ")'>Resgatar <i class='fas fa-gift'></i></button>";
                    }

                    html += "</td>" +  
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
});