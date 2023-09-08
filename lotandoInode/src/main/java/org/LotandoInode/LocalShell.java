package org.LotandoInode;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

public class LocalShell {

    public static void main (String[] args) throws IOException {
        final LocalShell shell = new LocalShell();

        Boolean controle = true;
        ArrayList<String> barraDeProgresso = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            barraDeProgresso.add(" ");
        }

        for(int i = 0; controle; i++){
            shell.executeCommand("dd if=/dev/urandom of=/mnt/teste/" + i + ".bin bs=1 count=1");
            if (i % 100 == 0){
                ClearConsole();
                int porcentagemDeUso = Integer.parseInt(shell.executeCommand("df -i /mnt/teste").substring(37, 38).trim());
                System.out.println(porcentagemDeUso);

                if(porcentagemDeUso % 10 == 0) {
                    int numeroBarraDeProgresso = porcentagemDeUso / 10;
                    barraDeProgresso.set(numeroBarraDeProgresso, "=");
                }
                System.out.print( "Porcentagem de uso " + porcentagemDeUso + "% "  + barraDeProgresso );
            }
            if (i > 25800) {
                controle = false;
            }
        }
    }

    private static final Logger log = Logger.getLogger(LocalShell.class.getName());

    public String executeCommand(final String command) throws IOException {

        final ArrayList<String> commands = new ArrayList<String>();
        commands.add("/bin/bash");
        commands.add("-c");
        commands.add(command);

        BufferedReader br = null;

        try {
            final ProcessBuilder p = new ProcessBuilder(commands);
            final Process process = p.start();
            final InputStream is = process.getInputStream();
            final InputStreamReader isr = new InputStreamReader(is);

            br = new BufferedReader(isr);

            br.readLine();

            String linha =  br.readLine();

             return linha;

        } catch (IOException ioe) {
            log.severe("Erro ao executar comando shell" + ioe.getMessage());
            throw ioe;
        } finally {
            secureClose(br);
        }
    }


    private void secureClose(final Closeable resource) {
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (IOException ex) {
            log.severe("Erro = " + ex.getMessage());
        }
    }



    public static void ClearConsole(){
        try{
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}

