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
        - Si el JDK es version 11 o superior 
             Descargar JavaFx de https://drive.google.com/drive/folders/1631C-GkPcYtb55uMogQRLn03ZjHTOuVM?usp=sharing e importar la carpeta lib en el proyecto seleccionado y para ejecutar la interfaz grafica agregar el comando de ejecucion 

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



IDE IDEA Inetllij:
• Abrir el IDE IDEA Intellij  
• Abrir Menú "File -> New -> Project From Existing Sources"  
• Elegir: el ProyectoJavaCliente o el ProyectoJavaServidor   
• Elegir: "Maven"   
• Marcar: "Trust Project" 
• Marcamos: New windows o nueva ventana para que nos pueda abrir ambos proyectos (Cliente / Servidor) sin cerrarnos alguno

• Marcar la carpeta src como source: "File -> Project Stucture -> Modules"
• Damos click en la pestaña "Sources", click en la carpeta "src" y click en la carpeta azul "source"
• Le damos aplicar y aceptar

(Solo necesario en el proyecto cliente)
• Si usamos JDK 11 o superior, debemos importar la libreria de JavaFX
• File -> Project Structure -> Library -> "+", y buscamos la carpeta que descargamos de JavaFX e importamos la subcarpeta "lib".
• Aceptamos todos los mensajes y damos aplicar, y aceptar

• Después agregamos una opcion al ejecutar para incluir a JavaFX (Solo necesario en el cliente)
• Run -> Edit Configuration 
• Si no aparece VM options apretamos modify options -> ADD VM options
• En VM options colocamos --module-path path\javafx-sdk-11.0.2\lib --add-modules javafx.controls,javafx.fxml (path es la ruta donde tenemos la carpeta)
• Le damos aplicar y aceptar

• Una vez abierto los proyectos, ejecutamos el main del servidor y el main del cliente.


◦ Deberá configurar en la clase archivos.bd.Bd.java lo siguiente:  
▪ IP, puerto y nombre de la BD (variable url)  
▪ Usuario y Password del postgresql (variables user y password)  
