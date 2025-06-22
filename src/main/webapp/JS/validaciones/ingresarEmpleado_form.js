// Función principal que se ejecuta al cargar el DOM
function initRegistroEmpleadoForm() {
    setupToggleButton();
    setupRutFormatter();
    setupFormValidation();
    initToasts();
    setTodayDate();
}

// Configurar botón toggle entre INGRESO / SALIDA
function setupToggleButton() {
    const toggleButton = document.getElementById('toggleButton');
    const tipoRegistro = document.getElementById('tipoRegistro');

    if (!toggleButton || !tipoRegistro) return;

    toggleButton.addEventListener('click', function () {
        const isIngreso = toggleButton.textContent === 'INGRESO';
        toggleButton.textContent = isIngreso ? 'SALIDA' : 'INGRESO';
        toggleButton.classList.toggle('salida', isIngreso);
        tipoRegistro.value = isIngreso ? 'SALIDA' : 'INGRESO';
    });
}

// Formatear y validar RUT en tiempo real
function setupRutFormatter() {
    const rutInput = document.getElementById('rutPersona');
    if (!rutInput) return;

    rutInput.addEventListener('input', function () {
        let valor = rutInput.value.replace(/[^0-9kK]/g, '');

        if (valor.length > 9) valor = valor.substring(0, 9);
        if (valor.length === 0) {
            rutInput.value = '';
            return;
        }

        const cuerpo = valor.slice(0, -1);
        const dv = valor.slice(-1).toUpperCase();

        let cuerpoFormateado = '';
        for (let i = cuerpo.length - 1, j = 1; i >= 0; i--, j++) {
            cuerpoFormateado = cuerpo[i] + cuerpoFormateado;
            if (j % 3 === 0 && i !== 0) cuerpoFormateado = '.' + cuerpoFormateado;
        }

        rutInput.value = cuerpoFormateado + '-' + dv;

        // Validar longitud
        rutInput.classList.toggle('is-invalid', rutInput.value.length < 11);
    });
}

// Validar formulario al enviar
function setupFormValidation() {
    const form = document.getElementById('registroForm');
    if (!form) return;

    form.addEventListener('submit', function (event) {
        const rutInput = document.getElementById('rutPersona');
        if (!rutInput || rutInput.value.length < 11) {
            event.preventDefault();
            rutInput.classList.add('is-invalid');
            showErrorToast('El RUT debe tener al menos 11 caracteres (ej: 12.345.678-9)');
        }
    });
}

// Inicializar cierre automático de toasts existentes
function initToasts() {
    ['toastError', 'toastExito'].forEach(toastId => {
        const toast = document.getElementById(toastId);
        if (toast) {
            setTimeout(() => {
                toast.classList.remove('show');
            }, 3000);
        }
    });
}

// Mostrar toast de error personalizado
function showErrorToast(mensaje) {
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
function setTodayDate() {
    const fechaInput = document.getElementById('fechaPersona');
    if (!fechaInput) return;

    const today = new Date();
    const yyyy = today.getFullYear();
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // Meses van de 0-11
    const dd = String(today.getDate()).padStart(2, '0');

    fechaInput.value = `${yyyy}-${mm}-${dd}`;
    //`${dd}-${mm}-${yyyy}`
}
// Ejecutartodo cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', initRegistroEmpleadoForm);