let productos = [];
let productoAEliminar = null;

document.addEventListener("DOMContentLoaded", cargarProductosDesdeAPI);

async function cargarProductosDesdeAPI() {
  try {
    const respuesta = await fetch("http://localhost:8080/api/productos");
    productos = await respuesta.json();
    guardarEnLocalStorage();
    renderizarTabla();
  } catch (error) {
    console.error("Error al cargar productos:", error);
  }
}

function guardarEnLocalStorage() {
  localStorage.setItem("productos", JSON.stringify(productos));
}
function consultaAvanzada() {
  const form = document.getElementById("formConsultaAvanzada");
  const data = new FormData(form);
  const params = new URLSearchParams(data).toString();

  fetch(`/api/productos/consulta-avanzada?${params}`)
    .then((res) => res.json())
    .then((data) => {
      productos = data;
      renderizarTabla();

      bootstrap.Modal.getInstance(document.getElementById("modalConsultaAvanzada")).hide();

      const mensaje = document.getElementById("mensajeBusqueda");
      mensaje.textContent = productos.length === 0
        ? "No se encontraron productos con los criterios especificados."
        : "";
    })
    .catch((err) => {
      console.error("Error en consulta avanzada:", err);
      document.getElementById("mensajeBusqueda").textContent = "Error en búsqueda: campo o valor inválido.";
    });
}

function renderizarTabla() {
  const cuerpo = document.getElementById("cuerpoProductos");
  cuerpo.innerHTML = "";
  productos.forEach((producto, index) => {
    const fila = document.createElement("tr");
    fila.innerHTML = `
      <td class="col-id">${producto.id}</td>
      <td class="col-nombre">${producto.nombre}</td>
      <td class="col-categoria">${producto.categoria}</td>
      <td class="col-descripcion">${producto.descripcion}</td>
      <td class="col-precio">S/ ${parseFloat(producto.precio).toFixed(2)}</td>
      <td class="col-stock">${producto.stock}</td>
      <td class="col-imagen"><img src="${
        producto.imagen || ""
      }" alt="Producto" style="width:50px;height:auto;"></td>
      <td class="col-acciones">
        <button class="btn btn-warning btn-sm" onclick="editarProducto(${index})"><i class="fas fa-edit"></i></button>
        <button class="btn btn-danger btn-sm" onclick="prepararEliminar(${index})" data-bs-toggle="modal" data-bs-target="#modalEliminarProducto"><i class="fas fa-trash"></i></button>
      </td>
    `;
    cuerpo.appendChild(fila);
  });
  actualizarResultados();
}

function actualizarResultados() {
  const filas = document.querySelectorAll("#tablaProductos tbody tr");
  const resultado = document.getElementById("resultadoCantidad");
  if (resultado) resultado.textContent = filas.length;
}

function editarProducto(index) {
  const producto = productos[index];
  document.getElementById("nombre").value = producto.nombre;
  document.getElementById("categoria").value = producto.categoria;
  document.getElementById("descripcionProd").value = producto.descripcion;
  document.getElementById("precio").value = producto.precio;
  document.getElementById("stock").value = producto.stock;
  document.getElementById("productoId").value = producto.id;

  const modal = new bootstrap.Modal(document.getElementById("modalProducto"));
  modal.show();
}

function prepararEliminar(index) {
  productoAEliminar = index;
}

document
  .getElementById("confirmarEliminarProducto")
  .addEventListener("click", async () => {
    if (productoAEliminar !== null) {
      const producto = productos[productoAEliminar];
      try {
        await fetch(`http://localhost:8080/api/productos/${producto.id}`, {
          method: "DELETE",
        });

        productos.splice(productoAEliminar, 1);
        guardarEnLocalStorage();
        renderizarTabla();
        productoAEliminar = null;

        const modal = bootstrap.Modal.getInstance(
          document.getElementById("modalEliminarProducto")
        );
        modal.hide();
      } catch (error) {
        console.error("Error al eliminar producto:", error);
      }
    }
  });

document
  .getElementById("confirmarGuardarProducto")
  .addEventListener("click", async () => {
    try {
      const nombre = document.getElementById("nombre").value;
      const categoria = document.getElementById("categoria").value;
      const descripcion = document.getElementById("descripcionProd").value;
      const precio = parseFloat(document.getElementById("precio").value);
      const stock = parseInt(document.getElementById("stock").value);
      const id = document.getElementById("productoId").value;

      const imagen = document.getElementById("imagen").value;
      const productoData = {
        nombre,
        categoria,
        descripcion,
        precio,
        stock,
        imagen,
      };

      const url = id
        ? `http://localhost:8080/api/productos/${id}`
        : "http://localhost:8080/api/productos";

      const metodo = id ? "PUT" : "POST";

      const response = await fetch(url, {
        method: metodo,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(productoData),
      });

      if (!response.ok) throw new Error("Error al guardar el producto");

      bootstrap.Modal.getInstance(
        document.getElementById("modalConfirmarGuardar")
      )?.hide();
      bootstrap.Modal.getInstance(
        document.getElementById("modalProducto")
      )?.hide();

      setTimeout(() => {
        window.location.reload();
      }, 500);
    } catch (error) {
      console.error("Error al guardar el producto:", error);
      alert("Ocurrió un error al guardar el producto.");
    }
  });

document.getElementById("busqueda").addEventListener("input", async () => {
  const valor = document.getElementById("busqueda").value.trim().toLowerCase();
  try {
    const response = await fetch(
      `http://localhost:8080/api/productos/buscar?query=${valor}`
    );
    productos = await response.json();
    renderizarTabla();
  } catch (error) {
    console.error("Error en búsqueda:", error);
  }
});

function generarReporteExcel() {
  window.open("http://localhost:8080/api/productos/reporte", "_blank");
}
