$(function () {
    const categoriesSelector = document.getElementById("categoryCostRegister");
    const subcategoriesSelector = document.getElementById("subcategoryCostRegister");

    categoriesSelector.addEventListener("change", function () {
        const currentCategory = getCurrentCategoryValue();
        const subcategories = getSubcategoriesFor(currentCategory);

        clear(subcategoriesSelector)
        createSubcategoryDefaultOption(subcategoriesSelector);
        createSubcategoryOptions(subcategories);
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
});