// Función principal para inicializar
function initReportesPage() {
    inicializarDataTables();
    ocultarToastError();
    setTodayDate();
}

// Inicializa todas las tablas con configuración de DataTables
function inicializarDataTables() {
    const tablas = document.querySelectorAll('.tabla-data');

    tablas.forEach(tabla => {
        $(tabla).DataTable({
            ordering: true,
            searching: true,
            pageLength: 10,
            language: {
                url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json'
            },
            dom: '<"top"f>rt<"bottom"lip><"clear">'
        });
    });
}

// Oculta automáticamente el toast de error si existe
function ocultarToastError() {
    const toast = document.getElementById('toastError');
    if (toast) {
        setTimeout(() => {
            toast.classList.remove('show');
        }, 3000);
    }
}
function setTodayDate() {
    const fechaInput = document.getElementById('hasta');
    if (!fechaInput) return;

    const today = new Date();
    const yyyy = today.getFullYear();
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // Meses van de 0-11
    const dd = String(today.getDate()).padStart(2, '0');

    fechaInput.value = `${yyyy}-${mm}-${dd}`;
    //`${dd}-${mm}-${yyyy}`
}
// Ejecutar al cargar el DOM
document.addEventListener('DOMContentLoaded', initReportesPage);
