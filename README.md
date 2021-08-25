# HospitalSocket

# Integrantes:
  -Barreto Salinas, Gisselle Estefania

  -Caceres Coronil, Jesus Ariel

  -Ferreira Galeano, Leticia Noemi

  -Garcia Ovelar, Kathia Maria del Rosario


# Requerimientos de Instalacion
• Instalar software base   
• Instalar servidor de base de datos PostgreSQL: https://www.postgresql.org/  
• Instalar el JDK de Java.
        - Si el JDK es version 8  
             No hace falta instalar JavaFx    
        - Si el JDK es version 11    
             Descargar JavaFx de https://drive.google.com/drive/folders/1631C-GkPcYtb55uMogQRLn03ZjHTOuVM?usp=sharing e instalarlo en la carpeta Java    
• Instalar el IDE Eclipse/IDEA Intellij desde: https://www.eclipse.org/downloads/ o https://www.jetbrains.com/es-es/idea/download/  
        ◦ OBS: Puede utilizar otro IDE de su preferencia.   
• Abrir el IDE IDEA Intellij  
• Abrir Menú "File"  
• Submenú "Project From Existing Sources"   
• Elegir: el ProyectoJavaCliente o el ProyectoJavaServidor   
• Elegir: "Maven"   
• Marcar: "Trust Project"    
• Abrir el IDE Eclipse   
• Abrir Menú "File"  
• Submenú "Import"  
• Elegir: "Existing Maven Projects"  
• Ubicar el directorio “lab-socket/ProyectoJava” dentro de su computadora. En ese directorio se encuentra el archivo pom.xml que contiene las configuraciones y librerías del proyecto.  
• Aparecerá disponible el proyecto Maven socket, dar Finalizar.  
• Habilitar el “Project Explorer” del IDE Eclipse: Menú “Window” -> “Show View” -> “Project Explorer”   
• Una vez importado y teniendo la vista de proyectos del Eclipse, realizar clic derecho sobre el proyecto, luego submenú "Maven" -> "Update Project ..." (debe setearse la opción Force Update), luego "ok". En este paso descarga la librería al repositorio local de maven.    

#Instrucciones
• Base de Datos:  
◦ Deberá crear una base de datos Postgresql con llamada “sd”  
◦ Deberá crear estructura cuyo script:  

    DROP TABLE Cama;
    DROP TABLE Hospital;

    CREATE TABLE Hospital(  
    Hospital_id INT GENERATED ALWAYS AS IDENTITY,  
    Hospital_name VARCHAR(50) NOT NULL,  
    PRIMARY KEY(Hospital_id)  
    );
    
    CREATE TABLE Cama(  
    Cama_id INT GENERATED ALWAYS AS IDENTITY,  
    Hospital_id INT,  
    ocupacion_cama BOOLEAN,
    habilitado_cama BOOLEAN,
    PRIMARY KEY(Cama_id),  
    CONSTRAINT fk_Hospital  
    FOREIGN KEY(Hospital_id)   
    REFERENCES Hospital(Hospital_id)  
    );
    
    INSERT INTO hospital(hospital_name) values ('Hospital San Lorenzo');
    INSERT INTO hospital(hospital_name) values ('Hospital Capiata');
    INSERT INTO hospital(hospital_name) values ('Hospital Villarrica');
    INSERT INTO hospital(hospital_name) values ('Hospital Paraguari');
    INSERT INTO hospital(hospital_name) values ('Hospital Asuncion');
    
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (1,false,true);
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (1,false,true);
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (1,false,true);
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (1,false,true);
    
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (2,false,true);
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (2,false,true);
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (2,false,true);
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (2,false,true);
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (2,false,true);
    
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (3,false,true);
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (3,false,true);
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (3,false,true);
    
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (4,false,true);
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (4,false,true);
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (4,false,true);
    INSERT INTO cama(hospital_id, ocupacion_cama, habilitado_cama) values (4,false,true);
    
    select * from hospital;
    select * from cama;

◦ Deberá configurar en la clase archivos.bd.Bd.java lo siguiente:  
▪ IP, puerto y nombre de la BD (variable url)  
▪ Usuario y Password del postgresql (variables user y password)  
