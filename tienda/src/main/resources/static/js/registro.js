document.getElementById("formRegistro").addEventListener("submit", async function(event) {
    event.preventDefault();

    const username = document.querySelector("input[name='username']").value;
    const correo = document.querySelector("input[name='correo']").value;
    const telefono = document.querySelector("input[name='telefono']").value;
    const password = document.querySelector("input[name='password']").value;
    const confirmar = document.querySelector("input[name='confirmar_password']").value;
    const alerta = document.getElementById("alerta");

    if (password !== confirmar) {
        alerta.classList.remove("d-none");
        alerta.textContent = "Las contraseñas no coinciden.";
        return;
    }

    alerta.classList.add("d-none");

  try {
        const response = await fetch("http://localhost:8080/api/registro", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username,
                password,
                correo,
                telefono
            })
        });

        const resultado = await response.text(); // Obtiene el cuerpo de la respuesta como texto

        if (response.ok) { // Esto verifica si el status code está en el rango 2xx (ej. 201 Created)
            alerta.classList.remove("d-none");
            alerta.classList.add("alert-success");
            alerta.classList.remove("alert-danger");
            alerta.textContent = resultado; // Mostrará "Usuario registrado exitosamente como ROLE_USER."
            setTimeout(() => {
                window.location.href = "Login.html";
            }, 2000);
        } else { // Esto se ejecutará para cualquier status code que no sea 2xx (ej. 400, 409)
            alerta.classList.remove("d-none");
            alerta.classList.add("alert-danger");
            alerta.classList.remove("alert-success");
            alerta.textContent = resultado; // Mostrará el mensaje de error del backend
        }
    } catch (error) {
        console.error('Error al intentar registrar:', error);
        alerta.classList.remove("d-none");
        alerta.classList.add("alert-danger");
        alerta.classList.remove("alert-success");
        alerta.textContent = "No se pudo conectar con el servidor para registrar el usuario.";
    }
});