// Función principal de inicialización
function initLoginForm() {
    initFormSubmitHandler();
    initTogglePasswordHandler();
    initToastAutoHide();
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
            event.preventDefault(); // Evita el envío si hay errores
        }
    });
}

// Controla el botón para mostrar/ocultar la contraseña
function initTogglePasswordHandler() {
    const toggleButton = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('contrasenaUsuario');
    const toggleIcon = document.getElementById('toggleIcon');

    if (!toggleButton || !passwordInput || !toggleIcon) return;

    toggleButton.addEventListener('click', function () {
        const isHidden = passwordInput.type === 'password';

        passwordInput.type = isHidden ? 'text' : 'password';

        // Cambia el ícono dinámicamente
        toggleIcon.classList.remove('bi-eye-fill', 'bi-eye-slash-fill');
        toggleIcon.classList.add(isHidden ? 'bi-eye-slash-fill' : 'bi-eye-fill');
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