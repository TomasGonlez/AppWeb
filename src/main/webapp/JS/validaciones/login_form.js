// Función principal de inicialización
function initLoginForm() {
    initFormSubmitHandler();     // Validación del formulario
    setupPasswordToggle();       // Mostrar/ocultar contraseña
    initToastAutoHide();         // Ocultar mensajes tipo toast
}

// Maneja el envío del formulario y validaciones
function initFormSubmitHandler() {
    const form = document.getElementById('formLogin');
    if (!form) return;

    form.addEventListener('submit', function (event) {
        const username = document.getElementById('nombreUsuario').value.trim();
        const password = document.getElementById('contrasenaUsuario').value;

        if (username === '' || password === '') {
            mostrarError('Por favor, complete todos los campos.');
            event.preventDefault(); // Detiene el envío del formulario
        }
    });
}
// Controla el botón para mostrar/ocultar la contraseña
function setupPasswordToggle() {
    const toggleButton = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('contrasenaUsuario');

    if (!toggleButton || !passwordInput) return;

    toggleButton.addEventListener('click', function() {
        const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordInput.setAttribute('type', type);

        // Cambiar ícono
        const icon = this.querySelector('i');
        if (icon) {
            icon.classList.toggle('bi-eye');
            icon.classList.toggle('bi-eye-slash');
        }
    });
}

// Oculta automáticamente los toasts después de 3 segundos
function initToastAutoHide() {
    const toasts = document.querySelectorAll('.toast.show');

    toasts.forEach(toast => {
        setTimeout(() => {
            toast.classList.remove('show');
        }, 3000);
    });
}

// Muestra un mensaje de error (puedes adaptar a toast o div si lo prefieres)
function mostrarError(mensaje) {
    alert(mensaje);
}

// Ejecuta la inicialización cuando el DOM está listo
document.addEventListener('DOMContentLoaded', initLoginForm);