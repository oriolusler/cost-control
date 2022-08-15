$(function () {
    const costRegisterForm = document.getElementById("costRegistrationId");
    const categoriesSelector = document.getElementById("categoryCostRegister");
    const subcategoriesSelector = document.getElementById("subcategoryCostRegister");
    const isPendingToPayCheckbox = document.getElementById("isPendingToPayCostRegister")
    const pendingToPayAmountDiv = document.getElementById("pendingToPayAmountCostRegisterDiv")

    window.addEventListener("load", () => {
        costRegisterForm.reset();
        emptyAmountField();
        checkIsPendingToPayCheckbox()
    });

    categoriesSelector.addEventListener("change", function () {
        const currentCategory = getCurrentCategoryValue();
        const subcategories = getSubcategoriesFor(currentCategory);

        clear(subcategoriesSelector)
        createSubcategoryDefaultOption(subcategoriesSelector);
        createSubcategoryOptions(subcategories);
    });

    isPendingToPayCheckbox.addEventListener("change", function () {
        checkIsPendingToPayCheckbox()
    });

    function checkIsPendingToPayCheckbox() {
        const currentStatus = isPendingToPayCheckbox.checked

        isPendingToPayCheckbox.value = currentStatus;

        if (currentStatus === true) {
            showElement(pendingToPayAmountDiv)
        } else {
            hideElement(pendingToPayAmountDiv)
        }
    }

    function hideElement(element) {
        element.style.display = "none";
    }

    function showElement(element) {
        element.style.display = "block";
    }

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
});