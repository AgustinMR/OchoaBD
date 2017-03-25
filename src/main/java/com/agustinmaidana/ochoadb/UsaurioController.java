package com.agustinmaidana.ochoadb;

import com.orientechnologies.orient.core.db.ODatabase;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
    
    Unicamente con la anotacion @RestController, ya se genera el web service rest. todo lo demas son configuraciones y palabreria.
    
    CrossOrigin lo que hace es habilitar los pedidos AJAX desde cualquier dominio, es decir, que cualquiera pueda hacer pedidos a
    tu servidor(no solo desde localhost). Esto en la vida real no lo harias, a no ser que estes muy desesperado, te haya dejado tu
    novia o estes haciendo una tarea de la facultad. Lo que deberia hacerse, es utilizar autenticacion HTTP basica(user & pass) en
    todos los requests, y si te vas al hueso, OAuth como medida de seguridad.

    RequestMapping especifica que, todos los pedidos http hacia la URI /usaurios (http://direccion:puerto/usaurio) caiga en esta
    clase, o controlador mejor cicho.
    Tiene otros modificadores, como methods = algo, especificando que solamente los pedidos de cierto tipo sean posibles(GET, POST, etc)
    ej: @RequestMapping("/usaurios", method = RequestMethod.GET) solo permite pedidos get. por defecto se permite todo.
 */
@RestController
@CrossOrigin
@RequestMapping("/usaurios")
public class UsaurioController {

    /*
        
        El RequestMapping puesto encima de cada metodo, aplica solo para ese metodo, es decir, la restriccion de solo metodo POST 
        aplica para el metodo agregar, el cual es accedido con la URI http://direccion:puerto/usaurio/agregar ya que eso se especifico
    
        La joda es utilizar las operaciones(los verbos) definidos por el http, en vez de definirlas uno mismo(como si pasaba en SOAP)
        Esto es porque la mayoria de las veces van a ser esas las operaciones que utilices. las operaciones CRUD, por CREATE, READ,
        UPDATE, DELETE 
        
        @RequestParam define los parametros que recibe el pedido hecho por el web service
        la propiedad "name" especifica el nombre del parametro que se utilizo en el pedido http. o sea, cuando se programe luego el 
        html o javascript, o lo que sea, tiene que pasarse los parametros con esos nombres para que funke, o para que los reciba bien por lo menos...
    
        La idea de Spring es de proveer soluciones a problemas especificos y que anden de one. sin mucho misterio.
        Por eso, por defecto se asumen ciertas configuraciones, por ejemplo, que el puerto sea 8080. si no lo configuras explicitamente, funciona en ese.
        Lo mismo con otras cosas.
    
     */
    @RequestMapping(value = "agregar", method = RequestMethod.POST)
    public TipoRetorno agregar(@RequestParam(name = "nom") String nombre, @RequestParam(name = "ape") String apellido, @RequestParam(name = "ema") String email, @RequestParam(name = "pass") String password) {
        ODocument curso = new ODocument("Usaurio");
        ODatabaseDocumentTx database = new ODatabaseDocumentTx("plocal:/databases/ochoaDB");
        ODatabase bd = database.activateOnCurrentThread().open("admin", "admin");
        if (nombre != null) {
            curso.field("nombre", nombre);
        }
        if (apellido != null) {
            curso.field("apellido", apellido);
        }
        if (email != null) {
            curso.field("email", email);
        }
        if (password != null) {
            curso.field("password", password);
        }
        curso.save();
        bd.commit();
        bd.close();
        return TipoRetorno.OK;

        /*
            Basicamente, creo una conexion(normalmente se leen la url, el usuario y el password desde un archivo de conf o algo)
            Creo un documento del tipo "Usaurio" y le encajo esos cuatro campos con sus calores respectivamente.
            Ninguno es necesario.
            
            aca utilizo una api nativa de la base de datos para trabajar con documentos. Los tipos te la venden como que es la mas rapida.
            Se puede utilizar JDBC, JPA igualmente con esta bd. Incluso la api para acceso a datos de Spring. pero no.
        
            Defino un enum TipoRetorno que uso para retornar el status de la operacion.
            Esto permite ser mas exquisito(mas romantico) a la hora de especificar que fue lo que paso.
            Por ejemplo, quien recibe puede tomar decisiones dependiendo de si fue OK, o del tipo de error que haya sucedido.
            Es como un uso nativo de manejo de errores si se quiere, en vez de programar y manejar excepciones horribles.
        
            Al ser llamado desde el Web Service, se trasforma toda respuesta a JSON de todas maneras, ya sea un enum, un objeto, un boolean, etc.
         */
    }

    public ODocument getUsaurio(ODatabase bd, String rid) {

        /*
            Como se podran fijar, con esta operacion, obtengo un documento, un usaurio en este caso de la bd, 
            en base a un "rid"...
            Como tambien se podran dar cuenta, ese campo nunca lo ingrese en la operacion de agregar... :D
            
            Esto es porque, en OrientDB, no se precisa agregar un campo especifico para identificar a los documentos,
            Cuando un documento se crea, se genera un rid, o record id, o id de registro, que basicamente es un string
            de la forma #(cluster):(posicion), por ejemplo, #12:00 que indica la posicion del disco en la que se encuentra 
            el registro para ir a buscarlo. 
            
            Es un indice basicamente.
        
            Entonces, con la consulta sql de abajo, obtengo, si hay, el resultado de seleccionar el documento ubicado en "rid"
        
            Esta operacion no se ofrece mediante el web service, esta orientada a ser utilizada por las demas operaciones.
        
         */
        return (ODocument) bd.query(new OSQLSynchQuery("SELECT FROM " + rid)).get(0);
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public TipoRetorno modificar(@RequestParam(name = "rid") String rid, @RequestParam(name = "nom") String nombre, @RequestParam(name = "ape") String apellido, @RequestParam(name = "ema") String email, @RequestParam(name = "pass") String password) {
        ODatabaseDocumentTx database = new ODatabaseDocumentTx("plocal:/databases/ochoaDB");
        ODatabase bd = database.activateOnCurrentThread().open("admin", "admin");
        ODocument u = getUsaurio(bd, rid);
        if (u == null) {
            return TipoRetorno.ERR_USAURIO_INEXISTENTE;
        }
        if (nombre != null) {
            u.field("nombre", nombre);
        }
        if (apellido != null) {
            u.field("apellido", apellido);
        }
        if (email != null) {
            u.field("email", email);
        }
        if (password != null) {
            u.field("password", password);
        }
        u.save();
        bd.commit();
        bd.close();
        return TipoRetorno.OK;

        /*
        
            Obtengo el usario, si existe, y luego voy modificando o sobreescribiendo los valores en los campos si existian, o creandolos sino.
            Despues es la misma mierda que antes.
            Lo "comunmente bien visto" es hacer la modificacion o update, un metodo http PUT, pero con jquery es mas facil asi, por lo tanto ya fue. XD
         */
    }

    @RequestMapping(value = "obtener", method = RequestMethod.GET)
    public String obtener(@RequestParam(name = "rid") String rid) {
        ODatabaseDocumentTx database = new ODatabaseDocumentTx("plocal:/databases/ochoaDB");
        ODatabase bd = database.activateOnCurrentThread().open("admin", "admin");
        ODocument usaurio = getUsaurio(bd, rid);
        if (usaurio == null) {
            return TipoRetorno.ERR_USAURIO_INEXISTENTE.toString();
        }
        bd.close();
        return usaurio.toJSON();

        /*
        
            Algunas cosas... por ejemplo, por cada operacion, hay que crear una conexion. es rompe bola en ese sentido.
            
            Obtengo el usario, si no existe mando error, y si existe, retorno el mismo como JSON, mediante un metodo nativo de la api.
            
         */
    }

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public List<String> listar() throws IOException {
        List<String> ret = new ArrayList<>();
        ODatabaseDocumentTx database = new ODatabaseDocumentTx("plocal:/databases/ochoaDB");
        ODatabase bd = database.activateOnCurrentThread().open("admin", "admin");
        for (ODocument u : database.browseClass("Usaurio")) {
            ret.add(u.toJSON());
        }
        bd.close();
        return ret;
    }

}
