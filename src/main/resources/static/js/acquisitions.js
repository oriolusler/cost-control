const currentMonthValueMap = transformCurrentMonthValue()
const categoryBalancePerMonthMap = transformCategoryBalancePerMonth()

function transformCategoryBalancePerMonth() {
    const af = [];
    for (let x in categoryBalancePerMonth) {
        af.push({
            "category": x, "value": categoryBalancePerMonth[x]
        })
    }
    return af
}

function transformCurrentMonthValue() {
    const af = [];
    for (let x in currentMonthValue) {
        if (x === "true") {
            af.push({
                "status": "Income", "value": currentMonthValue[x]
            })
        } else {
            af.push({
                "status": "Expenses", "value": currentMonthValue[x]
            })
        }
    }
    return af
}

new Chart(document.getElementById('acquisitions'), {
    type: 'bar', data: {
        labels: currentMonthValueMap.map(row => row.status), datasets: [{
            label: 'Overall', data: currentMonthValueMap.map(row => row.value)
        }]
    }
});


new Chart(document.getElementById('acquisitions1'), {
    type: 'bar', data: {
        labels: categoryBalancePerMonthMap.map(row => row.category), datasets: [{
            label: 'By categories', data: categoryBalancePerMonthMap.map(row => row.value)
        }]
    }
});


