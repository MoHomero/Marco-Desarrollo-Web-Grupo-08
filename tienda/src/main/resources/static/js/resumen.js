// obtenemos el resumen del carrito
document.addEventListener("DOMContentLoaded", () => {
  // Verifica si el usuario está autenticado
  fetch("http://localhost:8080/api/carrito", {
    method: "GET",
    credentials: "include"
  })
  // son los productos del carrito
    .then(res => res.json())
    .then(items => {
      // mostrar el producto
      const resumenLista = document.getElementById("resumenLista");
      if (!resumenLista) return;
      // suma los productos por precio x cantidad
      let total = 0;
      
      items.forEach(item => {
        const p = item.producto;
        const subtotal = parseFloat(p.precio) * item.cantidad;
        total += subtotal;
        // crea un elemento de lista para cada producto
        const li = document.createElement("li");
        li.className = "list-group-item d-flex justify-content-between lh-sm";
        li.innerHTML = `
          <div>
            <h6 class="my-0">${p.nombre}</h6>
            <small class="text-muted">Cantidad: ${item.cantidad}</small>
          </div>
          <span class="text-muted">S/ ${subtotal.toFixed(2)}</span>
        `;
        resumenLista.appendChild(li);
      });
      // muestra  el total a pagar
      resumenLista.innerHTML += `
        <li class="list-group-item d-flex justify-content-between bg-light">
          <div><strong>Envío</strong></div>
          <span class="text-success">Gratis</span>
        </li>
        <li class="list-group-item d-flex justify-content-between">
          <span><strong>Total</strong></span>
          <strong id="totalResumen" data-total="${total.toFixed(2)}">S/ ${total.toFixed(2)}</strong>
        </li>
      `;
      // Pasa el total al boton de paypal
      inicializarPaypal(total.toFixed(2));
    })
    .catch(err => {
      console.error("Error al cargar el resumen del carrito:", err);
    });
});
// despues de calcular el moto se ejecuta
function inicializarPaypal(monto) {
  //verifica si paypal esta cargando 
  if (!window.paypal) {
    console.error("PayPal SDK no está cargado.");
    return;
  }
 // verifica si el contenedor de PayPal existe
  const container = document.getElementById("paypal-button-container");
  if (!container) {
    console.error("No se encontró el contenedor de PayPal.");
    return;
  }
  
  paypal.Buttons({
    createOrder: function (data, actions) {
      if (!monto || isNaN(monto)) {
        console.error("Monto total inválido:", monto);
        alert("Monto total inválido. Verifica el resumen de compra.");
        throw new Error("Monto total inválido.");
      }
      // cuanto se va a pagar y la moneda
      return actions.order.create({
        purchase_units: [{
          amount: {
            value: parseFloat(monto).toFixed(2),
            currency_code: "USD"
          }
        }]
      });
    },
    // se registra el pago
    onApprove: function (data, actions) {
      return actions.order.capture().then(function (details) {
        const pagoInfo = {
          nombre: details.payer.name.given_name,
          apellido: details.payer.name.surname,
          email: details.payer.email_address,
          id_transaccion: details.id,
          estado: details.status,
          monto: details.purchase_units[0].amount.value,
          moneda: details.purchase_units[0].amount.currency_code
        };

        console.log("Detalles del pago:", pagoInfo);

        fetch("http://localhost:8080/api/pagos", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(pagoInfo)
        })
          .then(res => {
            if (!res.ok) {
              throw new Error("Error al guardar el pago");
            }
            return res.json();
          })
          .then(data => {
            alert("✅ " + data.mensaje);
            
            return fetch("http://localhost:8080/api/pedido/registrar", {
              method: "POST",
              credentials: "include"
            });
          })
          .then(res => {
            if (!res.ok) {
              throw new Error("Error al registrar los pedidos");
            }
            return res.json();
          })
          .then(data => {
            alert("🧾 " + data.mensaje);
            // Redireccionar o actualizar la interfaz
            window.location.href = "/Pedidos.html";
          })
          .catch(error => {
            console.error("Error procesando el pago o pedido:", error);
            alert("❌ Error procesando el pago o registrando el pedido.");
          });
      });
    },
    onError: function (err) {
      console.error("Error de PayPal:", err);
      alert("Hubo un error al procesar el pago.");
    }
  }).render("#paypal-button-container");
}
