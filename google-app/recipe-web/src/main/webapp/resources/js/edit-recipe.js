function afterNewIngredientAdded() {
	afterNewItemAdded($(editRecipeIngredientsTable.jqId));
}
function afterNewStepAdded() {
	afterNewItemAdded($(editRecipeStepsTable.jqId));
}

function afterNewItemAdded($table) {
	var $lastRow = $table.find('tbody tr').last();
	$lastRow.find('span.ui-icon-pencil').last().click();
	focusAndSelect($lastRow);
}

function editingIngredient() {
	editingItem($(editRecipeIngredientsTable.jqId));
}
function editingStep() {
	editingItem($(editRecipeStepsTable.jqId));
}
function editingItem($table) {
	focusAndSelect($table.find('tbody tr.ui-row-editing'));
}
function focusAndSelect($tr) {
	var $inputfield = $tr.find('input[type="text"]').filter(':visible').first();
	$inputfield.focus();
	$inputfield.select();

}
