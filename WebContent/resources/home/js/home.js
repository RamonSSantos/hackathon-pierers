$(document).ready(function()
{
    $.ajax(
    {
        type: "POST",
        url: "../../getListCompanyUnits",
        success: function(ListCompanyUnits)
        {
            let html = "<select name='companyUnit' id='companyUnit' class='form-control' required>";
                html += "<option value='0' selected>Escolha a unidade</option>";

            for(i = 0; i < ListCompanyUnits.length; i++)
            {
                html += "<option name='idCompanyUnit' id='idCompanyUnit' value='" + ListCompanyUnits[i].id + "'>" + ListCompanyUnits[i].tradeName + "</option>";
            }

                html += "</select>"

            $("#showCompanyUnits").html(html);

            let idCompanyUnit = undefined;
            $("#companyUnit").change(function(e)
            {
                //prevent Default functionality
                e.preventDefault();

                idCompanyUnit = $(this).val();
                $("#id_CompanyUnit").val(idCompanyUnit);
                
                if(idCompanyUnit == undefined || idCompanyUnit == 0)
                {
                    getListDepartments(undefined);

                } else
                {
                    getListDepartments(idCompanyUnit);
                }
            });
        },
        error: function(err)
        {
            alert("Erro!" + err.responseText);
        }
    });

    getListDepartments = function(idCompanyUnit)
    {
        let html = "<select name='department' id='department' class='form-control' required>";
            html += "<option value='0' selected>Escolha o departamento</option>";

        if(idCompanyUnit == undefined)
        {
            html += "</select>";

            $("#showDepartments").html(html);
        } else
        {
            $.ajax(
            {
                type: "POST",
                url: "../../getListDepartments",
                data: "idCompanyUnit=" + idCompanyUnit,
                success: function(ListDepartments)
                {
                    for(i = 0; i < ListDepartments.length; i++)
                    {
                        html += "<option name='idDepartment' id='idDepartment' value='" + ListDepartments[i].id + "'>" + ListDepartments[i].description + "</option>";
                    }
                    
                        html += "</select>"
    
                    $("#showDepartments").html(html);
                },
                error: function(err)
                {
                    alert("Erro!" + err.responseText);
                }
            });
        }
    };

    getListDepartments(undefined);

    $("#statusIdea").change(function(e)
    {
        //prevent Default functionality
        e.preventDefault();

        $("#idStatusIdea").val($(this).val());
        let idStatusIdea = $("#idStatusIdea").val();

        showIdeas(undefined, idStatusIdea);
    });

    showIdeas = function(ideaList, searchStatus)
    {
        let table = "<table class='table table-borderless mb-5'>";

        if(ideaList != undefined && ideaList.length > 0)
        {
            table += "<hr />";

            for(i = 0; i < ideaList.length; i++)
            {
                let statusIdea = "";
                if(ideaList[i].status == 1)
                {
                    statusIdea = "Aguardando aprovação";
                } else if(ideaList[i].status == 2)
                {
                    statusIdea = "Aprovada";
                } else if(ideaList[i].status == 3)
                {
                    statusIdea = "Reprovada";
                } else if(ideaList[i].status == 4)
                {
                    statusIdea = "Executada";
                }

                let nameAuthor = "";
                if(ideaList[i].userIdentification == 1)
                {
                    nameAuthor = ideaList[i].nameAuthor;
                } else
                {
                    nameAuthor = "Anônimo";
                }

                table += 
                "<tr class='row mt-4'>" + 
                    "<td class='col-2 p-0'><i class='fas fa-user fa-4x ml-0'></i></td>" + 
                    "<td class='col-6 text-title p-0'>Título: " + ideaList[i].title + 
                        "<i class='fas fa-eye fa-1x cursor-pointer ml-2' onclick='viewIdea(" + ideaList[i].id + ", " + ideaList[i].status + ")'></i>" + 
                        "<p class='text-small'>" + 
                            "<strong>Status: " + statusIdea + "</strong>" + 
                        "</p>" + 
                        "<td class='col-2 p-0'>" + 
                            "<i class='fas fa-comment text-outline-info cursor-pointer'></i> " + ideaList[i].comments +   
                        "</td>" + 
                        "<td class='col-2 p-0'>" + 
                            "<i class='fas fa-feather text-outline-info cursor-pointer'></i> " + ideaList[i].likes + 
                        "</td>" + 
                    "</td>" + 
                "</tr>" + 
                "<tr class='row'>" + 
                    "<td class='col-2 p-0'>" + 
                        "<p class='text-small'>" + 
                            "<strong>" + nameAuthor + "</strong>" + 
                        "</p>" + 
                    "</td>" + 
                    "<td class='col-10 p-0'>" + 
                        "<p class='text-label'>" + 
                            "<strong>Descrição: " + ideaList[i].description + "</strong>" + 
                        "</p>" + 
                    "</td>" + 
                "</tr>";
            }
        } else
        {
            table += "<hr />";

            if(ideaList == undefined)
            {
                if(searchStatus == 0)
                {
                    //Nao acontece nada
                } else
                {
                    searchStatus = $("#idStatusIdea").val();
                }

                var cfg = 
                {
                    type: "GET",
                    url: "../../rest/homeRest/searchIdeaByStatus/status/" + searchStatus,
                    success: function(ideaList)
                    {
                        showIdeas(ideaList);
                    },
                    error: function(err)
                    {
                        alert("Erro ao consultar as ideias!" + err.responseText);
                    }
                };
                ajax.post(cfg);
            } else
            {
                table += 
                "<tr class='row'>" + 
                    "<td class='col-12 ml-2'>Nenhuma ideia encontrada!" + 
                    "</td>" + 
                "</tr>";
            }
        }

        table += "</table>";
        $("#ideaList").html(table);
    };

    showIdeas(undefined, 0);

    viewIdea = function(idIdea, statusIdea)
    {
        $("#showIdea").modal();

        var cfg = 
        {
            type: "GET",
            url: "../../rest/ideaRest/searchIdeaById/id/" + idIdea + "/status/" + statusIdea,
            success: function(idea)
            {
                showIdeaById(idea);
            },
            error: function(err)
            {
                alert("Erro ao consultar a ideia!" + err.responseText);
            }
        };
        ajax.post(cfg);
    };

    showIdeaById = function(idea)
    {
        $("#titleViewIdea").val(idea.title);
        $("#registerDateViewIdea").val(idea.registerDate);
        $("#descriptionViewIdea").val(idea.description);
        $("#expectedResultsViewIdea").val(idea.expectedResults);

        if(idea.userIdentification == 1)
        {
            $("#authorViewIdea").val(idea.nameAuthor);
        } else
        {
            $("#authorViewIdea").val("Anônimo");
        }
        
        $("#companyUnitViewIdea").val(idea.nameCompanyUnit);
        $("#departmentviewIdea").val(idea.nameDepartment);

        let statusIdea = "";
        if(idea.status == 1)
        {
            statusIdea = "Aguardando aprovação";
            $("#nameApproverIdea").hide();
            $("#observationApprovalIdea").hide();
        } else
        {
            $("#nameApproverViewIdea").val(idea.nameApprover);
            $("#nameApproverIdea").show();

            $("#observationApprovalViewIdea").val(idea.observationApproval);
            $("#observationApprovalIdea").show();
        }
        
        if(idea.status == 2)
        {
            statusIdea = "Aprovada";
        } else if(idea.status == 3)
        {
            statusIdea = "Reprovada";
        } else if(idea.status == 4)
        {
            statusIdea = "Executada";
        }
        $("#statusViewIdea").val(statusIdea);
    };
});