
document.addEventListener("DOMContentLoaded", () => {
    fetch("http://localhost:8080/api/usuario/autenticado", {
        credentials: "include" // cockies para sesion
    })
    .then(res => res.json())
    .then(data => {
        if (data.autenticado) {
            // Cambia el texto del enlace al nombre de usuario
            document.getElementById("usuarioNombre").textContent = data.username;
        } else {
            // Si no está autenticado, redirige al login o muestra "Invitado"
            document.getElementById("usuarioNombre").textContent = "Invitado";
        }
    })
    .catch(err => {
        console.error("Error al obtener usuario:", err);
        document.getElementById("usuarioNombre").textContent = "Invitado";
    });
});

document.addEventListener("DOMContentLoaded", () => {
    fetch("http://localhost:8080/api/usuario/autenticado", {
        credentials: "include"
    })
    .then(response => response.json())
    .then(data => {
        const usuarioNombre = document.getElementById("usuarioNombre");
        if (data.autenticado && usuarioNombre) {
            usuarioNombre.innerText = data.username || "Usuario";
        }

        const roles = data.roles || [];
        const esAdmin = roles.some(rol => rol.authority === "ROLE_ADMIN");

        if (!esAdmin) {
            // Oculta el botón si no es admin
            const gestion = document.querySelector('a[href="index.html"]');
            if (gestion) gestion.parentElement.style.display = "none";
        }
    })
    .catch(error => console.error("Error al obtener datos del usuario:", error));
});

