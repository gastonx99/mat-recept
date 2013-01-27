$(document).ready(function() {
	$.ajax({
		url : 'recipes.xml',
		success : handleXML,
		dataType : 'xml'
	});
});

function handleXML(xml) {
	var recipes = $.xml2json(xml);
	for ( var i1 = 0; i1 < recipes.recipe.length; i1++) {
		createRecipeRow(recipes.recipe[i1]);
	}
}

function createRecipeRow(recipe) {
	var $tr = $('<tr>');
	$tr.addClass('recipe-type-' + recipe.type);
	$tr.data('recipe', recipe);
	$tr.append($('<td>').text(recipe.name));

	$('#recipe-table-body').append($tr);
}