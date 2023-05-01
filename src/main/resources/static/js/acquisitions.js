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

const pastel = [
    '#e3ffc2',
    '#ffffa1',
    '#ffe2a4',
    '#ffe4c8',
    '#fcc9c3',
    '#fdb0ba',
    '#e9c5e4',
    '#f6e1fc',
    '#c9bfff',
    '#a0cffd',
    '#bff1fa',
    '#a1ffd1',
    '#cafda8',
]

function getCategoryLabels() {
    const result = []
    for (const x in categoryMonthBalance) {
        result.push(categoryMonthBalance[x].category)
    }
    return result
}

function getCategoryValues() {
    const result = []
    for (const x in categoryMonthBalance) {
        result.push(categoryMonthBalance[x].value)
    }
    return result
}

const data = {
    labels: getCategoryLabels(),
    datasets: [
        {
            label: 'Category spent',
            data: getCategoryValues(),
            backgroundColor: pastel.splice(categoryMonthBalance.length - pastel.length)
        }
    ]
};

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
                label: 'Housing',
                data: [housing],
                borderColor: "#faa22e",
                backgroundColor: "#fcd69e",
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
});


new Chart(document.getElementById('acquisitions1'), {
        type: 'polarArea',
        data: data,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Spent by category'
                }
            }
        },
    }
);


