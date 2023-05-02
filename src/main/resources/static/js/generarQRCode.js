document.addEventListener('DOMContentLoaded', function() {
    // Agrega el controlador de eventos de teclado a todos los campos de entrada dentro del formulario
    document.querySelector('form').addEventListener('keypress', function(event) {
        if (event.which === 13) {
            event.preventDefault(); // Previene el comportamiento por defecto del Enter (enviar el formulario)
            generarQRCode(); // Llama a la función generarQRCode()
        }
    });
});

async function generarQRCode() {
    let datos = {};
    datos.texto  = document.getElementById('text').value;
    datos.width  = document.getElementById('width').value;
    datos.height = document.getElementById('height').value;


    fetch('/api/qrcode', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al generar código QR');
            }
            return response.text();
        })
        .then(data => {
            console.log(datos);
            // mostrar el código QR generado en el DOM
            const qrCode = document.createElement('img');
            qrCode.src = `data:image/png;base64,${data}`;
            document.body.appendChild(qrCode);
        })
        .catch(error => {
            console.error(error);
            alert('Error al generar código QR');
        });
}
