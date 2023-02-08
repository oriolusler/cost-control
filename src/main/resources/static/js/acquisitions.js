const generalMonthBalance = getGeneralMonthBalance()
const categoryMonthBalance = transformCategoryBalancePerMonth()

function transformCategoryBalancePerMonth() {
    const result = [];
    for (let x in categoryBalancePerMonth) {
        result.push({
            "category": x, "value": categoryBalancePerMonth[x]
        })
    }
    return result
}

function getGeneralMonthBalance() {
    const result = [];
    result.push({"status": "Expenses", "value": expenses})
    result.push({"status": "Income", "value": income})
    result.push({"status": "Invested", "value": invested})
    return result
}

new Chart(document.getElementById('acquisitions'), {
    type: 'bar', data: {
        labels: generalMonthBalance.map(row => row.status), datasets: [{
            label: 'Overall', data: generalMonthBalance.map(row => row.value)
        }]
    }
});


new Chart(document.getElementById('acquisitions1'), {
    type: 'bar', data: {
        labels: categoryMonthBalance.map(row => row.category), datasets: [{
            label: 'By categories', data: categoryMonthBalance.map(row => row.value)
        }]
    }
});


