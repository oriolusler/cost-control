$(function () {
    const costRegisterForm = document.getElementById("costRegistrationId");
    const categoriesSelector = document.getElementById("categoryCostRegister");
    const subcategoriesSelector = document.getElementById("subcategoryCostRegister");
    const buttonAddSharedCost = document.getElementById("buttonAddSharedCost");

    window.addEventListener("load", () => {
        costRegisterForm.reset();
        emptyAmountField();
        setDefaultCategoriesSelectorsOption();
    });

    categoriesSelector.addEventListener("change", function () {
        const currentCategory = getCurrentCategoryValue();
        const subcategories = getSubcategoriesFor(currentCategory);

        clear(subcategoriesSelector)
        createSubcategoryDefaultOption(subcategoriesSelector);
        createSubcategoryOptions(subcategories);
    });

    buttonAddSharedCost.addEventListener("click", function () {
        const table = document.getElementById('sharedCosts');
        let length = table.rows.length - 1;
        const nextLength = length++;

        const nextSharedName = "shared[" + nextLength + "]";
        const nextSharedId = "shared" + nextLength + "";

        const cellDebtorHtml = "<td><input class=form-control name='" + nextSharedName + ".debtor' id='" + nextSharedId + ".debtor'></td>"
        const cellAmountHtml = "<td><input type=number step=any class=form-control name='" + nextSharedName + ".amount' id='" + nextSharedId + ".amount'></td>"
        const cellPaidHtml = "<td class=centered><div><input type=checkbox class=form-check-input name='" + nextSharedName + ".paid' id='" + nextSharedId + ".paid'></div></td>"

        const newRow = table.insertRow(table.rows.length);
        const newCellDebtor = newRow.insertCell(0);
        const newCellAmount = newRow.insertCell(1);
        const newCellPaid = newRow.insertCell(2);

        newCellPaid.classList.add("centered");

        newCellDebtor.innerHTML = cellDebtorHtml;
        newCellAmount.innerHTML = cellAmountHtml;
        newCellPaid.innerHTML = cellPaidHtml;
    });

    function getSubcategoriesFor(value) {
        return categoriesMap[value];
    }

    function createSubcategoryOptions(subcategories) {
        let option, i;
        for (i = 0; i < subcategories.length; i++) {
            option = document.createElement('option');
            option.value = option.text = subcategories[i];
            subcategoriesSelector.add(option);
        }
    }

    function getCurrentCategoryValue() {
        return categoriesSelector.options[categoriesSelector.selectedIndex].value;
    }

    function createSubcategoryDefaultOption(subcategoriesSelector) {
        const defaultSubcategoryOption = document.createElement('option');
        defaultSubcategoryOption.selected = true
        defaultSubcategoryOption.disabled = true
        defaultSubcategoryOption.text = "Select a subcategory"
        subcategoriesSelector.add(defaultSubcategoryOption);
    }

    function clear(list) {
        while (list.options.length) {
            list.remove(0);
        }
    }

    function emptyAmountField() {
        document.getElementById("amountCostRegister").value = ''
    }

    function setDefaultCategoriesSelectorsOption() {
        categoriesSelector.selectedIndex = 0;
        subcategoriesSelector.selectedIndex = 0;
    }
});