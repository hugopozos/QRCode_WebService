let scanner = new Instascan.Scanner({ video: document.getElementById('preview') });
scanner.addListener('scan', function(content) {
    // Aquí puedes hacer lo que necesites con el contenido del código QR escaneado
    console.log(content);
    scanner.stop();

    // Realiza una petición a la API para obtener los datos del QR desencriptado
    // Convierte los datos en strings y envíalos a través de la petición POST
    let dataString = JSON.stringify({ qrData: String(content) });
    console.log(dataString); // Agrega una línea de registro para imprimir la cadena dataString
    fetch('/api/leer_qrcode', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: dataString
    })
        .then(response => response.json())
        .then(data => {
            // Aquí puedes hacer lo que necesites con los datos obtenidos de la API
            console.log(data)

            // Obtener el elemento div creado en el HTML y asignarle el valor de qrDataDesencriptado
            const qrDataDesencriptadoDiv = document.getElementById('qrDataDesencriptado');
            qrDataDesencriptadoDiv.innerHTML = `Hola: ${data.qrDataDesencriptado}`;
        })
        .catch(error => {
            console.error(error);
        });
});
Instascan.Camera.getCameras().then(function(cameras) {
    if (cameras.length > 0) {
        scanner.start(cameras[0]);
    } else {
        console.error('No se encontraron cámaras disponibles.');
    }
}).catch(function(e) {
    console.error(e);
});