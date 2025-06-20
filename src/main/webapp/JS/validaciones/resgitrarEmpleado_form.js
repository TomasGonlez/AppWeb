// === Inicialización principal ===
function initRegistroEmpleadoForm() {
    setupRutFormatter();
    setupFormValidation();
    setupToastAutoHide();
}

// === Formatear y validar RUT en tiempo real ===
function setupRutFormatter() {
    const rutInput = document.getElementById('rutPersona');
    if (!rutInput) return;

    rutInput.addEventListener('input', function () {
        formatearYValidarRut(this);
    });
}

// === Validación antes de enviar el formulario ===
function setupFormValidation() {
    const form = document.getElementById('registroForm');
    if (!form) return;

    form.addEventListener('submit', function (event) {
        const rutInput = document.getElementById('rutPersona');

        if (rutInput.value.length < 11) {
            event.preventDefault();
            rutInput.classList.add('is-invalid');
            mostrarError('El RUT debe tener al menos 11 caracteres (ej: 12.345.678-9)');
        }
    });
}

// === Formateo y validación del RUT ===
function formatearYValidarRut(input) {
    let valor = input.value.replace(/[^0-9kK]/g, '');
    if (valor.length > 9) {
        valor = valor.substring(0, 9);
    }

    if (valor.length === 0) {
        input.value = '';
        return;
    }

    let cuerpo = valor.slice(0, -1);
    let dv = valor.slice(-1).toUpperCase();

    let cuerpoFormateado = '';
    for (let i = cuerpo.length - 1, j = 1; i >= 0; i--, j++) {
        cuerpoFormateado = cuerpo[i] + cuerpoFormateado;
        if (j % 3 === 0 && i !== 0) {
            cuerpoFormateado = '.' + cuerpoFormateado;
        }
    }

    input.value = cuerpoFormateado + '-' + dv;

    if (input.value.length < 11) {
        input.classList.add('is-invalid');
    } else {
        input.classList.remove('is-invalid');
    }
}

// === Toasts: Auto ocultar ===
function setupToastAutoHide() {
    const toastError = document.getElementById('toastError');
    const toastSuccess = document.getElementById('toastExito');

    [toastError, toastSuccess].forEach(toast => {
        if (toast && toast.classList.contains('show')) {
            setTimeout(() => {
                toast.classList.remove('show');
            }, 3000);
        }
    });
}

// === Mostrar error personalizado ===
function mostrarError(mensaje) {
    let toastContainer = document.getElementById('toastError');

    if (!toastContainer) {
        toastContainer = document.createElement('div');
        toastContainer.id = 'toastError';
        toastContainer.className = 'toast toast-error show';
        document.body.appendChild(toastContainer);
    }

    toastContainer.textContent = mensaje;
    toastContainer.classList.add('show');

    setTimeout(() => {
        toastContainer.classList.remove('show');
    }, 3000);
}

// === Ejecutar cuando el DOM esté listo ===
document.addEventListener('DOMContentLoaded', initRegistroEmpleadoForm);
