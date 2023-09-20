package org.LotandoInode;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class LocalShell {
    public static void main (String[] args) throws IOException {

        ArrayList<String> barraDeProgresso = generateBarraDeProgresso();
        barraDeProgresso.set(0, "=");

        for(int i = 0; true; i++){
            addBinary(i);
            if (i % 100 == 0) {
                ClearConsole();
                String porcentagemDeUso = executeCommand("df -i /mnt/teste").substring(35, 40).trim().replace("%", "");
                try {
                    if (parseInt(porcentagemDeUso) % 10 == 0) {
                        barraDeProgresso.set((int) Math.ceil(Double.parseDouble(porcentagemDeUso) / 10), "=");
                    }
                    String barraDeProgressoString = concatenarArray(barraDeProgresso);
                    System.out.println("Preenchendo inodes " + "[" + barraDeProgressoString + "] " + porcentagemDeUso + "%");

                } catch (IndexOutOfBoundsException e){
                    System.out.println("Acabou :)");
                    return;
                }
            }
        }

    }

    public static String concatenarArray(ArrayList<String> array) {
        StringBuilder resultado = new StringBuilder();

        for (String elemento : array) {
            resultado.append(elemento);
        }

        return resultado.toString();
    }

    private static ArrayList<String> generateBarraDeProgresso() {
        ArrayList<String> barraDeProgresso = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            barraDeProgresso.add(" ");
        }
        return barraDeProgresso;
    }

    public static String executeCommand(final String command) throws IOException {

        final ArrayList<String> commands = new ArrayList<>();
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

            return br.readLine();

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            throw ioe;
        } finally {
            secureClose(br);
        }
    }


    private static void secureClose(final Closeable resource) {
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void addBinary(Integer i) throws IOException {
        executeCommand("dd if=/dev/urandom of=/mnt/teste/" + i + ".bin bs=1 count=1");

    }

    public static void ClearConsole(){
        try{
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

