package club.tempestissimo.net.analyse;

import club.tempestissimo.net.entities.Net;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class NetToFileAnalyser implements AbstractAnalyser{
    String filePath;
    HashMap<String, List<Double>> data;
    @Override
    public HashMap<String, List<Double>> getData() {
        return this.data;
    }

    @Override
    public void clearData() {
        this.data = new HashMap<>();

    }


    @Override
    public void analyse(Net net) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (filePath,true),"UTF-8"));
            writer.write("tick=".concat(String.valueOf(net.getTickFrame())));
            writer.write("\n");
            writer.write("node count=".concat(String.valueOf(net.getNodeCount())));
            writer.write("\n");
            for (int i = 0; i < net.getNodeCount(); i++) {
                for (int j = 0; j < net.getNodeCount(); j++) {
                    writer.write(" ");
                    writer.write(String.valueOf(net.getConnectionMatrix()[i][j]));
                }
                writer.write("\n");
            }
            writer.write("\n");
            writer.flush();
            writer.close();

//            FileWriter fileWriter = new FileWriter(filePath, true);
//            BufferedWriter writer = Files.newBufferedWriter(fileWriter, StandardCharsets.UTF_8);
//            writer.append("tick=".concat(String.valueOf(net.getTickFrame())));
//            for (int i = 0; i < net.getNodeCount(); i++) {
//                for (int j = 0; j < net.getNodeCount(); j++) {
//                    writer.append(" ");
//                    writer.append(String.valueOf(net.getConnectionMatrix()[i][j]));
//                }
//                writer.append("\n");
//            }
//            writer.flush();
            //写入
//            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
//            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
//
//            dataOutputStream.writeUTF("tick=".concat(String.valueOf(net.getTickFrame())));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NetToFileAnalyser(String filePath) {
        this.filePath = filePath;
        this.data = new HashMap<>();
    }
}
