$(document).ready(function() {
	$.ajax({
		url : 'recipes.xml',
		success : handleRecipesXML,
		dataType : 'xml'
	});
});

function loadMenues() {
	$.ajax({
		url : 'menues.xml',
		success : handleMenuesXML,
		dataType : 'xml'
	});
}

var EXPANDED_ICON = '&#9660;';
var COLLAPSED_ICON = '&#9654;';
var CURRENT_WEEK = '201305';

var allRecipes;

function handleRecipesXML(xml) {
	var recipes = $.xml2json(xml);
	for ( var i1 = 0; i1 < recipes.recipe.length; i1++) {
		var recipe = recipes.recipe[i1];
		createRecipeRow(recipe);
	}
	allRecipes = recipes.recipe;
	loadMenues();
}

function handleMenuesXML(xml) {
	var menues = $.xml2json(xml);
	for ( var i1 = 0; i1 < menues.weeklyMenu.length; i1++) {
		createMenuRow(menues.weeklyMenu[i1]);
	}
	var $tr = $('#weeklyMenu-' + CURRENT_WEEK);
	toggleWeeklyMenuExpandedState($tr);
}

function createRecipeRow(recipe) {
	var $tr = $('<tr>');
	$tr.addClass('recipe-type-' + recipe.type);
	$tr.addClass('collapsed');
	$tr.data('recipe', recipe);
	var text = '<span class="expanded-state">' + COLLAPSED_ICON + '</span>'
			+ recipe.name;
	$tr.append($('<td>').html(text));

	$('#recipe-table-body').append($tr);
}

function createMenuRow(weeklyMenu) {
	var $tr = $('<tr>').attr('id', 'weeklyMenu-' + weeklyMenu.key);
	$tr.addClass('collapsed');
	$tr.data('weeklyMenu', weeklyMenu);
	var text = '<span class="expanded-state">' + COLLAPSED_ICON + '</span>'
			+ weeklyMenu.name;
	$tr.append($('<td>').html(text));

	$('#menu-table-body').append($tr);

	// Placeholder row for data
	$tr = $('<tr>').attr('id', 'weeklyMenu-data-' + weeklyMenu.key);
	$tr.hide();
	$('#menu-table-body').append($tr);
}

function toggleRecipeExpandedState($tr) {

}

function showWeeklyMenu(weeklyMenu) {
	var $trPlaceholder = $('#weeklyMenu-data-' + weeklyMenu.key);
	if($trPlaceholder.find('td').size() == 0) {
		var $tdPlaceholder = $('<td>');
		var $table = $('<table>');
		for ( var i1 = 0; i1 < weeklyMenu.day.length; i1++) {
			var day = weeklyMenu.day[i1];
			var $tr = $('<tr>');
			var $td = $('<td>');
			$td.append($('#daily-menu-template').html());
			$td.find('.daily-menu').show();
			$td.find('h2.day').text(day.key);

			var $ulLunch = $td.find('div.lunch ul.dish');
			for ( var i2 = 0; i2 < day.lunch.dish.recipeKey.length; i2++) {
				var recipe = findRecipe(day.lunch.dish.recipeKey[i2]);
				var $li = $('<li>').text(recipe.name);
				$ulLunch.append($li);
			}
			var $ulDinner = $td.find('div.dinner ul.dish');
			for ( var i2 = 0; i2 < day.dinner.dish.recipeKey.length; i2++) {
				var recipe = findRecipe(day.dinner.dish.recipeKey[i2]);
				var $li = $('<li>').text(recipe.name);
				$ulDinner.append($li);
			}

			$tr.append($td);
			$table.append($tr);
		}
		$tdPlaceholder.append($table);
		$trPlaceholder.append($tdPlaceholder);
	}
	$trPlaceholder.show();
}

function findRecipe(key) {
	for(var i1 = 0; i1 < allRecipes.length; i1++) {
		if(allRecipes[i1].key == key) {
			return allRecipes[i1];
		}
	}
	return 'Wrong key for recipe with key ' + key;
}

function toggleWeeklyMenuExpandedState($tr) {
	var weeklyMenu = $tr.data('weeklyMenu');
	if ($tr.hasClass('collapsed')) {
		$tr.find('.expanded-state').html(EXPANDED_ICON);
		$tr.removeClass('collapsed');
		$tr.addClass('expanded');
		showWeeklyMenu(weeklyMenu);
	} else {
		$tr.find('.expanded-state').html(COLLAPSED_ICON);
		$tr.addClass('collapsed');
		$tr.removeClass('expanded');
	}
}