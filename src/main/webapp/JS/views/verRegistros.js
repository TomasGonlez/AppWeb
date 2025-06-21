// Función principal
function initTablaRegistros() {
    configurarDataTable();
}

// Inicializa la tabla con DataTables
function configurarDataTable() {
    const tabla = document.querySelector('.tabla-data');
    if (!tabla) return;

    $(tabla).DataTable({
        ordering: false,
        searching: true,
        pageLength: 5,
        language: {
            url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json'
        }
    });
}
// Esperar que el DOM esté listo
document.addEventListener('DOMContentLoaded', initTablaRegistros);
