
package rpgtest;

/**
 *
 * @author erargueta
 Edwin Argueta from HN
 */
public class RPGTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    //server configurations
        String server = "";
        String user = "";
        String pass = "";
   //Main RPG to call
        String libreria = ""; 
        String programa = "";
        String tipoPrograma = "";
   //params to send RPG function
        String[] parametros = {"IN", "OUT","OUT","OUT"};
        callRPG rpg = new callRPG(server, user, pass);
   //if RPG program need load libraries 
        // rpg.cargarLibreria("LIBRARI", "PROGRAM", "TYPE");
   //call MAIN  RPG program
        rpg.testRPG("SRVSRC", "PSRSPWB", tipoPrograma, parametros);
  //print out params                                         0     1     2     3
        rpg.imprimirSalida(1,2,3);//  String[] parametros = {"IN", "OUT","OUT","OUT"};
       

    }

}
