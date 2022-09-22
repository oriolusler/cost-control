const confirmedImportedCosts = []

const swiper = new Swiper(".mySwiper", {
    pagination: {
        el: ".swiper-pagination", dynamicBullets: true,
    }, navigation: {
        nextEl: ".swiper-button-next", prevEl: ".swiper-button-prev",
    },
});

document.getElementById("demo").addEventListener("change", handleFileAsync, false);

function removeAllSlides() {
    swiper.removeAllSlides()
}

function allCostsAreConfirmed() {
    return swiper.isLocked;
}

function processAndSendCosts() {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        type: "POST", url: "/register/multi", beforeSend: function (request) {
            request.setRequestHeader(header, token);
        }, contentType: "application/json", dataType: "json", data: JSON.stringify(confirmedImportedCosts)

    });
}

async function handleFileAsync(e) {
    removeAllSlides();

    const file = e.target.files[0];
    const data = await file.arrayBuffer();
    const workbook = XLSX.read(data);
    const first_ws = workbook.Sheets[workbook.SheetNames[0]];
    const excelCostArray = XLSX.utils.sheet_to_json(first_ws, {header: 1, raw: false});

    for (const x in excelCostArray) {
        const date = excelCostArray[x][0]
        const description = excelCostArray[x][2]
        const comment = excelCostArray[x][3]
        const amount = excelCostArray[x][4]

        if (description && amount) {
            swiper.appendSlide(`         
            <div class="swiper-slide justify-content-center" style="padding-top: 20px;padding-bottom: 30px">
                <form id="importCostFormId` + x + `" style="width: 75%">
                    <div class="row form-outline mb-4">
                        <input type="date" id="importDateId` + x + `" class="form-control form-control-lg" placeholder="Select a day" value=/>
                    </div>
                    <div class="row form-outline mb-4">
                        <input type="text" id="importDescriptionId` + x + `" class="form-control form-control-lg" placeholder="Write a description"/>
                    </div>
                    <div class="row form-outline mb-4">
                        <input type="text" id="importCommentId` + x + `" class="form-control form-control-lg" placeholder="Write a comment"/>
                    </div>
                    <div class="row form-outline mb-4">
                        <input type="number" step="any" id="importAmountId` + x + `" class="form-control form-control-lg" placeholder="Introduce the amount"/>
                    </div>
                    <div class="row form-outline mb-4">
                        <select id="importCategoryId` + x + `" class="form-control form-control-lg"">
                            <option value="" disabled selected>Select a category</option>
                        </select>
                    </div>
                    <div class="row form-outline mb-4">
                        <select id="importSubcategoryId` + x + `" class="form-control form-control-lg">
                            <option value="" disabled selected>Select a subcategory</option>
                        </select>
                    </div>
                    <div class="row justify-content-center">
                        <div class="table-responsive">
                            <table class="table table-striped" id="importSharedCosts` + x + `">
                                <tr>
                                    <th>Who?</th>
                                    <th>How much?</th>
                                    <th>Paid?</th>
                                </tr>
                            </table>
                        </div>
                        <div class="d-flex">
                            <input class="btn btn-success mr-1" id="importAddSharedButton` + x + `" type="button"
                                   value="Split cost"
                                   name="buttonShareCost"/>

                            <input class="btn btn-danger mr-1" id="importDeleteSharedButton` + x + `" type="button"
                                   value="Delete last split"
                                   name="buttonDeleteCost"/>
                        </div>
                    </div>
                    <div class="row d-flex justify-content-center py-2">
                        <p>
                            <button class="btn btn-primary mr-1"
                                    type="submit"
                                    value="Submit">
                                Confirm
                            </button> 
                            <button class="btn btn-danger mr-1"
                             value="Discard"
                              id="importDiscardButton` + x + `">
                                Discard
                            </button>
                        </p>
                    </div>
                </form>
            </div>`);

            let importDateElement = document.getElementById("importDateId" + x);
            let importDescriptionElement = document.getElementById("importDescriptionId" + x);
            let importCommentElement = document.getElementById("importCommentId" + x);
            let importAmountElement = document.getElementById("importAmountId" + x);
            let importCategoryElement = document.getElementById("importCategoryId" + x);
            let importSubcategoryElement = document.getElementById("importSubcategoryId" + x);

            let importDiscardButtonElement = document.getElementById("importDiscardButton" + x);

            let importAddSharedButtonElement = document.getElementById("importAddSharedButton" + x);
            let importDeleteSharedButtonElement = document.getElementById("importDeleteSharedButton" + x);

            let importSharedCostsTableElement = document.getElementById("importSharedCosts" + x);

            const options = document.getElementById("importCategoriesSelectorId").options
            for (let i = 0; i < options.length; i++) {
                const option = options[i]
                importCategoryElement.add(new Option(option.text, option.value, false, false));
            }

            importCategoryElement.addEventListener("change", function () {
                const currentCategory = importCategoryElement.options[importCategoryElement.selectedIndex].value;
                const subcategories = categoriesMap[currentCategory];

                clear(importSubcategoryElement)
                const defaultSubcategoryOption = document.createElement('option');
                defaultSubcategoryOption.selected = true
                defaultSubcategoryOption.disabled = true
                defaultSubcategoryOption.text = "Select a subcategory"
                importSubcategoryElement.add(defaultSubcategoryOption);

                for (let i = 0; i < subcategories.length; i++) {
                    let option = document.createElement('option');
                    option.value = subcategories[i].name;
                    option.text = subcategories[i].displayName;
                    importSubcategoryElement.add(option);
                }
            });

            importDateElement.value = date.split("/").reverse().join("-");
            importDescriptionElement.value = description;
            importCommentElement.value = comment;
            importAmountElement.value = amount.replace(",", "")

            importDeleteSharedButtonElement.addEventListener("click", function () {
                const length = importSharedCostsTableElement.rows.length - 1;
                if (length > 0) {
                    importSharedCostsTableElement.deleteRow(length);
                }
            });

            importAddSharedButtonElement.addEventListener("click", function () {
                let length = importSharedCostsTableElement.rows.length - 1;
                const nextLength = length++;

                const nextSharedName = "shared[" + nextLength + "]";
                const nextSharedId = "shared" + nextLength + "";

                const cellDebtorHtml = "<th><input placeholder='Introduce a name' class=form-control name='" + nextSharedName + ".debtor' id='" + nextSharedId + ".debtor'></th>"
                const cellAmountHtml = "<th><input placeholder='Introduce an amount' type=number step=any class=form-control name='" + nextSharedName + ".amount' id='" + nextSharedId + ".amount'></th>"
                const cellPaidHtml = "<th class=centered><div><input type=checkbox class=form-check-input name='" + nextSharedName + ".paid' id='" + nextSharedId + ".paid'></div></th>"

                const newRow = importSharedCostsTableElement.insertRow(importSharedCostsTableElement.rows.length);
                const newCellDebtor = newRow.insertCell(0);
                const newCellAmount = newRow.insertCell(1);
                const newCellPaid = newRow.insertCell(2);

                newCellPaid.classList.add("centered");

                newCellDebtor.innerHTML = cellDebtorHtml;
                newCellAmount.innerHTML = cellAmountHtml;
                newCellPaid.innerHTML = cellPaidHtml;
            });

            importDiscardButtonElement.addEventListener('click', function handleDiscard(event) {
                event.preventDefault();

                if (allCostsAreConfirmed()) {
                    removeAllSlides()
                    processAndSendCosts();
                } else {
                    swiper.slideNext();
                    swiper.removeSlide(swiper.activeIndex - 1);
                }
            });

            const form = document.getElementById("importCostFormId" + x)
            form.addEventListener('submit', function handleSubmit(event) {
                event.preventDefault();

                let length = importSharedCostsTableElement.rows.length - 1;

                const sharedCosts = []
                for (let i = 0; i < length; i++) {
                    const debtor = document.getElementById("shared" + i + ".debtor").value
                    const amount = document.getElementById("shared" + i + ".amount").value
                    const paid = document.getElementById("shared" + i + ".paid").checked

                    sharedCosts.push({
                        "debtor": debtor,
                        "amount": amount,
                        "paid": paid,
                    })
                }

                confirmedImportedCosts.push({
                    "date": importDateElement.value,
                    "description": importDescriptionElement.value,
                    "comment": importCommentElement.value,
                    "amount": importAmountElement.value,
                    "category": importCategoryElement.value,
                    "subcategory": importSubcategoryElement.value,
                    "shared": sharedCosts
                })

                if (allCostsAreConfirmed()) {
                    removeAllSlides()
                    processAndSendCosts();
                } else {
                    swiper.slideNext();
                    swiper.removeSlide(swiper.activeIndex - 1);
                }
            });
        }
    }
}

function clear(list) {
    while (list.options.length) {
        list.remove(0);
    }
}