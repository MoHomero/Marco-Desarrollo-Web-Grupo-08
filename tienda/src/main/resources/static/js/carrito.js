document.addEventListener("DOMContentLoaded", () => {
  fetch("http://localhost:8080/api/carrito", {
    method: "GET",
    credentials: "include"
  })
    .then(res => res.json())
    .then(items => {
      const contenedor = document.getElementById("carritoItems");
      let total = 0;

      items.forEach(item => {
        const p = item.producto;
        const subtotal = parseFloat(p.precio) * item.cantidad;
        total += subtotal;

        const div = document.createElement("div");
        div.className = "carrito-producto item";
        div.setAttribute("data-precio", p.precio);

        div.innerHTML = `
          <img src="${p.imagen}" alt="${p.nombre}" class="carrito-imagen">
          <div class="carrito-info">
            <h5 class="carrito-nombre">${p.nombre}</h5>
            <p class="carrito-vendedor">Vendido por TiendaTec</p>
          </div>
          <p class="carrito-precio">S/ ${parseFloat(p.precio).toFixed(2)}</p>
          <div class="carrito-cantidad">
            <input type="number" class="input-cantidad" value="${item.cantidad}" min="1" data-id="${item.id}">
          </div>
          <div class="carrito-eliminar">
            <button class="boton-eliminar" data-id="${item.id}"><i class="fas fa-trash-alt"></i></button>
          </div>
        `;

        contenedor.appendChild(div);
      });

      document.getElementById("subtotal").textContent = `S/ ${total.toFixed(2)}`;
      document.getElementById("total").textContent = `S/ ${total.toFixed(2)}`;
    })
    .catch(err => {
      console.error("Error al cargar el carrito:", err);
    });
});
document.addEventListener("click", async (e) => {
  if (e.target.closest(".boton-eliminar")) {
    const button = e.target.closest(".boton-eliminar");
    const id = button.getAttribute("data-id");

    if (!confirm("¿Eliminar este producto del carrito?")) return;

    const res = await fetch(`http://localhost:8080/api/carrito/${id}`, {
      method: "DELETE",
      credentials: "include"
    });

    const result = await res.text();
    if (result === "OK") {
      button.closest(".carrito-producto").remove();
      location.reload(); // o actualiza el total manualmente si prefieres
    } else {
      alert("No se pudo eliminar el producto");
    }
  }
});