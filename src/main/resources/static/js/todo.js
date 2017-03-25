function agregarUsaurio() {
    "use strict";
    var nombre = document.getElementById("nom").value;
    var apellido = document.getElementById("ape").value;
    var email = document.getElementById("ema").value;
    var password = document.getElementById("pass").value;

    $.post("http://localhost:10070/usaurios/agregar", "nom=" + nombre + "&ape=" + apellido + "&ema=" + email + "&pass=" + password, function (resp) {
        if (resp !== undefined) {
            if (resp === "OK") {
                alert("Usaurio agregado correctamente!");
                var row = document.createElement("tr");
                var cell = document.createElement("td");
                var cellText = document.createTextNode(nombre);
                cell.appendChild(cellText);
                row.appendChild(cell);
                var cell2 = document.createElement("td");
                var cellText2 = document.createTextNode(apellido);
                cell2.appendChild(cellText2);
                row.appendChild(cell2);
                var cell3 = document.createElement("td");
                var cellText3 = document.createTextNode(email);
                cell3.appendChild(cellText3);
                row.appendChild(cell3);
                var cell4 = document.createElement("td");
                var cellText4 = document.createTextNode(password);
                cell4.appendChild(cellText4);
                row.appendChild(cell4);
                document.getElementById("tabla").insertBefore(row, document.getElementById("tbody").value);
            } else {
                alert("Error. Manejate.");
            }
        }
    });
}

function listarUsaurios(){
    "use strict";
    $.get("http://localhost:10070/usaurios/listar", function (respuesta) {
        if (respuesta !== undefined) {
            $.each(respuesta, function (index, e) {
                var usaurio = JSON.parse(e);
                var row = document.createElement("tr");
                var cell = document.createElement("td");
                var cellText = document.createTextNode(usaurio.nombre);
                cell.appendChild(cellText);
                row.appendChild(cell);
                var cell2 = document.createElement("td");
                var cellText2 = document.createTextNode(usaurio.apellido);
                cell2.appendChild(cellText2);
                row.appendChild(cell2);
                var cell3 = document.createElement("td");
                var cellText3 = document.createTextNode(usaurio.email);
                cell3.appendChild(cellText3);
                row.appendChild(cell3);
                var cell4 = document.createElement("td");
                var cellText4 = document.createTextNode(usaurio.password);
                cell4.appendChild(cellText4);
                row.appendChild(cell4);
                document.getElementById("tabla").appendChild(row);
            });
        }
    });
}