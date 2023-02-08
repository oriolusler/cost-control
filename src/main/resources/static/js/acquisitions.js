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
        type: 'bar',
        data: {
            datasets: [
                {
                    label: 'Earned',
                    data: [income],
                    borderColor: "#4bb6fa",
                    backgroundColor: "#9cd0f6",
                    borderWidth: 2
                },
                {
                    label: 'Spent',
                    data: [expenses],
                    borderColor: "#fa2e2e",
                    backgroundColor: "#fc8e9c",
                    borderWidth: 2
                },
                {
                    label: 'Invest',
                    data: [invested],
                    borderColor: "#2ec931",
                    backgroundColor: "#c4ffa5",
                    borderWidth: 2
                }
            ]
        }
    }
);


new Chart(document.getElementById('acquisitions1'), {
        type: 'bar',
        data: {
            labels: categoryMonthBalance.map(row => row.category),
            datasets: [
                {
                    label: 'By categories',
                    data: categoryMonthBalance.map(row => row.value)
                }
            ]
        }
    }
);


