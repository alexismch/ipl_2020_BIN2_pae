/*function listQuote(){
	$.ajax({
        url:'/quotes-list',
        type:'get',
        data:{
        },
        success:function(response){
            var json = JSON.parse(response);
			fillTable(json);
        },
        error:function(jqXHR, textStatus, errorThrown){
            console.log('jqXHR : ' + jqXHR + '\ntextStatus : ' + textStatus + '\nerrorThrown');
        }
    });
}


function fillTable(table){
	for(var i = 0 ; i < table.length ; i++){
		$('#tableListQuotes tr:last').after('<tr> <td>'+ table[i].idQuote + '</td>'+ '<td>' +
		 table[i].idCustomer + '</td>'+ '<td>' + table[i].quoteDate + '</td>'+ '<td>' + table[i].totalAmount + '</td>'+'</tr>');
	}
}
*/



function createQuotesList(quotesList) {
    $('#content').empty();
    for (const quote of quotesList) {
        const quoteListItem =`<li class="page-devis-item border rounded mb-2">
        <p>${quote.idQuote}</p>
        <a class="btn btn-primary w-min" href="plusTard">Sélectionner</a>
        </li>`;
        $('#content').append(quoteListItem);
    }
  }
  
  export {createQuotesList};