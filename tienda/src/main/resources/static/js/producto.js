const params = new URLSearchParams(window.location.search);
const id = params.get("id");
// Verificar si se proporcionó un ID de producto

// se proporciana la id y se obtiene el producto
if (id) {
    fetch(`http://localhost:8080/api/productos/${id}`)
        .then(res => res.json())
        .then(producto => {
            document.getElementById("nombre-producto").innerText = producto.nombre;
            document.getElementById("imagen-producto").src = producto.imagen;
            document.getElementById("imagen-producto").alt = producto.nombre;
            document.getElementById("precio-producto").innerText = "S/ " + parseFloat(producto.precio).toFixed(2);
            document.getElementById("descripcion-producto").innerText = producto.descripcion;

            // Configurar el botón "Agregar al carrito"
            const btn = document.getElementById("btn-carrito");
            btn.onclick = () => agregarAlCarrito(producto.id);
        })
        .catch(() => {
            document.body.innerHTML = "<h2>Producto no encontrado.</h2>";
        });
} else {
    document.body.innerHTML = "<h2>ID de producto no proporcionado.</h2>";
}
async function agregarAlCarrito(productoId) {
  const cantidad = parseInt(document.getElementById("cbd").value);

  const response = await fetch("http://localhost:8080/api/carrito", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    credentials: "include",
    body: JSON.stringify({ idProducto: productoId, cantidad })
  });

  const result = await response.text();
  if (result === "OK") {
    alert("✅ Producto agregado al carrito");
  } else if (result === "NO_SESSION") {
    alert("⚠️ Por favor, inicia sesión para agregar productos");
    window.location.href = "login.html";
  } else {
    alert("❌ Error al agregar: " + result);
  }
}