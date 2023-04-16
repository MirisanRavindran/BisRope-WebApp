const callURL="http://localhost:8080/lab5-1.0/api/students/json";
console.log("Loaded data from " + callURL);

window.onload = function requestData() {
    fetch(callURL, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        },
    })
        .then(response => response.json())
        .then(response => add_records(response))
        .catch((err) => {
            console.log("something went wrong: " + err);
        });
}

function add_records(data) {
    let tableRef = document.getElementById("chart-body");

    for (let i = 0; i < data.students.length; i++) {
        let newRow = tableRef.insertRow();
        let nameCell = newRow.insertCell();
        let idCell = newRow.insertCell();
        let gpaCell = newRow.insertCell();

        nameCell.textContent = data.students[i].name;
        idCell.textContent = data.students[i].id;
        gpaCell.textContent = data.students[i].gpa;
    }
}

function manual_add_record() {
    const name = document.getElementById("name").value;
    const id = document.getElementById("id").value;
    const gpa = document.getElementById("gpa").value;

    // Check if any of the input values are empty
    if (name === "" || id === "" || gpa === "") {
        return; // Do nothing
    }
    else {
        const row = document.createElement("tr");

        const nameCell = document.createElement("td");
        nameCell.textContent = name;
        row.appendChild(nameCell);

        const idCell = document.createElement("td");
        idCell.textContent = id;
        row.appendChild(idCell);

        const gpaCell = document.createElement("td");
        gpaCell.textContent = gpa;
        row.appendChild(gpaCell);

        const tableBody = document.querySelector("table tbody");
        tableBody.appendChild(row);
    }
}