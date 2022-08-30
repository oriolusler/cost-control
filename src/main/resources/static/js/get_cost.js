$(function () {
    const categoriesSelector = document.getElementById("categoryCostGet");
    const subcategoriesSelector = document.getElementById("subcategoryCostGet");

    categoriesSelector.addEventListener("change", function () {
        updateSubcategoryValue();
    });

    window.addEventListener("load", () => {
        setCurrentCategoryOption(categoriesSelector);
        setCurrentSubcategoryOption(subcategoriesSelector);
    });


    function updateSubcategoryValue() {
        const currentCategory = getCurrentCategoryValue();
        const subcategories = getSubcategoriesFor(currentCategory);

        clear(subcategoriesSelector)
        createSubcategoryOptions(subcategories);
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