/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgtest;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400ConnectionPool;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.CommandCall;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;
import com.ibm.as400.access.QSYSObjectPathName;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erargueta
 */
public class callRPG {

    private String serverIP;
    private String user;
    private String password;
    private ProgramCall programCall;
    ProgramParameter[] parmList;
    private AS400 as400 = null;

    public callRPG(String serverIP, String user, String password) {
        this.password = password;
        this.serverIP = serverIP;
        this.user = user;
        as400 = new AS400(serverIP, user, password);

    }

    public void testRPG(String libreria, String programa, String tipoPrograma, String... parametros) {
        ////AS400ConnectionPool connectionPool = new AS400ConnectionPool();
        try {
            // connectionPool.setMaxConnections(1);
            //connectionPool.setMaxLifetime(300000);
            //connectionPool.fill(serverIP, user, password, AS400.COMMAND, 1);
            // String libreriaL = "/QSYS.LIB/SRVSRC.LIB/CSRV037.PGM";
            String URL1 = QSYSObjectPathName.toPath(libreria, programa, tipoPrograma);
            //connectionPool.getConnection(serverIP, user, password, AS400.COMMAND);
            programCall = new ProgramCall(as400);
            parmList = CastStringProgramParameterAS400(parametros);
            programCall.setProgram(URL1, parmList);
            System.out.println("se procedera a llamar el programa:" + URL1);
            if (!programCall.run()) {
                System.out.println("Fallo al correr el RPG");
                AS400Message[] mesageList = programCall.getMessageList();
                for (AS400Message message : mesageList) {
                    System.out.println("Mensaje :" + message.getID());
                }
            } else {
                AS400Message[] messagelist = programCall.getMessageList();
                System.out.println("PROGRAMA correcto");
                for (int i = 0; i < messagelist.length; ++i) {
                    // Show each message.
                    System.out.println("mansaje :" + messagelist[i].getText());
                }
                System.out.println("----------------------------------------------------------------");

            }

        } catch (Exception e) {
            System.out.println("Error al llamar el RPG: " + e);
        } finally {
            try {
                if (as400 != null) {
                    as400.disconnectAllServices();
                }
            } catch (Exception ee) {
                System.out.println("Error al cerrar la conecxion: " + ee);
            }

        }

    }

    public void imprimirSalida(int... numParametro) {
        byte[] out;

        try {
            System.out.println("Imprimiendo parametros: ");
            for (int i : numParametro) {
                out = parmList[i].getOutputData();
                String ouString = new String(out, "IBM285").trim();
                System.out.println("           Parametro de salida del programa: " + ouString);

            }

        } catch (Exception e) {
            System.out.println("no se pudieron imprimir los valores el error es :" + e);
        }
        System.out.println("----------------------------------------------------------------");

    }

    public void cargarLibreria(String lib, String pg, String tp) {
        try {
            programCall = new ProgramCall(as400);
            String URL1 = QSYSObjectPathName.toPath(lib, pg, tp);
            programCall.setProgram(URL1);
            System.out.println("----------------------------------------------------------------");
            System.out.println("  Se cargala la libreria :" + URL1);
            if (!programCall.run()) {

                AS400Message[] mesageList = programCall.getMessageList();
                System.out.println("  Fallo al correr el RPG :" + mesageList.length);
                for (AS400Message message : mesageList) {
                    System.out.println("Mensaje :" + message.getID());
                }
            } else {
                AS400Message[] messagelist = programCall.getMessageList();
                System.out.println("  RPG correcto :" + messagelist.length);
                for (int i = 0; i < messagelist.length; ++i) {
                    // Show each message.
                    System.out.println("mansaje :" + messagelist[i].getText());
                }

            }

        } catch (Exception ex) {

            System.out.println("no se puedo cargar la libreria:" + ex);
        }
        System.out.println("----------------------------------------------------------------");
    }

    private ProgramParameter[] CastStringProgramParameterAS400(String... parametros) {
        ProgramParameter[] parametrosList = null;
        byte[] input;//[C:48,C:48,C:48,C:9,C:121,C:1000]
        //  int[] longitudes = {48, 48, 48, 121, 1000};

        System.out.println("creando parametros: ");
        int y = 0;
        if (parametros != null && parametros.length > 0) {
            parametrosList = new ProgramParameter[parametros.length];
            for (String g : parametros) {
                AS400Text branchTxt;

                if (g.length() < 1) {
                    branchTxt = new AS400Text(1000);
                } else {
                    branchTxt = new AS400Text(g.length() + 10);
                }
                System.out.println("        parametro :" + g + " longitud:" + branchTxt.getByteLength() + " tipo:" + branchTxt.getClass().toString());
                try {
                    // if (g != "" || g != null) {//parametro de entrada
                    input = g.getBytes("IBM285");
                    parametrosList[y] = new ProgramParameter(branchTxt.toBytes(g), branchTxt.getByteLength());
                    y++;
                    //    }
                } catch (Exception e) {
                    System.out.println("Error al parsear los datos: " + e.toString());
                }

            }

        }
        System.out.println("----------------------------------------------------------------");
        return parametrosList;
    }

}
