$(document).ready(function()
{
    $("#searchUser").keyup(function()
    {
        let searchUser = $(this).val();
        if(searchUser != "")
        {
            var cfg = 
            {
                type: "GET",
                url: "../../rest/userRest/searchUserByName/" + searchUser,
                success: function(rankingList)
                {
                    if(rankingList.length == 0)
                    {
                        showInfoRanking(rankingList, "")
                    } else if(rankingList.length == 1)
                    {
                        showInfoRanking(undefined, searchUser);
                    } else if(rankingList != undefined && rankingList.length > 0)
                    {
                        //Lista de usuarios
                        let html = "<select name='userListItem' id='userListItem' class='form-control'>";

                        for(i = 0; i < userList.length; i++)
                        {
                            html += "<option name='idUsername' id='idUsername' value='" + rankingList[i].id + "'>" + rankingList[i].fullname + "</option>";
                        }

                        html += "</select>";
                        $("#UserList").html(html).show();
                    }

                    $('#userListItem').on('change', function()
                    {
                        let searchValue = $(this).val();

                        showinfoUser(undefined, searchValue);

                        document.getElementById("searchUser").focus();
                    });
                },
                error: function(err)
                {
                    alert("Erro ao consultar o ranking!" + err.responseText);
                }
            };
            ajax.post(cfg);
        } else
        {
            showInfoRanking(undefined, "");
        }
    });

    search_User = function()
    {
        let searchValue = $("#searchUser").val();
        showInfoRanking(undefined, searchValue);
    };

    showInfoRanking = function(rankingList, searchUser)
    {
        let theadTable = "<div class='table-responsive'>";
        theadTable += "<table class='table table-bordered table-striped mt-2'>";
        theadTable += "<thead class='thead-dark'>";
        theadTable += "<tr class='d-flex text-center subtitle'>";
        theadTable += "<th class='col-4'>Nome completo</th><th class='col-3'>Unidade</th><th class='col-2'>Departamento</th><th class='col-1' class='tooltip' data-toggle='tooltip' data-placement='top' title='Pontos atuais'>PA</th><th class='col-1' class='tooltip' data-toggle='tooltip' data-placement='top' title='Pontos totais'>PT</th><th class='col-1'>Ações</th></tr></thead>";
        theadTable += "</table>";
        theadTable += "</div>";

        $("#thead_Table").html(theadTable);

        let html = "<div class='table-responsive'>";
        html += "<table id='rankingListTable' class='table table-bordered table-striped mt-2'>";

        if(rankingList != undefined && rankingList.length > 0)
        {
            for(i = 0; i < rankingList.length; i++)
            {
                html += "<tbody class='table-hover'><tr class='d-flex text-center text-small table-light'>" + 
                        "<td class='col-4 text-uppercase'>" + rankingList[i].fullname + "</td>" + 
                        "<td class='col-3 text-uppercase'>" + rankingList[i].nameCompanyUnit + "</td>" + 
                        "<td class='col-2 text-uppercase'>" + rankingList[i].nameDepartment + "</td>" + 
                        "<td class='col-1'>" + rankingList[i].currentScore + "</td>" + 
                        "<td class='col-1'>" + rankingList[i].totalScore + "</td>";

                html += "<td class='col-1 cursor-pointer'><a onclick='viewUser("+ rankingList[i].id + ")'><i class='fas fa-eye'></i></a></td>" +  
                        "</tr></tbody>";
            }
        } else
        {
            if(rankingList == undefined)
            {
                if(searchUser == "")
                {
                    searchUser = null;
                }

                var cfg = 
                {
                    type: "GET",
                    url: "../../rest/userRest/searchUserByName/" + searchUser,
                    success: function(rankingList)
                    {
                        showInfoRanking(rankingList);
                    },
                    error: function(err)
                    {
                        alert("Erro ao consultar o ranking!" + err.responseText);
                    }
                };
                ajax.post(cfg);
            } else
            {
                html += "<tbody class='table-hover'><tr><td colspan='5'>Nenhum usuário encontrado!</td></tr></tbody>";
            }
        }
        html += "</table>";
        html += "</div>";
        $("#rankingList").html(html);

        $(document).ready(function()
        {
            $('#rankingListTable').paginathing(
            {
                perPage : 5,
                firstText : 'Primeira',
                lastText : 'Última',
                insertAfter: '#rankingListTable'
            });
        });
    };

    showInfoRanking(undefined, "");
});