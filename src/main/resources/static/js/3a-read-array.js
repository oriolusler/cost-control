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
        type: "POST",
        url: "/register/multi",
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(confirmedImportedCosts)

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
                    <div class="row justify-content-center">
                        <div class="d-flex">
                            <input class="btn btn-success mr-1" id="buttonAddSharedCostGet" type="button"
                                   value="Split cost"
                                   name="buttonShareCost"/>

                            <input class="btn btn-danger mr-1" id="buttonDeleteSharedCostGet" type="button"
                                   value="Delete last split"
                                   name="buttonDeleteCost"/>
                        </div>
                    </div>
                    <div class="row d-flex justify-content-center py-2">
                        <p>
                            <button class="btn btn-primary btn-lg"
                                    type="submit"
                                    value="Submit">
                                Confirm
                            </button>
                        </p>
                    </div>
                </form>
            </div>`);

            let importDateElement = document.getElementById("importDateId" + x);
            let importDescriptionElement = document.getElementById("importDescriptionId" + x);
            let importCommentElement = document.getElementById("importCommentId" + x);
            let importAmountElement = document.getElementById("importAmountId" + x);

            importDateElement.value = date.split("/").reverse().join("-");
            importDescriptionElement.value = description;
            importCommentElement.value = comment;
            importAmountElement.value = amount.replace(",", "")

            const form = document.getElementById("importCostFormId" + x)
            form.addEventListener('submit', function handleSubmit(event) {
                event.preventDefault();

                confirmedImportedCosts.push({
                    "date": importDateElement.value,
                    "description": importDescriptionElement.value,
                    "comment": importCommentElement.value,
                    "amount": importAmountElement.value,
                })

                swiper.slideNext();
                swiper.removeSlide(swiper.activeIndex - 1);

                if (allCostsAreConfirmed()) {
                    removeAllSlides()
                    processAndSendCosts();
                }
            });
        }
    }
}