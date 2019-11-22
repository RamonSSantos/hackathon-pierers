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

                $.ajax(
                {
                    type: "POST",
                    url: "../../LoggedUsername",
                    success: function(user)
                    {
                        var cfg = 
                        {
                            type: "GET",
                            url: "../../rest/ideaRest/searchIdeaByUser/user/" + user.id + "/status/" + searchStatus,
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
                    },
                    error: function(err)
                    {
                        alert("Erro: " + err.responseText);
                    }
                });
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

    openAddIdea = function()
    {
        $("#addIdea").modal();

        $("#addIdea").submit(function(e)
        {
            //prevent Default functionality
            e.preventDefault();

            addIdea();
        });
    };

    addIdea = function()
    {
        let btnAddIdea = $("#btnAddIdea");
        btnAddIdea.click(function()
        {
            btnAddIdea.html("Aguarde...");
            btnAddIdea.prop("disabled", true);
        })

        let addIdea = new Object();

        addIdea.title = $("#titleAddIdea").val();
        addIdea.description = $("#descriptionAddIdea").val();
        addIdea.expectedResults = $("#expectedResultsAddIdea").val();
        addIdea.idDepartment = $("#department").val();

        if($("#companyUnit").val() == 0 || $("#department").val() == 0)
        {
            var cfg = 
            {
                title: "Cadastro de ideia",
                height: "auto",
                width: "auto",
                modal: true,
                buttons: 
                {
                    "OK": function()
                    {
                        btnAddIdea.html("Salvar");
                        btnAddIdea.prop("disabled", false);

                        $(this).dialog("close");
                    }
                }
            };

            $("#addIdea").modal("hide");

            $("#msg").html("Preencha a unidade ou departamento corretamente!");
            $("#msg").dialog(cfg);
        } else
        {
            if($("#statusAddIdea").is(":checked"))
            {
                addIdea.userIdentification = $("#statusAddIdea").val();
            } else
            {
                addIdea.userIdentification = 0;
            }

            $.ajax(
            {
                type: "POST",
                url: "../../LoggedUsername",
                success: function(user)
                {
                    addIdea.idAuthor = user.id;
    
                    var cfg = 
                    {
                        url: "../../rest/ideaRest/addIdea",
                        data: JSON.stringify(addIdea),
                        success: function(msg)
                        {
                            var cfg = 
                            {
                                title: "Cadastro de ideia",
                                height: "auto",
                                width: "auto",
                                modal: true,
                                buttons: 
                                {
                                    "OK": function()
                                    {
                                        btnAddIdea.html("Salvar");
                                        btnAddIdea.prop("disabled", false);
    
                                        $(this).dialog("close");
                                    }
                                }
                            };
    
                            $("#addIdea").modal("hide");
    
                            $("#titleAddIdea").val("");
                            $("#descriptionAddIdea").val("");
                            $("#expectedResultsAddIdea").val("");
                            $("#companyUnit").val(0);
                            $("#department").val(0);
    
                            showIdeas(undefined, 0);
    
                            $("#msg").html(msg);
                            $("#msg").dialog(cfg);
                        },
                        error: function(err)
                        {
                            alert("Erro ao cadastrar a ideia" + err.responseText);
                        }
                    };
                    ajax.post(cfg);
                },
                error: function(err)
                {
                    alert("Erro: " + err.responseText);
                }
            });
        }
    };

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