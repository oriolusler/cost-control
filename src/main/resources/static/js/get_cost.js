$(function () {


    const categoriesSelector = document.getElementById("categoryCostGet");
    const subcategoriesSelector = document.getElementById("subcategoryCostGet");

    const buttonAddSharedCost = document.getElementById("buttonAddSharedCostGet");
    const buttonDeleteSharedCost = document.getElementById("buttonDeleteSharedCostGet");

    categoriesSelector.addEventListener("change", function () {
        updateSubcategoryValue();
    });

    window.addEventListener('load', () => {
        setCurrentCategoryOption(categoriesSelector);
        setCurrentSubcategoryOption(subcategoriesSelector);
    });

    document.addEventListener("deviceready", function() {
        setCurrentCategoryOption(categoriesSelector);
        setCurrentSubcategoryOption(subcategoriesSelector);
    }, false);

    buttonDeleteSharedCost.addEventListener("click", function () {
        const table = document.getElementById('sharedCostsGet');
        const length = table.rows.length - 1;
        if (length > 0) {
            table.deleteRow(length);
        }
    });

    buttonAddSharedCost.addEventListener("click", function () {
        const table = document.getElementById('sharedCostsGet');
        let length = table.rows.length - 1;
        const nextLength = length++;

        const nextSharedName = "shared[" + nextLength + "]";
        const nextSharedId = "shared" + nextLength + "";

        const cellDebtorHtml = "<td><input placeholder='Introduce a name' class=form-control name='" + nextSharedName + ".debtor' id='" + nextSharedId + ".debtor'></td>"
        const cellAmountHtml = "<td><input placeholder='Introduce an amount' type=number step=any class=form-control name='" + nextSharedName + ".amount' id='" + nextSharedId + ".amount'></td>"
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

    function updateSubcategoryValue() {
        const currentCategory = getCurrentCategoryValue();
        const subcategories = getSubcategoriesFor(currentCategory);

        clear(subcategoriesSelector);
        createSubcategoryDefaultOption(subcategoriesSelector);
        createSubcategoryOptions(subcategories);
    }

    function createSubcategoryDefaultOption(subcategoriesSelector) {
        const defaultSubcategoryOption = document.createElement('option');
        defaultSubcategoryOption.selected = true
        defaultSubcategoryOption.disabled = true
        defaultSubcategoryOption.text = "Select a subcategory"
        subcategoriesSelector.add(defaultSubcategoryOption);
    }

    function getCurrentCategoryValue() {
        return categoriesSelector.options[categoriesSelector.selectedIndex].value;
    }

    function createSubcategoryOptions(subcategories) {
        let option, i;
        for (i = 0; i < subcategories.length; i++) {
            option = document.createElement('option');
            option.value = subcategories[i].name;
            option.text = subcategories[i].displayName;
            subcategoriesSelector.add(option);
        }
    }

    function getSubcategoriesFor(value) {
        return categoriesMap[value];
    }

    function clear(list) {
        while (list.options.length) {
            list.remove(0);
        }
    }

    function setCurrentCategoryOption(categoriesSelector) {
        for (let i = 0; i < categoriesSelector.length; i++) {
            const option = categoriesSelector.options[i];
            if (option.text === currentCategory) {
                categoriesSelector.value = option.value
                updateSubcategoryValue()
            }
        }
    }

    function setCurrentSubcategoryOption(subcategoriesSelector) {
        for (let i = 0; i < subcategoriesSelector.length; i++) {
            const option = subcategoriesSelector.options[i];
            if (option.text === currentSubcategory) {
                subcategoriesSelector.value = option.value
            }
        }
    }

});