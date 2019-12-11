/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgtest;

/**
 *
 * @author erargueta
 */
public class RPGTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // dependencias : programa :CSRV037 tipo:PGM  libreria:SRVSRC    atributo CLP 
        String server = "150.150.6.7";//"150.150.60.233"; //150.150.6.7 
        String user = "ATL1ERAA";//ATLUCAE";
        String pass = "c0mpu123";//a25216cae";
        String libreria = "FRAN3"; //"AOGSRC";// "QMQMSAMP";
        String programa = "j47145DTC";//"PBORUT";// "MATH0200C";
        String tipoPrograma = "PGM";
        String[] parametros = {"S|47|L|C|08|0001|*", "","",""};//{"002211375", "4551309999990008", "", ""};//{"QM.ATH.PRODUCCION", "LQ.SOL.WEB", "LQ.RES.BAT", "0100*************498400080105********ABK00222**0****************************************************************", ""};
        callRPG rpg = new callRPG(server, user, pass);
        String[] dependencia = {"SRVSRC/CSRV037/PGM"};
        String[] desencriptado = {"FRAN3/j47145DTCX/PGM"};

        // rpg.cargarLibreria("SRVSRC", "CSRV037", "PGM");
        rpg.testRPG("SRVSRC", "PSRSPWB", tipoPrograma, parametros);
        rpg.imprimirSalida(1,2,3);
        // rpg.CommandCall(dependencia);
        // rpg.testRPG(libreria, programa, tipoPrograma, parametros);
        //rpg.cargarLibreria("SRVSRC", "CSRV037", "PGM");
        //  rpg.testRPG("AOGSRC", "PRUEBA2", "PGM", parametros);
        //rpg.CommandCall(desencriptado);

    }

}
