$(document).ready(function() {
	$('#xmlButton').click(function() {
		postMenuesXML(toXML());
	});
	$.ajax({
		url : 'constants.xml',
		success : handleConstantsXML,
		dataType : 'xml'
	});
	$('#trashcan-container').droppable({
		accept : "li.mealitem",
		tolerance : "pointer",
		drop : function(event, ui) {
			var $li = ui.draggable;
			var $ul = $li.parent();
			console.log($ul.children('li').size());
			$ul.children('li').each(function() {
				console.log($(this).html());
			});
			if ($ul.children('li.mealitem').size() == 1) {
				addEmptyMenuItem($ul);
			}
			$li.remove();
			var $trashcanContainer = $('#trashcan-container');
			$trashcanContainer.hide();
		},
		over : function(event, ui) {
			$(this).css({
				border : '1px solid red'
			});
		},
		out : function(event, ui) {
			$(this).css({
				border : '1px solid black'
			});
		}
	});
});

function postMenuesXML(xmlStr) {
	var indata = {
		username : 'gaston',
		xml : xmlStr
	};
	$.post('php/saveMenues.php', indata, function(data) {
		alert('success');
	}).fail(function() {
		alert("error");
	});
}

function toXML() {
	var menues = {};
	menues.weeklyMenu = [];
	function toJsonMeal($li, meal) {
		meal.dish = {
			recipeKey : []
		};

		$li.each(function() {
			var recipe = $(this).data('recipe');
			meal.dish.recipeKey.push(recipe.key);
		});
	}
	function toJsonWeeklyMenu($trWeeklyMenu) {
		var weeklyMenuData = $trWeeklyMenu.data('weeklyMenu');
		if (typeof (weeklyMenuData) != "undefined") {
			var wm = {
				key : weeklyMenuData.key,
				name : weeklyMenuData.name,
				day : []
			};
			menues.weeklyMenu.push(wm);

			var $trPlaceholder = $('#weeklyMenu-data-' + weeklyMenuData.key);

			var weekdays = findConstants('WEEKDAY');
			for ( var i1 = 0; i1 < weekdays.length; i1++) {
				var day = {
					key : weekdays[i1].key
				};

				var $liLunch = $trPlaceholder.find('div.' + weekdays[i1].key + ' div.lunch ul.dish li').not('.empty');
				if ($liLunch.size() > 0) {
					day.lunch = {};
					toJsonMeal($liLunch, day.lunch);
				}
				var $liDinner = $trPlaceholder.find('div.' + weekdays[i1].key + ' div.dinner ul.dish li').not('.empty');
				if ($liDinner.size() > 0) {
					day.dinner = {};
					toJsonMeal($liDinner, day.dinner);
				}
				wm.day.push(day);
			}
		}
	}
	var doc = document.implementation.createDocument(null, null, null);
	function toXML(obj, nodeName) {
		var parentNode = doc.createElement(nodeName);
		if (typeof (obj) === 'string') {
			var text = doc.createTextNode(obj);
			parentNode.appendChild(text);
		} else {
			for ( var key in obj) {
				if (Array.isArray(obj[key])) {
					for ( var i1 = 0; i1 < obj[key].length; i1++) {
						parentNode.appendChild(toXML(obj[key][i1], key));
					}
				} else {
					parentNode.appendChild(toXML(obj[key], key));
				}
			}
		}
		return parentNode;
	}
	$('#menu-table-body tr').each(function() {
		toJsonWeeklyMenu($(this));
	});
	var node = toXML(menues, 'menues');
	// var newDoc = JXON.unbuild(menues);
	doc.appendChild(node);
	return (new XMLSerializer()).serializeToString(doc);

}

function loadRecipes() {
	$.ajax({
		url : 'recipes.xml',
		success : handleRecipesXML,
		dataType : 'xml'
	});
}

function loadMenues() {
	$.ajax({
		url : 'php/menues.php',
		success : handleMenuesXML,
		dataType : 'xml'
	});
}

var EXPANDED_ICON = '&#9660;';
var COLLAPSED_ICON = '&#9654;';
var CURRENT_WEEK = '201305';
var LI_EMPTY = '<Dra och släpp hit>';

var allRecipes;
var constants;

function handleConstantsXML(xml) {
	constants = $.xml2json(xml);
	loadRecipes();
}

function handleRecipesXML(xml) {
	var recipes = $.xml2json(xml);
	for ( var i1 = 0; i1 < recipes.recipe.length; i1++) {
		var recipe = recipes.recipe[i1];
		createRecipeRow(recipe);
	}
	allRecipes = recipes.recipe;
	$('.recipe-name').draggable({
		helper : "clone"
	});
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
	var $tr = $('<tr>').addClass('recipe-type-' + recipe.type).addClass('collapsed');
	var $icon = $('<span>').addClass('expanded-state').html(COLLAPSED_ICON);
	var $recipe = $('<span>').addClass('recipe-name').text(recipe.name).data('recipe', recipe);
	var $td = $('<td>');
	$td.append($icon).append($recipe);
	$tr.append($td);

	$('#recipe-table-body').append($tr);
}

function createMenuRow(weeklyMenu) {
	var $tr = $('<tr>').attr('id', 'weeklyMenu-' + weeklyMenu.key);
	$tr.addClass('collapsed');
	$tr.data('weeklyMenu', weeklyMenu);
	var text = '<span class="expanded-state">' + COLLAPSED_ICON + '</span>' + weeklyMenu.name;
	$tr.append($('<td>').html(text));

	$('#menu-table-body').append($tr);

	// Placeholder row for data
	$tr = $('<tr>').attr('id', 'weeklyMenu-data-' + weeklyMenu.key);
	$tr.hide();
	$('#menu-table-body').append($tr);
}

function findWeeklyMenuDay(weeklyMenu, dayKey) {
	for ( var i1 = 0; i1 < weeklyMenu.day.length; i1++) {
		if (weeklyMenu.day[i1].key === dayKey) {
			return weeklyMenu.day[i1];
		}
	}
	return 'NOT_PRESENT';
}

function hasRecipeKeys(meal) {
	if (typeof (meal) == 'undefined' || typeof (meal.dish) == 'undefined'
			|| typeof (meal.dish.recipeKey) == 'undefined') {
		return false;
	} else {
		return meal.dish.recipeKey.length > 0;
	}
}

function addMealItem($ul, recipeKey) {
	var recipe = findRecipe(recipeKey);
	var $li = $('<li>').addClass('mealitem').text(recipe.name).data('recipe', recipe);
	$ul.append($li);
	return $li;
}

function addEmptyMenuItem($ul) {
	var $li = $('<li>').text(LI_EMPTY).addClass('empty');
	$ul.append($li);
}

function makeMealItemDraggable($li) {
	$li.draggable({
		revert : true,
		start : function(event, ui) {
			var $trashcanContainer = $('#trashcan-container');
			$trashcanContainer.css({
				left : event.pageX - 150,
				top : event.pageY - 20,
				border : '1px solid black'
			});
			$trashcanContainer.show();
		},
		stop : function(event, ui) {
			var $trashcanContainer = $('#trashcan-container');
			$trashcanContainer.hide();
		}

	});

}

function showWeeklyMenu(weeklyMenu) {
	var $trPlaceholder = $('#weeklyMenu-data-' + weeklyMenu.key);
	if ($trPlaceholder.find('td').size() == 0) {
		var $tdPlaceholder = $('<td>');
		var $table = $('<table>');
		var weekdays = findConstants('WEEKDAY');
		for ( var i1 = 0; i1 < weekdays.length; i1++) {
			var weeklyMenuDay = findWeeklyMenuDay(weeklyMenu, weekdays[i1].key);
			var $tr = $('<tr>');
			var $td = $('<td>');
			$td.append($('#daily-menu-template').html());
			$td.find('h2.day').text(weekdays[i1].value);

			$td.find('div.daily-menu').addClass(weekdays[i1].key);

			var $ulLunch = $td.find('div.lunch ul.dish');
			var $ulDinner = $td.find('div.dinner ul.dish');
			if (weeklyMenuDay === 'NOT_PRESENT' || !hasRecipeKeys(weeklyMenuDay.lunch)) {
				addEmptyMenuItem($ulLunch);
			} else {
				if (typeof (weeklyMenuDay.lunch.dish.recipeKey) === 'string') {
					addMealItem($ulLunch, weeklyMenuDay.lunch.dish.recipeKey);
				} else {
					for ( var i2 = 0; i2 < weeklyMenuDay.lunch.dish.recipeKey.length; i2++) {
						addMealItem($ulLunch, weeklyMenuDay.lunch.dish.recipeKey[i2]);
					}
				}
			}
			if (weeklyMenuDay === 'NOT_PRESENT' || !hasRecipeKeys(weeklyMenuDay.dinner)) {
				addEmptyMenuItem($ulDinner);
			} else {
				if (typeof (weeklyMenuDay.dinner.dish.recipeKey) === 'string') {
					addMealItem($ulDinner, weeklyMenuDay.dinner.dish.recipeKey);
				} else {
					for ( var i2 = 0; i2 < weeklyMenuDay.dinner.dish.recipeKey.length; i2++) {
						addMealItem($ulDinner, weeklyMenuDay.dinner.dish.recipeKey[i2]);
					}
				}
			}
			$tr.append($td);
			$table.append($tr);
		}
		$tdPlaceholder.append($table);
		$trPlaceholder.append($tdPlaceholder);
		$trPlaceholder.find('li.mealitem').each(function() {
			makeMealItemDraggable($(this));
		});
		$trPlaceholder.find('ul.dish').droppable({
			accept : "span.recipe-name",
			tolerance : "pointer",
			drop : function(event, ui) {
				var recipe = ui.draggable.data('recipe');
				var $ul = $(event.target);
				$ul.find('li.empty').remove();
				var $li = addMealItem($ul, recipe.key);
				makeMealItemDraggable($li);
			}
		});
	}
	$trPlaceholder.show();
}

function findConstant(type, key) {
	for ( var i1 = 0; i1 < constants.constant.length; i1++) {
		var constant = constants.constant[i1];
		if (constant.type === type && constant.key === key) {
			return constant.value;
		}
	}
	return 'Wrong key for constant with key ' + key + ' and type ' + type;
}

function findConstants(type) {
	var arr = new Array();
	for ( var i1 = 0; i1 < constants.constant.length; i1++) {
		var constant = constants.constant[i1];
		if (constant.type === type) {
			arr.push(constant);
		}
	}
	return arr;
}

function findWeekday(key) {
	return findConstant('WEEKDAY', key);
}

function findRecipe(key) {
	for ( var i1 = 0; i1 < allRecipes.length; i1++) {
		if (allRecipes[i1].key == key) {
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