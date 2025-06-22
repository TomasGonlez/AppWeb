package com.example.appweb.CONTROLADOR;


import com.example.appweb.DAO.PersonaDAO;
import com.example.appweb.DAO.RegistroDAO;
import com.example.appweb.SERVICIO.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class InicializadorApp implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        //1.- Crear todas las dependencias compartidas
        PersonaDAO personaDAO = new PersonaDAO();
        RegistroDAO registroDAO = new RegistroDAO();

        // 2. Inicializar servicios
        ReporteService reporteService = new ReporteService();
        PersonaService personaService = new PersonaService(personaDAO);
        RegistroService registroService = new RegistroService(personaService, registroDAO);
        UsuarioService usuarioService = new UsuarioService();
        ExportServiceEXCEL exportServiceEXCEL = new ExportServiceEXCEL();
        ExportServicePDF exportServicePDF = new ExportServicePDF();

        // 3. Guardar en el contexto
        ServletContext contexto = event.getServletContext();
        contexto.setAttribute("reporteService", reporteService);
        contexto.setAttribute("personaService", personaService);
        contexto.setAttribute("registroService", registroService);
        contexto.setAttribute("usuarioService", usuarioService);
        contexto.setAttribute("exportServiceEXCEL", exportServiceEXCEL);
        contexto.setAttribute("exportServicePDF", exportServicePDF);


        System.out.println("🛠️ Servicios inicializados y disponibles en el contexto");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("🔌 Aplicación detenida - Liberando recursos");
    }
}
