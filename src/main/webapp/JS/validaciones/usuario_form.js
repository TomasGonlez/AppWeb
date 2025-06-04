/**
 * Validaciones para el formulario de creación de usuario
 * Se ejecuta cuando el DOM está completamente cargado (gracias al atributo 'defer')
 */

// Función principal
function initUsuarioForm() {
    const form = document.getElementById('formUsuario');
    if (!form) return;

    // Configurar validación en tiempo real
    setupLiveValidation(form);

    // Configurar validación al enviar
    setupSubmitValidation(form);

    // Inicializar toast de errores
    initToast();

    setupPasswordToggle();
}

// Validación mientras el usuario escribe
function setupLiveValidation(form) {
    form.querySelectorAll('input').forEach(input => {
        input.addEventListener('input', function() {
            if (this.checkValidity()) {
                this.classList.remove('is-invalid');
            }
        });
    });
}

// Validación al enviar el formulario
function setupSubmitValidation(form) {
    form.addEventListener('submit', function(event) {
        if (!validateCompleteForm(form)) {
            event.preventDefault();
            showErrorToast('Por favor complete todos los campos correctamente');
        }
    });
}

// Validación completa del formulario
function validateCompleteForm(form) {
    let isValid = true;
    const campos = ['nombreCompletoUser', 'correoUser', 'numeroUser', 'nombreUser', 'contrasena'];

    campos.forEach(campoId => {
        const input = document.getElementById(campoId);
        if (!input.checkValidity()) {
            input.classList.add('is-invalid');
            isValid = false;
        }
    });

    // Validación adicional para contraseña
    const contrasena = document.getElementById('contrasena').value;
    if (!/(?=.*[A-Za-z])(?=.*\d)/.test(contrasena)) {
        document.getElementById('contrasena').classList.add('is-invalid');
        isValid = false;
    }

    return isValid;
}

// Manejo del toast de errores
function initToast() {
    const toast = document.getElementById('toastNombreUser');
    if (toast) {
        setTimeout(() => toast.classList.remove('show'), 3000);
    }
}

function showErrorToast(mensaje) {
    let toastContainer = document.getElementById('toastNombreUser');

    if (!toastContainer) {
        toastContainer = document.createElement('div');
        toastContainer.id = 'toastNombreUser';
        toastContainer.className = 'toast toast-error show';
        document.body.appendChild(toastContainer);
    }

    toastContainer.textContent = mensaje;
    setTimeout(() => toastContainer.classList.remove('show'), 3000);
}
// Función para mostrar/ocultar contraseña
function setupPasswordToggle() {
    const toggleButton = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('contrasena');

    if (!toggleButton || !passwordInput) return;

    toggleButton.addEventListener('click', function() {
        const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordInput.setAttribute('type', type);

        // Cambiar ícono (opcional)
        const icon = this.querySelector('i');
        if (icon) {
            icon.classList.toggle('bi-eye');
            icon.classList.toggle('bi-eye-slash');
        }

        // Cambiar texto del botón si no usas íconos
        this.textContent = type === 'password' ? 'Mostrar' : 'Ocultar';
    });
}
// Inicializar el formulario cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', initUsuarioForm);