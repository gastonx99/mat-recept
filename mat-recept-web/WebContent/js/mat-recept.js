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
	var menues = {
		weeklyMenu : []
	};
	function toJsonMeal($li) {
		var meal = {
			dish : {
				recipeKey : []
			}
		};
		$li.each(function() {
			var recipe = $(this).data('recipe');
			meal.dish.recipeKey.push(recipe.key);
		});
		return meal;
	}
	function toJsonWeeklyMenu($trWeeklyMenu) {
		var weeklyMenuData = $trWeeklyMenu.data('weeklyMenu');
		var wm = {
			key : weeklyMenuData.key,
			name : weeklyMenuData.name,
			day : []
		};

		var weekdays = findConstants('WEEKDAY');
		for ( var i1 = 0; i1 < weekdays.length; i1++) {
			var day = {
				key : weekdays[i1].key
			};
			var $liLunch = $trWeeklyMenu.find('div.' + weekdays[i1].key + ' div.lunch ul.dish li').not('.empty');
			if ($liLunch.size() > 0) {
				day.lunch = toJsonMeal($liLunch);
			}
			var $liDinner = $trWeeklyMenu.find('div.' + weekdays[i1].key + ' div.dinner ul.dish li').not('.empty');
			if ($liDinner.size() > 0) {
				day.dinner = toJsonMeal($liDinner);
			}
			wm.day.push(day);
		}
		return wm;
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
	$('#menu-table-body tr').not('#menu-table-body-row-template').each(function() {
		menues.weeklyMenu.push(toJsonWeeklyMenu($(this)));
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
	$('#recipe-table-body tr').not('#recipe-table-body-row-template').sortElements(recipeComparator);
	allRecipes = recipes.recipe;
	$('.recipe-name').draggable({
		helper : "clone"
	});
	loadMenues();
}

function handleMenuesXML(xml) {
	function getKeyForYearWeek(yearweek) {
		return yearweek.year + (yearweek.week < 10 ? '0' : '') + yearweek.week;
	}
	function existsForWeek(menues, yearweek) {
		var key = getKeyForYearWeek(yearweek);
		for ( var i1 = 0; i1 < menues.weeklyMenu.length; i1++) {
			if (menues.weeklyMenu[i1].key === key) {
				return true;
			}
		}
		return false;
	}
	function addFutureWeeksIfNotPresent(menues) {
		var currentDate = new Date();
		for ( var i1 = 0; i1 < 4; i1++) {
			var d = new Date();
			d.setDate(currentDate.getDate() + (i1 * 7 + 7));
			var yearweek = getWeekNumber(d);
			if (!existsForWeek(menues, yearweek)) {
				var wm = {
					key : getKeyForYearWeek(yearweek),
					name : 'Vecka ' + yearweek.week,
					day : []
				};
				menues.weeklyMenu.push(wm);
			}
		}
	}
	var menues = $.xml2json(xml);
	addFutureWeeksIfNotPresent(menues);
	for ( var i1 = 0; i1 < menues.weeklyMenu.length; i1++) {
		createMenuRow(menues.weeklyMenu[i1]);
	}
	var currentDate = new Date();
	var currentWeek = getWeekNumber(currentDate);

	var $tr = $('#weeklyMenu-' + currentWeek.year + (currentWeek.week < 10 ? '0' : '') + currentWeek.week);
	toggleWeeklyMenuExpandedState($tr);
	$('#menu-table-body span.expanded-state').click(function() {
		toggleWeeklyMenuExpandedState($(this).parent().parent());
	});
}

function createRecipeRow(recipe) {
	var $tr = $('<tr>').addClass('collapsed');
	$tr.append($('#recipe-table-body-row-template').html());
	$tr.find('.expanded-state').html(COLLAPSED_ICON);
	$tr.find('.recipe-name').addClass('recipe-type-' + recipe.type).text(recipe.name).data('recipe', recipe);
	$('#recipe-table-body').append($tr);
}

function createMenuRow(weeklyMenu) {
	var $tr = $('<tr>').attr('id', 'weeklyMenu-' + weeklyMenu.key).html($('#menu-table-body-row-template').html());
	$tr.addClass('collapsed');
	$tr.data('weeklyMenu', weeklyMenu);
	$tr.find('span.expanded-state').html(COLLAPSED_ICON);
	$tr.find('span.weekly-menu-name').text(weeklyMenu.name);

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
	var $li = $('<li>').addClass('mealitem').data('recipe', recipe);
	var $span = $('<span>').addClass('recipe-type-' + recipe.type).text(recipe.name);
	$li.append($span);
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

function getRecipeTypeSortIndex(recipe) {
	var index;
	switch (recipe.type) {
	case 'FISH':
		index = 0;
		break;
	case 'POULTRY':
		index = 1;
		break;
	case 'VEGGIE':
		index = 2;
		break;
	case 'MEAT':
		index = 3;
		break;
	case 'SIDE_ORDER_ROOT':
		index = 4;
		break;
	case 'SIDE_ORDER_GREEN':
		index = 5;
		break;
	default:
		throw 'Unsupported recipe type ' + recipe.type;
		break;
	}
	return index;
}

function mealitemComparator(liA, liB) {
	var recipeA = $(liA).data('recipe');
	var recipeB = $(liB).data('recipe');
	return getRecipeTypeSortIndex(recipeA) < getRecipeTypeSortIndex(recipeB) ? -1 : 1;
}

function recipeComparator(trA, trB) {
	var $trA = $(trA);
	var $spanA = $trA.find('.recipe-name');
	var recipeA = $spanA.data('recipe');
	var recipeB = $(trB).find('.recipe-name').data('recipe');
	return getRecipeTypeSortIndex(recipeA) < getRecipeTypeSortIndex(recipeB) ? -1 : 1;
}

function sortMealItems($ul) {
	var $li = $ul.children('li');
	$li.sortElements(mealitemComparator);
}

function showWeeklyMenu($tr) {
	var weeklyMenu = $tr.data('weeklyMenu');
	if ($tr.find('div.daily-menu').size() == 0) {
		var $td = $tr.find('td.weekly-menu-container');
		var weekdays = findConstants('WEEKDAY');
		for ( var i1 = 0; i1 < weekdays.length; i1++) {
			var weeklyMenuDay = findWeeklyMenuDay(weeklyMenu, weekdays[i1].key);
			var $div = $('<div>').html($('#daily-menu-template').html());
			$div.addClass('daily-menu');
			$div.addClass(weekdays[i1].key);

			$div.find('h2.day').text(weekdays[i1].value);
			var $ulLunch = $div.find('div.lunch ul.dish');
			var $ulDinner = $div.find('div.dinner ul.dish');
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
				sortMealItems($ulLunch);
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
				sortMealItems($ulDinner);
			}
			$td.append($div);
		}
		$tr.find('li.mealitem').each(function() {
			makeMealItemDraggable($(this));
		});
		$tr.find('div.dish').droppable({
			accept : "span.recipe-name",
			tolerance : "pointer",
			over : function(event, ui) {
				$(this).css({
					border : '1px dotted red'
				});
			},
			out : function(event, ui) {
				$(this).css({
					border : 'none'
				});
			},
			drop : function(event, ui) {
				var recipe = ui.draggable.data('recipe');
				var $ul = $(this).find('ul.dish');
				$ul.find('li.empty').remove();
				var $li = addMealItem($ul, recipe.key);
				sortMealItems($ul);
				makeMealItemDraggable($li);
				$(this).css({
					border : 'none'
				});
			}
		});
	}
	$tr.find('div.daily-menu').show();
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
	if ($tr.hasClass('collapsed')) {
		$tr.find('.expanded-state').html(EXPANDED_ICON);
		$tr.removeClass('collapsed');
		$tr.addClass('expanded');
		showWeeklyMenu($tr);
	} else {
		$tr.find('.expanded-state').html(COLLAPSED_ICON);
		$tr.addClass('collapsed');
		$tr.removeClass('expanded');
		$tr.find('div.daily-menu').hide();
	}
}