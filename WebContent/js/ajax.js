//Criando o objeto generico SENAI
//SENAI = new Object();

//Criando o sub-objeto ajax do objeto SENAI
ajax = new Object();

/*
* Processa o pedido, solicitacao HTTP Ajax a ser percebido pelo Rest
*/

function ajaxRequestDefault() {
	
	var def = {
			
		url: null,
		dataType: "json",
		contentType: "application/json; charset=UTF-8",
		type: "POST",
		success: function() {},
		error: function(err) {
			
			alert("error=" + err.responseText);
		}
	};
	
	return def;
}

/*
* Verifica o estado do objeto cfg recebido, ou seja, se o identificador cfg trata-se realmente de uma variavel
* de objeto contendo suas respectivas propriedades com valores, se isto se confirmar retorna o objeto cfg.
* Notem a chamada ao metodo stringify(), que, por sua vez, converte o conteudo dos dados encontrados em cfg.data
* em JSON para que posteriormente possam ser repassados pelo Ajax a partir de um pedido HTTP. Notem tambem a chamada
* a funcao isObject(cfg.data) que repassa como parametro cfg.data para saber se os dados apontados por cfg tratam-se de
* um array de dados, ou se 'e um objeto simples, criado como {} ou new Object e por ultimo verifica se trata-se de uma funcao objeto Javascript
*/
function verifyObjectData(cfg) {
	
	if(cfg.data) {
		
		if(isObject(cfg.data)) {
			
			cfg.data = JSON.stringify(cfg.data);
		}
	}
	
	return cfg;
}

/*
* A funcao abaixo verifica os dados apontados por cfg tratam-se de um array de dados, ou se 'e um objeto simples, criado como {} ou new Object e por
* ultimo verifica se trata-se de uma funcao objeto Javascript. Retorna o tipo estrutura passada pelo objeto cfg.
*/
function isObject(o) {
	
	return $.isArray(o) | $.isPlainObject(o) | $.isFunction(o);
};

ajax.post = function(cfg) {
	
	/*
	 * Inicia o Ajax e processa um pedido de Ajax, a partir de ajaxRequestDefault().
	 * Esta inicializacao e pedido sao repassadas para o objeto def que, por sua vez,
	 * passara a ser um Ajax solicitante de uso geral.
	 */
	var def = new ajaxRequestDefault();
	
	/*
	 * Chama a funcao verifyObjectData() que, por sua vez, verifica o estado do objeto cfg recebido, ou seja, se o identificador cfg trata-se realmente
	 * de uma variavel de objeto contendo suas respectivas propriedades com valores, se isto se confirmar retorna o objeto cfg e o armazena tambem em
	 * outro objeto cfg que sera fundido no objeto config para que uma solicitacao, pedido, HTTP, Ajax seja enviada para um recurso Rest.
	 */
	cfg = verifyObjectData(cfg);
	
	/*
	 * Fundindo os objetos del e cfg e armazenando seus respectivos valores em config
	 */
	var config = $.extend(def, cfg);
	
	/*
	 * Realizando um HTTP pedido ajax. O parametro config segue o pedido Ajax
	 */
	$.ajax(config);
};

ajax.get = function(cfg) {
	
	var def = new ajaxRequestDefault();
	cfg.type = "GET";
	cfg = verifyObjectData(cfg);
	var config = $.extend(del, cfg);
	$.ajax(config);
}