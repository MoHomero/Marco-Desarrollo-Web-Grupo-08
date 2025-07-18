document.addEventListener("DOMContentLoaded", () => {
  const urlParams = new URLSearchParams(window.location.search);
  const query = urlParams.get("query");

  fetch(`http://localhost:8080/api/productos/buscar?query=${encodeURIComponent(query)}`)
    .then(res => res.json())
    .then(productos => {
      const contenedor = document.getElementById("productos");
      contenedor.innerHTML = "";

      if (productos.length === 0) {
        contenedor.innerHTML = "<p>No se encontraron productos.</p>";
        return;
      }

      productos.forEach(p => {
        const div = document.createElement("div");
        div.className = "producto";
        div.onclick = () => location.href = `Producto.html?id=${p.id}`;
        div.dataset.categoria = p.categoria;
        div.dataset.precio = p.precio;
        div.dataset.stock = p.stock;
        div.dataset.condicion = p.condicion;
        div.dataset.envio = p.envioGratis; // ✅ CAMBIO AQUÍ

        div.innerHTML = `
          <img src="${p.imagen}" alt="${p.nombre}">
          <div class="product-info">
            <h3>${p.nombre}</h3>
            <p class="price">S/ ${p.precio.toFixed(2)}</p>
            <p>${p.descripcion}</p>
            <p>${p.envioGratis ? '✅ Envío gratis' : '🚚 Envío estándar'}</p> <!-- ✅ CAMBIO AQUÍ -->
          </div>
        `;
        contenedor.appendChild(div);
      });

      aplicarFiltros();
    });

  // Listeners de filtros
  document.querySelectorAll(
    '.filtro-categoria, .filtro-precio, .filtro-condicion, #disponibles, #filtro-envio, #min, #max'
  ).forEach(el => el.addEventListener('change', aplicarFiltros));
});


function aplicarFiltros() {
  const productos = document.querySelectorAll('.producto');

  const disponibles = document.getElementById('disponibles')?.checked;
  const envioGratis = document.getElementById('filtro-envio')?.checked;

  const categorias = Array.from(document.querySelectorAll('.filtro-categoria:checked')).map(cb => cb.value);
  const condiciones = Array.from(document.querySelectorAll('.filtro-condicion:checked')).map(cb => cb.value);
  const rangos = Array.from(document.querySelectorAll('.filtro-precio:checked')).map(cb => cb.value);

  const minPrecio = parseFloat(document.getElementById('min')?.value) || 0;
  const maxPrecio = parseFloat(document.getElementById('max')?.value) || Infinity;

  productos.forEach(producto => {
    const categoria = producto.dataset.categoria;
    const precio = parseFloat(producto.dataset.precio);
    const stock = parseInt(producto.dataset.stock);
    const condicion = producto.dataset.condicion;
    const envio = producto.dataset.envio === "true";

    const cumpleCategoria = categorias.length === 0 || categorias.includes(categoria);
    const cumpleCondicion = condiciones.length === 0 || condiciones.includes(condicion);
    const cumpleDisponibilidad = !disponibles || stock > 0;
    const cumpleEnvio = !envioGratis || envio;
    const cumplePrecioCustom = precio >= minPrecio && precio <= maxPrecio;

    let cumplePrecioRango = true;
    if (rangos.length > 0) {
      cumplePrecioRango = rangos.some(rango => {
        if (rango === "hasta-400") return precio <= 400;
        if (rango === "900-1500") return precio >= 900 && precio <= 1500;
        if (rango === "mas-2000") return precio > 2000;
        return true;
      });
    }

    const mostrar = cumpleCategoria && cumpleCondicion && cumpleDisponibilidad &&
                    cumpleEnvio && cumplePrecioCustom && cumplePrecioRango;

    producto.style.display = mostrar ? "" : "none";
  });
}
